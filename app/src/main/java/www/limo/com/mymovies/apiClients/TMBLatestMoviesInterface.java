package www.limo.com.mymovies.apiClients;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import www.limo.com.mymovies.entities.Result;
import www.limo.com.mymovies.entities.TMBMovie;

public interface TMBLatestMoviesInterface {

    @GET("upcoming?api_key=ae120ab4a7576f2fd899c48023fcbb60&language=en-US")
    Call<JsonElement> getLatestMovies(@Query("page") int page);


}
