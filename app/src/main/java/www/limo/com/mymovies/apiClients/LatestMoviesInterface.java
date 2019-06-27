package www.limo.com.mymovies.apiClients;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import www.limo.com.mymovies.entities.Movies;

public interface LatestMoviesInterface  {

    @GET("/schedule")
    Call<List<Movies>> getLatestMovies();
}
