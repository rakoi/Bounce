package www.limo.com.mymovies.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

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

    @Delete
    void deletemyMovie(MyMovie ...myMovies);

    @android.arch.persistence.room.Query("Update MyMovie SET episode=:newepisode,season=:newseason,name=:moviename where id=:movieId")
    void updatemyMovie(int movieId,String moviename,int newseason,int newepisode);


}
