package Recommend;

import DataService.FilmSearch;
import DataService.FilmSearchImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yuminchen
 * @version V1.0
 * @date 2017/7/9
 */
public class Recommender {

    Map<String, Map<String, Double>> prefs;

    public Recommender() {
        FilmSearch searcher=new FilmSearchImpl();
        prefs = searcher.getMaps();
//        System.out.println(prefs.size());
//        final Map<String,Double> t1 = new HashMap<String, Double>(){{
//            put("1", 1.5);
//            put("2", 5.5);
//            put("3", 7.0);
//            put("4", 8.0);
//            put("5", 6.5);
//            put("6", 7.5);
//
//        }};
//        final Map<String,Double> t2 = new HashMap<String, Double>(){{
//            put("1", 5.5);
//            put("2", 5.0);
//            put("3", 9.0);
//            put("4", 7.0);
//            put("5", 6.5);
//        }};
//        final Map<String,Double> t3 = new HashMap<String, Double>(){{
//            put("1", 8.5);
//            put("3", 7.0);
//            put("4", 8.0);
//            put("5", 9.5);
//            put("6", 9.5);
//
//        }};
//        final Map<String,Double> t4 = new HashMap<String, Double>(){{
//            put("2", 5.5);
//            put("3", 6.0);
//        }};
//
//        final Map<String,Double> t5 = new HashMap<String, Double>(){{
//            put("3", 6.5);
//            put("4", 7.0);
//            put("5", 8.5);
//        }};
//
//        final Map<String,Double> t6 = new HashMap<String, Double>(){{
//            put("2", 8.5);
//            put("4", 7.5);
//            put("6", 8.5);
//        }};
//
//        prefs = new HashMap<String, Map<String, Double>>(){{
//            put("Mike", t1);
//            put("Jerry", t2);
//            put("Cheney", t3);
//            put("Jone", t4);
//            put("Candy", t5);
//            put("Keven", t6);
//        }};
    }

    private double calEuclideanDistance(Map<String, Double> p1, Map<String, Double> p2){
        // 交集
        Set<String> filmName = new HashSet<>(p1.keySet());
        Set<String> filmName2 = p2.keySet();
        filmName.retainAll(filmName2);
        if(filmName.size() == 0){
            return 0.0;
        }

        double sum = filmName
                .stream()
                .map(
                        o -> Math.pow(p1.get(o) - p2.get(o), 2)
                )
                .reduce(0.0, Double::sum);

        return 1/(1 + Math.sqrt(sum));

    }

    private double simPearson(Map<String, Double> p1, Map<String, Double> p2){
        // 交集
        Set<String> filmName = new HashSet<>(p1.keySet());
        Set<String> filmName2 = p2.keySet();
        filmName.retainAll(filmName2);

        int len = filmName.size();
        if(len == 0){
            return 0.0;
        }

        double sum1 = filmName
                .stream()
                .map(p1::get)
                .reduce(0.0, Double::sum);
        double sum2 = filmName
                .stream()
                .map(p2::get)
                .reduce(0.0, Double::sum);

        double sumSqrt1 = filmName
                .stream()
                .map(o -> Math.pow(p1.get(o), 2))
                .reduce(0.0, Double::sum);
        double sumSqrt2 = filmName
                .stream()
                .map(o -> Math.pow(p2.get(o), 2))
                .reduce(0.0, Double::sum);
        double sumMul = filmName
                .stream()
                .map( o -> p1.get(o) * p2.get(o))
                .reduce(0.0, Double::sum);

        double num = sumMul - sum1 * sum2/len;
        double den = Math.sqrt((sumSqrt1 - Math.pow(sum1, 2)/len) * (sumSqrt2 - Math.pow(sum2, 2)/len));
        if(den == 0){
            return 0.0;
        }
        return num/den;

    }

    public List<String> filmRecommend(String personName, SimilarityCalculator calculator, int filmNum){
        Set<String> pName = prefs.keySet();
        if(!pName.contains(personName)){
            System.err.println("user not exist");
            return null;
        }
        Map<String, Double> curMap = prefs.get(personName);
        Map<String, Double> sortedFilm = new HashMap<>();
        pName
                .stream()
                .filter( o -> !o.equals(personName))
                .forEach(o ->
                {
                    double sim = calculator.calSim(curMap, prefs.get(o));
                    System.out.println("who : " + o + " sim : "+ sim);

                    if(sim > 0){
                        Set<String> filmNames = new HashSet<>(prefs
                                .get(o)
                                .keySet());
                        filmNames.removeAll(curMap.keySet());
                        filmNames
                                .forEach( sub ->
                                {
                                    if(sortedFilm.containsKey(sub)){
                                        sortedFilm.put(sub, sortedFilm.get(sub) + sim * prefs.get(o).get(sub));
                                    }
                                    else {
                                        sortedFilm.put(sub, sim * prefs.get(o).get(sub));
                                    }
                                }
                                );

                    }
                }
                );

        List<Map.Entry<String, Double>> list = sortedFilm
                .entrySet()
                .stream()
                .collect(Collectors.toList());
        list.sort((o1, o2) -> o1.getValue() - o2.getValue() > 0 ? -1 : 1);

        System.out.println(list);
        return list
                .stream()
                .map(Map.Entry::getKey)
                .limit(filmNum)
                .collect(Collectors.toList());

    }

    public static void main(String[] args) {
        Recommender recommender = new Recommender();
        System.out.println(recommender.filmRecommend("user1", recommender::simPearson, 2));
    }

    public List<String> getRecommendFilms(String username,int num){
//        Recommender recommender = new Recommender();
        return filmRecommend("103214078", this::simPearson, 2);
    }

    interface SimilarityCalculator{
        public double calSim(Map<String, Double> m1, Map<String, Double> m2);
    }

}

