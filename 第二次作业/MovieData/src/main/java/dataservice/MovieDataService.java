package dataservice;

import po.Movie;

import java.util.List;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public interface MovieDataService {

    /**
     * 得到2016年所有的电影
     *
     * @return
     */
    List<Movie> getAllMovieOf2016();

    /**
     * 从start开始获取指定数目的电影
     *
     * @param start
     * @param count
     * @return
     */
    List<Movie> getMovieOf2016(int start, int count);
}
