package com.omertron.imdbapi.Combination;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mongodb.*;
import com.mysql.jdbc.PreparedStatement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.converters.StringArrayConverter;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SK on 2017/6/11.
 * 用于将三个数据库中的异构数据整合入一个mongodb的collection中
 */
public class Combiantion {
    public static void main(String[] args) {
        // get three mongodb datasources
        Mongo mongo = null;

        try {
            mongo = new Mongo("127.0.0.1", Integer.parseInt("27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DB db;
        DBCollection imdbCollection,doubanCollection,timeCollection;

        db=mongo.getDB("test");


        //get mysql datasource: to store

        String driver = "com.mysql.jdbc.Driver";
        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://127.0.0.1:3306/film";
        // MySQL配置时的用户名
        String user = "root";
        // Java连接MySQL配置时的密码
        String password = "130014";

        // 加载驱动程序
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("connection failed!");
            e.printStackTrace();
        }
        // 连续数据库
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }





        timeCollection=db.getCollection("time");
        // read from time database
        DBCursor timeCursor=timeCollection.find();



        //time database


        while(timeCursor.hasNext()){
            BasicDBObject bdbObj = (BasicDBObject) timeCursor.next();
            String ename=bdbObj.getString("engName");
            String cname=bdbObj.getString("chiName");
            String year=bdbObj.getString("year");
            String score=bdbObj.getString("score");
            String num=bdbObj.getString("num");

            String intro=bdbObj.getString("intro");
            if(ename.length()==0 || ename==null)
                continue;
            int i = 0;
            String sql = "insert into allFilms (ename,cname,year,timeScore,timeVote,ctagline) values(?,?,?,?,?,?)";
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);

                pstmt.setString(1,ename);
                pstmt.setString(2,cname);
                pstmt.setString(3,year);
                pstmt.setString(4,score);
                pstmt.setString(5,num);
                pstmt.setString(6,intro);

                i = pstmt.executeUpdate();
                pstmt.close();

            } catch (SQLException e) {
                System.out.println(sql);
                e.printStackTrace();
            }


        }
        timeCursor.close();
        System.out.println("finish time insert!");



