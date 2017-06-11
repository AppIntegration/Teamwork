package data;

import dataimpl.MovieDataXmlImpl;
import dataservice.MovieDataService;
import org.junit.Test;
import po.Movie;

import java.util.List;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public class DataServiceTest {

    @Test
    public void test(){
        MovieDataService service = new MovieDataXmlImpl();
        List<Movie> result = service.getAllMovieOf2016();
        System.out.println(result.size());
        for(Movie movie:result){
            System.out.println(movie);
        }
    }
}
