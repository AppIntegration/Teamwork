package Recommend;

import java.util.List;
import java.util.Map;

/**
 * Created by SK on 2017/7/10.
 */
public class RecommendServiceImpl implements RecommendService {
    @Override
    public List<String> getRecommendFilm(String username, int num) {
        Recommender recommender = new Recommender();

        return recommender.getRecommendFilms(username,num);
    }

    @Override
    public void saveFilmInfo(String username, Map<String, Double> rates) {

    }
}
