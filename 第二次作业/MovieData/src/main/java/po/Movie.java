package po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public class Movie {

    private String imdbId;
    private String doubanId;

    private String ename;
    private String cname;

    private int year;

    private double doubanScore;
    private int doubanVote;
    private double imdbScore;
    private int imdbVote;
    private double timeScore;
    private int timeVote;

    private List<String> genres;
    private List<String> casts;
    private List<String> directors;
    private List<String> writers;

    private String etagline;
    private String ctagline;

    private String smallImage;
    private String mediumImage;
    private String bigImage;

    public Movie(MovieXML movieXML) {
        this.imdbId = movieXML.getImdbId();
        this.doubanId = movieXML.getDoubanId();
        this.ename = movieXML.getEname();
        this.cname = movieXML.getCname();

        this.year = movieXML.getYear();
        this.doubanScore = movieXML.getDoubanScore();
        this.doubanVote = movieXML.getDoubanVote();
        this.imdbVote = movieXML.getImdbVote();
        this.imdbScore = movieXML.getImdbScore();
        this.timeScore = movieXML.getTimeScore();
        this.timeVote = movieXML.getTimeVote();

        this.etagline = movieXML.getEtagline();
        this.ctagline = movieXML.getCtagline();
        this.smallImage = movieXML.getSmallImage();
        this.mediumImage = movieXML.getMediumImage();
        this.bigImage = movieXML.getBigImage();

        this.genres = this.convert(movieXML.getGenres());
        this.casts = this.convert(movieXML.getCasts());
        this.directors = this.convert(movieXML.getDirectors());
        this.writers = this.convert(movieXML.getWriters());
    }

    private List<String> convert(String genres) {
        if(genres == null || genres.length() <= 1){
            return new ArrayList<String>();
        }
        String[] splits = genres.split(";");

        List<String> result = new ArrayList<String>();
        for(String one:splits){
            result.add(one);
        }
        return result;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getDoubanScore() {
        return doubanScore;
    }

    public void setDoubanScore(double doubanScore) {
        this.doubanScore = doubanScore;
    }

    public int getDoubanVote() {
        return doubanVote;
    }

    public void setDoubanVote(int doubanVote) {
        this.doubanVote = doubanVote;
    }

    public double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(double imdbScore) {
        this.imdbScore = imdbScore;
    }

    public int getImdbVote() {
        return imdbVote;
    }

    public void setImdbVote(int imdbVote) {
        this.imdbVote = imdbVote;
    }

    public double getTimeScore() {
        return timeScore;
    }

    public void setTimeScore(double timeScore) {
        this.timeScore = timeScore;
    }

    public int getTimeVote() {
        return timeVote;
    }

    public void setTimeVote(int timeVote) {
        this.timeVote = timeVote;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getCasts() {
        return casts;
    }

    public void setCasts(List<String> casts) {
        this.casts = casts;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public String getEtagline() {
        return etagline;
    }

    public void setEtagline(String etagline) {
        this.etagline = etagline;
    }

    public String getCtagline() {
        return ctagline;
    }

    public void setCtagline(String ctagline) {
        this.ctagline = ctagline;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "imdbId='" + imdbId + '\'' +
                ", doubanId='" + doubanId + '\'' +
                ", ename='" + ename + '\'' +
                ", cname='" + cname + '\'' +
                ", year=" + year +
                ", doubanScore=" + doubanScore +
                ", doubanVote=" + doubanVote +
                ", imdbScore=" + imdbScore +
                ", imdbVote=" + imdbVote +
                ", timeScore=" + timeScore +
                ", timeVote=" + timeVote +
                ", genres=" + genres +
                ", casts=" + casts +
                ", directors=" + directors +
                ", writers=" + writers +
                ", etagline='" + etagline + '\'' +
                ", ctagline='" + ctagline + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", mediumImage='" + mediumImage + '\'' +
                ", bigImage='" + bigImage + '\'' +
                '}';
    }
}
