package www.limo.com.mymovies;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import www.limo.com.mymovies.Dao.MyMoviesDao;
import www.limo.com.mymovies.Dao.WatchListDao;
import www.limo.com.mymovies.entities.MyMovie;
import www.limo.com.mymovies.entities.WatchList;

@Database(entities = {MyMovie.class, WatchList.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyMoviesDao myMoviesDao();
    public abstract WatchListDao myWatchListDao();
}
