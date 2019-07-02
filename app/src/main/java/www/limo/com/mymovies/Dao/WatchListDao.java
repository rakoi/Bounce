package www.limo.com.mymovies.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import www.limo.com.mymovies.entities.WatchList;

@Dao
public interface WatchListDao {

    @Query("Select * from WatchList")
    List<WatchList> getAllWatchListItems();

    @Insert
    void saveWatchListItem(WatchList ...watchList);

    @Delete
    void deleteWatchListItem(WatchList... watchList);

    @Query("Update WatchList SET watched=:status where id=:watchListId")
    void updateWatchedStatus(int watchListId,boolean status);
}
