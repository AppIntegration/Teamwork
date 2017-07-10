package DataService;

import java.util.List;
import java.util.Map;

/**
 * Created by SK on 2017/7/10.
 */
public interface FilmSearch {
    //通过豆瓣ID得到中文名字
    public String getCname(String id);

    public Map<String,Map<String,Double>> getMaps();
}
