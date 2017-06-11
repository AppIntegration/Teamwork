package com.omertron.imdbapi.request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author yuminchen
 * @version V1.0
 * @date 2017/6/7
 */
public class PageGrasper {

    public final static int START = 0;
    public final static int NUM = 200;
    public final static int MAX = 3240;//3240
    MovieRequest request = new MovieRequest();

    public void readHtml(int index) {
        System.out.println(request.getPage(index));
        Document doc = Jsoup.parse(request.getPage(index));

        Set<String> urlSet = new TreeSet<String>();

        Elements list = doc.getElementsByTag("a");
        for (Element link : list) {
            String linkHref = link.attr("href");
            if (linkHref.startsWith("https://movie.douban.com/subject/")) {
                urlSet.add(linkHref);
            }
        }

        Set<String> idSet = new TreeSet<String>();
        for (String url : urlSet) {
            String result = request.sendGet(url);
            int start = result.indexOf("http://www.imdb.com/title/tt");
            idSet.add(result.substring(start + 26, start + 35));
        }

        try {
            ForFile.writeFileContent(idSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = START; i <= MAX; i = i + 20) {
            System.out.println("NOW:" + i);
            new PageGrasper().readHtml(i);
        }
    }
}

