package www.limo.com.mymovies.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MyMovie {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String movieName;
    @ColumnInfo(name = "season")
    public int season;
    @ColumnInfo(name = "episode")
    public int episode;

    @Ignore
    public boolean isSelected;

    public MyMovie() {
    }

    public MyMovie(int id, String movieName, int season, int episode) {
        this.id = id;
        this.movieName = movieName;
        this.season = season;
        this.episode = episode;
    }

    public MyMovie(String movieName, int season, int episode) {
        this.movieName = movieName;
        this.season = season;
        this.episode = episode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
