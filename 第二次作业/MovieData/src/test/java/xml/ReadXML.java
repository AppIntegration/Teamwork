package xml;

import org.junit.Test;
import po.MovieXMLCache;
import util.MovieDigester;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public class ReadXML {

    @Test
    public void test() {
        MovieDigester movieDigester = new MovieDigester();
        MovieXMLCache movieXMLCache = movieDigester.getMovieCache();
        System.out.println(movieXMLCache.toString());
    }
}
