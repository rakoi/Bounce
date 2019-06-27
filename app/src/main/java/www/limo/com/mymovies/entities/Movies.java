package www.limo.com.mymovies.entities;

import java.util.*;

public class Movies {

    private long id;
    private String url;
    private String name;
    private long season;
    private long number;
    private String airdate;
    private String airtime;
    private String airstamp;
    private long runtime;
    private Image image;
    private Object summary;
    private Show show;
    private MoviesLinks links;

    public Movies() {
    }

    public Movies(long id, String url, String name, long season, long number, String airdate, String airtime, String airstamp, long runtime, Image image, Object summary, Show show, MoviesLinks links) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.season = season;
        this.number = number;
        this.airdate = airdate;
        this.airtime = airtime;
        this.airstamp = airstamp;
        this.runtime = runtime;
        this.image = image;
        this.summary = summary;
        this.show = show;
        this.links = links;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSeason() {
        return season;
    }

    public void setSeason(long season) {
        this.season = season;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getAirstamp() {
        return airstamp;
    }

    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Object getSummary() {
        return summary;
    }

    public void setSummary(Object summary) {
        this.summary = summary;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public MoviesLinks getLinks() {
        return links;
    }

    public void setLinks(MoviesLinks links) {
        this.links = links;
    }
}
