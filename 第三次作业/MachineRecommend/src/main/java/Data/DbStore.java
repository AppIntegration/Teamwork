package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SK on 2017/7/9.
 */
public class DbStore {
    Connection con;

    public DbStore(){
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

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void store(List<String> doubanId, List<String> name ,List<String> ename){
        if(con == null){
            System.out.println("connnect  null");
        }
        // 创建sql语句模板
        String sql = "insert into  douban(doubanId,name,ename) values(?,?,?)";
        // 创建一个声明对象
        PreparedStatement pst = null;
        try {
            pst = (PreparedStatement) con.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("create prepared statement faile");
            e.printStackTrace();
        }
        // 用循环将数据添加到sql模板中
        for (int i = 0; i < doubanId.size(); i++) {
            try {
                pst.setString(1, doubanId.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(2, name.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(3, ename.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                pst.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 将sql语句发送到mysql上
        int[] res = new int[0];
        try {
            res = pst.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        try {
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void store(String id,List<String> username, List<Double> ratings) {
        if(con == null){
            System.out.println("connnect  null");
        }
        // 创建sql语句模板
        String sql = "insert into  comments(username,filmId,rating) values(?,?,?)";
        // 创建一个声明对象
        PreparedStatement pst = null;
        try {
            pst = (PreparedStatement) con.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println("create prepared statement faile");
            e.printStackTrace();
        }
        // 用循环将数据添加到sql模板中
        for (int i = 0; i < username.size(); i++) {
            try {
                pst.setString(1, username.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setString(2, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pst.setDouble(3, ratings.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                pst.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 将sql语句发送到mysql上
        int[] res = new int[0];
        try {
            res = pst.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        try {
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFilmCName(String id){
        String result=null;
        String sql = "select name from douban where id=?";
        PreparedStatement pst = null;
        try {
            pst=con.prepareStatement(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                result = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public String getFilmEName(String id){
        String result=null;
        String sql = "select ename from douban where id=?";
        PreparedStatement pst = null;
        try {
            pst=con.prepareStatement(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                result = rs.getString("ename");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }



    public Map<String,Map<String,Double>> getMaps(){
        Map<String,Map<String,Double>> map= new HashMap<>();

        String sql = "select * from comments";
        PreparedStatement pst = null;
        try {
            pst=con.prepareStatement(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String id = rs.getString("filmId");
                Double rating= rs.getDouble("rating");
                String username=rs.getString("username");
                if(map.get("username")==null){
                    Map<String,Double> newmap=new HashMap<String,Double>();
                    newmap.put(id,rating);
                    map.put(username,newmap);
                }else{
                    Map<String,Double> peopleMap=map.get("username");
                    peopleMap.put(id,rating);
                    map.replace(username,peopleMap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

}
