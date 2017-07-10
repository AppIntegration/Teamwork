package Data;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylin on 08/06/2017.
 * All rights reserved.
 */
public class Scrapper {

    private DbStore store;
    private int countPerTime;

    private DBUtil dbUtil;

    private UrlRequestService requestService;

    private MovieRequest request;

    public Scrapper() {
        countPerTime = 20;
//        dbUtil = new DBUtil();
        requestService = new UrlRequestHttpClient();
        store = new DbStore();
        request = new MovieRequest();
    }

    private String getURL(int year, int start, int count) {
        return "http://api.douban.com/v2/movie/search?tag=" + year + "&start=" + start + "&count=" + count;
    }

    public void scrapMovie(int year, int totalNumber) {

        // http://api.douban.com/v2/movie/search?tag=2016&start=1831&count=20

        int index = 1831;
        while (index < totalNumber) {
            // 拼接URL
            String url = getURL(year, index, countPerTime);
            try {
                // 调用豆瓣API获取电影列表的json
                String movieListJson = requestService.getString(url);
                // 向数据库存储数据
                dbUtil.saveListOfMovie(movieListJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取下一批电影
            index += countPerTime;
            System.out.println("index = " + index);
        }
    }


//    private String getComment(String id){
//        return "http://api.douban.com/v2/movie/subject/"+id+"/comments";
//    }
    public void extractDoubanComment(String id) throws IOException {
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        String commentUrl="http://movie.douban.com/subject/"+id+"/comments?status=P";
        //System.out.println(commentUrl);
        //https://movie.douban.com/subject/26606743/comments?start=20&limit=20&sort=new_score&status=P

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(commentUrl);
        HttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, "utf-8");
//        System.out.println(content);

//        String content = request.getPage(id);

        org.jsoup.nodes.Document doc = Jsoup.parse(content);

        List<String> usernames = new ArrayList<String>();
        List<Double> ratings = new ArrayList<Double>();

            Elements elements = doc.getElementsByAttributeValue("class","comment-info");
            System.out.println(elements.size());
            for(int i=0;i<elements.size();i++){

                Element element = elements.get(i);
                String vote= element.select("[title]").get(0).attr("title");

                Double rate=getRate(vote);

                String people=element.select("[href]").get(0).attr("href");

                people=getUsername(people);

                usernames.add(people);

                ratings.add(rate);

            }

            store.store(id,usernames,ratings);

    }

    public void getMovieComments(){
        FileReader fr = null;
        try {
            fr = new FileReader("id.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        try {
            while((line=br.readLine())!=null) {
                if(line.length()<1) {
                    continue;
                }
                System.out.println(line);

                extractDoubanComment(line);

            }
        } catch (IOException e) {

            e.printStackTrace();
        }

    }




    public String getUsername(String link){
        String result="";
        int mark=0;
        for(int i=link.length()-2;i>=0;i--){
            if(link.substring(i,i+1).equals("/")){
                mark=i;
                break;
            }

        }

        result=link.substring(mark+1,link.length()-1);
        return result;
    }


    public Double getRate(String words){
        Map<String,Double> map = new HashMap<String,Double>();
        map.put("很差",1.0);
        map.put("力荐",5.0);
        map.put("较差",2.0);
        map.put("还行",3.0);
        map.put("推荐",4.0);
        if(!map.containsKey(words)){
            return 0.0;
        }

        return map.get(words);


    }

}
