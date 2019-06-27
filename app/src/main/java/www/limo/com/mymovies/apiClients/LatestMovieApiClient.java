package www.limo.com.mymovies.apiClients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LatestMovieApiClient {

    public static final  String url="http://api.tvmaze.com/";
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient() {
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
