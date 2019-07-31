package www.limo.com.mymovies.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WatchList {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "movieName")
    public String movieName;
    @ColumnInfo(name = "watched")
    public boolean isWatched;


    @Ignore
    public boolean isSelected;

    public WatchList() {
    }

    public WatchList(String movieName, boolean isWatched, boolean isSelected) {
        this.movieName = movieName;
        this.isWatched = isWatched;
        this.isSelected = isSelected;
    }

    public WatchList(int id, String movieName) {
        this.id = id;
        this.movieName = movieName;
        this.isWatched=false;
    }

    public WatchList(String movieName) {
        this.movieName = movieName;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isWatched() {
        return isWatched;
    }


    public void setWatched(boolean watched) {
        this.isWatched = watched;
    }
}
