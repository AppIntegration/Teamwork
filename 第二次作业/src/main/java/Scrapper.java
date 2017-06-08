import java.io.IOException;

/**
 * Created by kylin on 08/06/2017.
 * All rights reserved.
 */
public class Scrapper {

    private int countPerTime;

    private DBUtil dbUtil;

    private UrlRequestService requestService;

    public Scrapper() {
        countPerTime = 20;
        dbUtil = new DBUtil();
        requestService = new UrlRequestHttpClient();
    }

    private String getURL(int year, int start, int count) {
        return "http://api.douban.com/v2/movie/search?tag=" + year + "&start=" + start + "&count=" + count;
    }

    public void scrapMovie(int year, int totalNumber) {
        int index = 0;
        while (index < totalNumber) {
            // 拼接URL
            String url = getURL(year, index, countPerTime);
            try {
                // 调用豆瓣API获取电影列表的json
                String movieListJson = requestService.getSring(url);
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

}