        doubanCollection=db.getCollection("douban");
        DBCursor doubanCursor=doubanCollection.find();
        //doubanCursor
        while(doubanCursor.hasNext()){
            BasicDBObject bdbObj = (BasicDBObject) doubanCursor.next();
            String cname=bdbObj.getString("title");
            String ename=bdbObj.getString("original_title");
            String year=bdbObj.getString("year");

            String directors=bdbObj.getString("directors");

            JSONArray jarry= JSONArray.fromObject(directors);
            directors="";
            for(int i=0;i<jarry.size();i++){
                JSONObject tmpObj=jarry.getJSONObject(i);
                directors += tmpObj.getString("name")+";";
            }
            if(directors.length() > 1)
                directors=directors.substring(0,directors.length()-1);


            String casts=bdbObj.getString("casts");

            JSONArray carray=JSONArray.fromObject(casts);
            casts="";
            for(int i=0;i<carray.size();i++){
                JSONObject tmpObj=carray.getJSONObject(i);
                casts += tmpObj.getString("name")+";";
            }
            if(casts.length()>1)
                casts=casts.substring(0,casts.length()-1);


            String genres=bdbObj.getString("genres");
            JSONArray garray=JSONArray.fromObject(genres);
            genres="";
            for(int i=0;i<garray.size();i++){

              genres +=  garray.get(i)+";";
            }
            if(genres.length()>1)
                genres=genres.substring(0,genres.length()-1);



            String doubanId=bdbObj.getString("id");

            BasicDBObject score= (BasicDBObject) bdbObj.get("rating");
            String average=score.getString("average");
            String collect_count=bdbObj.getString("collect_count");

            BasicDBObject images= (BasicDBObject) bdbObj.get("images");
            String smallImage=images.getString("small");
            String mediumImage=images.getString("medium");
            String largeImage=images.getString("large");


            String sql = "select * from allFilms where (cname=? or ename=?) and year=?";
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                pstmt.setString(1,cname);
                pstmt.setString(2,ename);
                pstmt.setString(3,year);


                ResultSet set=pstmt.executeQuery();
                int mark=0;
                if(set.next()){
                    mark=1;
                }

                if(mark == 0){
                    sql = "insert into allFilms (doubanId,ename,cname,year,doubanScore,doubanVote,genre,cast" +
                            ",directors," +
                            "smallImage,mediumImage,bigImage) " +
                            "values(?,?,?,?,?,?,?,?,?,?,?,?)";
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setString(1,doubanId);
                    pstmt.setString(2,ename);
                    pstmt.setString(3,cname);
                    pstmt.setString(4,year);
                    pstmt.setString(5,average);
                    pstmt.setString(6,collect_count);
                    pstmt.setString(7,genres);
                    pstmt.setString(8,casts);
                    pstmt.setString(9,directors);
                    pstmt.setString(10,smallImage);
                    pstmt.setString(11,mediumImage);
                    pstmt.setString(12,largeImage);

                    pstmt.executeUpdate();

                }else{
                    sql="update allFilms set doubanId=? ,doubanVote=?,genre=?,cast=?,directors=?," +
                            "smallImage=?,mediumImage=?," +
                            "bigImage=?,doubanScore=?" +
                            "where (cname=? or ename=?) and year=?";
                    pstmt=(PreparedStatement)conn.prepareStatement(sql);
                    pstmt.setString(1,doubanId);
                    pstmt.setString(2,collect_count);
                    pstmt.setString(3,genres);
                    pstmt.setString(4,casts);
                    pstmt.setString(5,directors);
                    pstmt.setString(6,smallImage);
                    pstmt.setString(7,mediumImage);
                    pstmt.setString(8,largeImage);
                    pstmt.setString(9,average);
                    pstmt.setString(10,cname);
                    pstmt.setString(11,ename);
                    pstmt.setString(12,year);

                    pstmt.executeUpdate();
                }


                pstmt.close();

            } catch (SQLException e) {
                System.out.println(sql);
                e.printStackTrace();
            }


        }
        doubanCursor.close();








        imdbCollection=db.getCollection("foo");
        DBCursor imdbCursor=imdbCollection.find();

        //imdbCursor
        while(imdbCursor.hasNext()){
            BasicDBObject bdbObj = (BasicDBObject) imdbCursor.next();
            String ename=bdbObj.getString("title");
            String year=bdbObj.getString("year");
            String rating=bdbObj.getString("rating");
            String num=bdbObj.getString("numVotes");
            String tagline=bdbObj.getString("tagline");
            String imdbId=bdbObj.getString("imdbId");

            String writers=bdbObj.getString("writers");
            JSONArray warray=JSONArray.fromObject(writers);
            writers="";
            for(int i=0;i<warray.size();i++){
                writers += warray.get(i)+";";
            }
            if(writers.length()>1)
                writers = writers.substring(0,writers.length()-1);


            String casts=bdbObj.getString("casts");
            JSONArray carray=JSONArray.fromObject(casts);
            casts="";
            for(int i=0;i<carray.size();i++){
                casts += carray.get(i)+";";
            }
            if(casts.length()>1)
                casts=casts.substring(0,casts.length()-1);


            String directors=bdbObj.getString("directors");
            JSONArray darray=JSONArray.fromObject(directors);
            directors="";
            for(int i=0;i<darray.size();i++){
                directors += darray.get(i)+";";
            }
            if(directors.length()>1)
                directors=directors.substring(0,directors.length()-1);


            String genres=bdbObj.getString("genres");
            JSONArray garray=JSONArray.fromObject(genres);
            genres="";
            for(int i=0;i<garray.size();i++){
                genres += garray.get(i)+";";
            }
            if(genres.length()>1)
                genres=genres.substring(0,genres.length()-1);


            String sql = "select genre,cast,directors from allFilms where ename=? and year=?";
            PreparedStatement pstmt;
            String doubanGenre = null;
            String doubanCast = null;
            String doubanDirector = null;


            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);

                pstmt.setString(1,ename);
                pstmt.setString(2,year);


                ResultSet set=pstmt.executeQuery();
                int mark=0;
                if(set.next()){
                    mark=1;
                    doubanGenre=set.getString("genre");
                    doubanCast=set.getString("cast");
                    doubanDirector=set.getString("directors");
                }

                if(mark == 0){
                    sql = "insert into allFilms (imdbId,ename,year,imdbScore,imdbVote,genre,cast,directors,writers," +
                            "etagline) " +

                            "values(?,?,?,?,?,?,?,?,?,?)";
                    pstmt = (PreparedStatement) conn.prepareStatement(sql);
                    pstmt.setString(1,imdbId);
                    pstmt.setString(2,ename);
                    pstmt.setString(3,year);
                    pstmt.setString(4,rating);
                    pstmt.setString(5,num);
                    pstmt.setString(6,genres);
                    pstmt.setString(7,casts);
                    pstmt.setString(8,directors);
                    pstmt.setString(9,writers);
                    pstmt.setString(10,tagline);

                    pstmt.executeUpdate();

                }else{
                    sql="update allFilms set imdbId=? ,imdbScore=?,imdbVote=?,genre=?,cast=?,directors=?," +
                            "writers=?,etagline=?" +

                            "where ename=? and year=?";
                    pstmt=(PreparedStatement)conn.prepareStatement(sql);
                    pstmt.setString(1,imdbId);
                    pstmt.setString(2,rating);
                    pstmt.setString(3,num);
                    if(doubanGenre == null || doubanGenre.length()==0)
                        pstmt.setString(4,genres);
                    else
                        pstmt.setString(4,doubanGenre+"/"+genres);

                    if(doubanCast == null || doubanCast.length()==0)
                        pstmt.setString(5,casts);
                    else
                        pstmt.setString(5,doubanCast+"/"+casts);

                    if(doubanDirector == null || doubanDirector.length()==0)
                        pstmt.setString(6,directors);
                    else
                        pstmt.setString(6,doubanDirector+"/"+directors);

                    pstmt.setString(7,writers);
                    pstmt.setString(8,tagline);
                    pstmt.setString(9,ename);
                    pstmt.setString(10,year);

                    pstmt.executeUpdate();
                }


                pstmt.close();

            } catch (SQLException e) {
                System.out.println(sql);
                e.printStackTrace();
            }




        }
        imdbCursor.close();





    }
}
