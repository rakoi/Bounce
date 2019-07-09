package www.limo.com.mymovies.apiClients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMBNewMoviesApiClient {

    public static final  String url="https://api.themoviedb.org/3/movie/";
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient() {
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
