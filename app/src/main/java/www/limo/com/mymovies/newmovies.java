package www.limo.com.mymovies;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.limo.com.mymovies.apiClients.TMBLatestMoviesInterface;
import www.limo.com.mymovies.apiClients.TMBNewMoviesApiClient;
import www.limo.com.mymovies.entities.Result;
import www.limo.com.mymovies.entities.TMBMovie;


/**
 * A simple {@link Fragment} subclass.
 */
public class newmovies extends Fragment {


    public String TAG="MSG";

    public List<TMBMovie> latestMoviesArrayList=new ArrayList<>();
    public TMBLatestMoviesInterface latestMoviesInterface;
    public RecyclerView recyclerView;
    public NewMovieRecyclerAdapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    public ShimmerFrameLayout shimmerFrameLayout;
    public ProgressDialog progressDialog;
    public List<Result> resultList;
    public int pagecount;

    public newmovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_newmovies, container, false);


        latestMoviesInterface= TMBNewMoviesApiClient.getApiClient().create(TMBLatestMoviesInterface.class);
        recyclerView=view.findViewById(R.id.moviesRecyclerView);
        layoutManager=new GridLayoutManager(getContext(),3);
        int randomPage=new Random().nextInt(9)+1;
        pagecount=1;
        Call<JsonElement> call=latestMoviesInterface.getLatestMovies(pagecount);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try{
                    JsonElement data=response.body().getAsJsonObject().get("results");

                    Type listType = new TypeToken<List<Result>>() {}.getType();
                     resultList = new Gson().fromJson(data, listType);
                    adapter=new NewMovieRecyclerAdapter(resultList,getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                }catch (Exception ex){
                    Toast.makeText(getContext(),"Server Error",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getContext(),"Check your connections and try again",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    pagecount++;
                    getData();
                }
            }
        });

        return view;
    }
    public void getData(){
        Call<JsonElement> moredatacall=latestMoviesInterface.getLatestMovies(pagecount);
        moredatacall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {
                    JsonElement data=response.body().getAsJsonObject().get("results");
                    Type listType = new TypeToken<List<Result>>() {}.getType();
                    List<Result> moredata = new Gson().fromJson(data, listType);
                    resultList.addAll(moredata);
                    adapter.notifyDataSetChanged();
                }catch (Exception ex){

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

}
