import org.junit.Test;

import java.io.IOException;

/**
 * Created by kylin on 08/06/2017.
 * All rights reserved.
 */
public class URLTest {

    @Test
    public void testGet(){
        String url = "http://api.douban.com/v2/movie/search?tag=2016&start=0&count=10";
        UrlRequestHttpClient requestHttpClient = new UrlRequestHttpClient();
        try {
            String result = requestHttpClient.getString(url);
            DBUtil dbUtil = new DBUtil();
            dbUtil.saveListOfMovie(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
