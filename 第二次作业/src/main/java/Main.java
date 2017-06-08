/**
 * Created by kylin on 08/06/2017.
 * All rights reserved.
 */
public class Main {

    public static void main(String[] args) {
        Scrapper scrapper = new Scrapper();
        // 获取2016年的电影，先获取100个
        scrapper.scrapMovie(2016,10000);
    }

}
