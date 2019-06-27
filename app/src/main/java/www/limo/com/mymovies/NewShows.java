package www.limo.com.mymovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.limo.com.mymovies.apiClients.LatestMovieApiClient;
import www.limo.com.mymovies.apiClients.LatestMoviesInterface;
import www.limo.com.mymovies.entities.Movies;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewShows extends Fragment {

    public List<Movies> latestMoviesArrayList=new ArrayList<>();
    public LatestMoviesInterface latestMoviesInterface;
    public RecyclerView.LayoutManager layoutManager;
    public ShimmerFrameLayout shimmerFrameLayout;


    public RecyclerView recyclerView;
    public NewMoviesRecyclerAdapter newMoviesRecyclerAdapter;
    public NewShows() {
        // Required empty public constructor
    }

    public void setLatestMoviesArrayList(List<Movies> latestMoviesArrayList) {
        this.latestMoviesArrayList = latestMoviesArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_new_shows, container, false);
         latestMoviesInterface= LatestMovieApiClient.getApiClient().create(LatestMoviesInterface.class);
        recyclerView=view.findViewById(R.id.newShowsRecyclerView);
        layoutManager=new LinearLayoutManager(getContext());

         shimmerFrameLayout =view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();


        Call<List<Movies>> call=latestMoviesInterface.getLatestMovies();
        call.enqueue(new Callback<List<Movies>>() {
            @Override
            public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {

                newMoviesRecyclerAdapter=new NewMoviesRecyclerAdapter(response.body());
                recyclerView.setAdapter(newMoviesRecyclerAdapter);
                recyclerView.setLayoutManager(layoutManager);
                shimmerFrameLayout.setRepeatDelay(12);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Movies>> call, Throwable t) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Check network Connection",Toast.LENGTH_LONG).show();
            }
        });

        Log.d("Fragment ",String.valueOf(latestMoviesArrayList.size()));





        return view;
    }
    public void getData(){
         List<Movies> data=new ArrayList();

    }

}
