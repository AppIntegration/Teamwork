package po;

/**
 * Created by kylin on 11/06/2017.
 * All rights reserved.
 */
public class MovieXML {

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

    private String genres;
    private String casts;
    private String directors;
    private String writers;

    private String etagline;
    private String ctagline;

    private String smallImage;
    private String mediumImage;
    private String bigImage;

    public MovieXML() {
    }

    public MovieXML(String imdbId, String doubanId, String ename, String cname, int year, double doubanScore, int doubanVote, double imdbScore, int imdbVote, double timeScore, int timeVote, String genres, String casts, String directors, String writers, String etagline, String ctagline, String smallImage, String mediumImage, String bigImage) {
        this.imdbId = imdbId;
        this.doubanId = doubanId;
        this.ename = ename;
        this.cname = cname;
        this.year = year;
        this.doubanScore = doubanScore;
        this.doubanVote = doubanVote;
        this.imdbScore = imdbScore;
        this.imdbVote = imdbVote;
        this.timeScore = timeScore;
        this.timeVote = timeVote;
        this.genres = genres;
        this.casts = casts;
        this.directors = directors;
        this.writers = writers;
        this.etagline = etagline;
        this.ctagline = ctagline;
        this.smallImage = smallImage;
        this.mediumImage = mediumImage;
        this.bigImage = bigImage;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String writers) {
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
        return "MovieXML{" +
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
                ", genres='" + genres + '\'' +
                ", casts='" + casts + '\'' +
                ", directors='" + directors + '\'' +
                ", writers='" + writers + '\'' +
                ", etagline='" + etagline + '\'' +
                ", ctagline='" + ctagline + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", mediumImage='" + mediumImage + '\'' +
                ", bigImage='" + bigImage + '\'' +
                '}'+"\n";
    }
}
