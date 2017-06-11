package po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public class MovieXMLCache {

    private List<MovieXML> movieXMLList;

    private boolean isSorted;

    public MovieXMLCache() {
        isSorted = false;
        movieXMLList = new ArrayList<MovieXML>();
    }

    public List<MovieXML> getMovieXMLList() {
        return movieXMLList;
    }

    public void setMovieXMLList(List<MovieXML> movieXMLList) {
        this.movieXMLList = movieXMLList;
    }

    // 供Digester调用的方法
    public void addMovieXML(MovieXML area) {
        this.movieXMLList.add(area);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (MovieXML xml:movieXMLList)
            builder.append(xml.toString());
        return builder.toString();
    }

    public List<MovieXML> getMovieXMLList(int start, int count) {
        List<MovieXML> result = new ArrayList<MovieXML>();
        int size = this.movieXMLList.size();
        if(start >= size){
            return result;
        }
        for (int i = start;i < size && i < start+count; i++) {
            result.add(this.movieXMLList.get(i));
        }
        return result;
    }

    public List<MovieXML> search(String keyword) {
        List<MovieXML> result = new ArrayList<MovieXML>();
        for(MovieXML movieXML:this.movieXMLList){
            // chinese name
            if(movieXML.getCname() != null && movieXML.getCname().contains(keyword)){
                result.add(movieXML);
                // english name
            } else if(movieXML.getEname() != null && movieXML.getEname().contains(keyword)){
                result.add(movieXML);
                // casts name
            } else if(movieXML.getCasts() != null && movieXML.getCasts().contains(keyword)){
                result.add(movieXML);
                // director
            } else if(movieXML.getDirectors() != null && movieXML.getDirectors().contains(keyword)){
                result.add(movieXML);
            }
        }
        return result;
    }

}

