package www.limo.com.mymovies.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import java.util.List;

import retrofit2.http.Query;
import www.limo.com.mymovies.entities.Movies;
import www.limo.com.mymovies.entities.MyMovie;

@Dao
public interface MyMoviesDao {

    @android.arch.persistence.room.Query("SELECT * FROM MyMovie")
    List<MyMovie> getAllMyMovies();

    @Insert
    void insertAll(MyMovie ...myMovies);



}
