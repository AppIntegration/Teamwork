package com.omertron.imdbapi.Integation;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.omertron.imdbapi.ImdbApi;
import com.omertron.imdbapi.ImdbException;
import com.omertron.imdbapi.model.ImdbMovieDetails;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SK on 2017/6/8.
 * 用于爬取imdb上的数据，根据imdbid列表从而得到数据
 */
public class Main {
    public static void main(String[] args){
        ImdbApi api=new ImdbApi();
        FileReader fr= null;

        try {
            fr = new FileReader("resttxt.txt");
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(fr);
        String line="";
        List<DBObject> totalInfo=new ArrayList<DBObject>();

        try {
            while ((line = br.readLine()) != null) {
                if(!line.substring(0,1).equals("t")){
                    continue;
                }
                try {
                    ImdbMovieDetails movie=api.getFullDetails(line);
                    List<String> casts=new ArrayList<String>();
                    List<String> directors=new ArrayList<String>();
                    List<String> writers=new ArrayList<String>();

                    for(int i=0;i<movie.getCast().size();i++){
                        casts.add(movie.getCast().get(i).getPerson().getName());
                    }
                    for(int i=0;i<movie.getDirectors().size();i++){
                        directors.add(movie.getDirectors().get(i).getPerson().getName());
                    }
                    for(int i=0;i<movie.getWriters().size();i++){
                        writers.add(movie.getWriters().get(i).getPerson().getName());
                    }

                    MongoModel model=new MongoModel(movie.getYear(),movie.getType(),movie.getTitle(),
                            movie.getTagline(),movie.getRating(),movie.getImdbId(),movie.getGenres(),
                            movie.getNumVotes(),casts,directors,writers);

                    JSONObject json= JSONObject.fromObject(model);
//                    JSONArray array=JSONArray.fromObject(model);
                    String fileJson=json.toString();
//                    System.out.println(fileJson);


                    totalInfo.add((DBObject)JSON.parse(fileJson));





                } catch (ImdbException e) {
                    System.out.println("the line is not get:"+line);
                    e.printStackTrace();
                }



            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Mongo mongo = null;
        try {
            mongo = new Mongo("127.0.0.1", Integer.parseInt("27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB db;
        DBCollection dbCollection;

        db=mongo.getDB("test");
        dbCollection=db.getCollection("foo");
        dbCollection.insert(totalInfo);
        System.out.println("job done");

    }
}
