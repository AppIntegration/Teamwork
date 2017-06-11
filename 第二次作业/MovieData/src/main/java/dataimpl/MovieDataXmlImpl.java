package dataimpl;

import dataservice.MovieDataService;
import po.Movie;
import po.MovieXML;
import po.MovieXMLCache;
import util.MovieDigester;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public class MovieDataXmlImpl implements MovieDataService {

    private MovieXMLCache movieXMLCache;

    private MovieDigester movieDigester;

    public MovieDataXmlImpl() {
        movieDigester = new MovieDigester();
        // read xml
        movieXMLCache= movieDigester.getMovieCache();
    }

    public List<Movie> getAllMovieOf2016() {
        List<MovieXML> movieXMLList = movieXMLCache.getMovieXMLList();
        return this.convert(movieXMLList);
    }

    public List<Movie> getMovieOf2016(int start, int count) {
        List<MovieXML> movieXMLList = movieXMLCache.getMovieXMLList(start,count);
        return this.convert(movieXMLList);
    }

    public List<Movie> search(String keyword) {
        List<MovieXML> movieXMLList = movieXMLCache.search(keyword);
        return this.convert(movieXMLList);
    }

    private List<Movie> convert(List<MovieXML> movieXMLList) {
        List<Movie> result = new ArrayList<Movie>();
        for (MovieXML movieXML:movieXMLList){
            result.add(new Movie(movieXML));
        }
        return result;
    }

}
