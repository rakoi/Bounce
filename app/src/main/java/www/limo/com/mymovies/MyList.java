package www.limo.com.mymovies;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.limo.com.mymovies.entities.MyMovie;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyList extends Fragment {

    public  AppDatabase database;
    RecyclerView recyclerView;
    public MyMoviesAdapter moviesRecyclerAdapter;
    public List<MyMovie> myMovieArrayList;
    public Spinner episode;
    public Spinner Season;
    ArrayAdapter<CharSequence> episodesAdapter;
    ArrayAdapter<CharSequence> seasonAdapter;
    public MyList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_my_list, container, false);
        FloatingActionButton addMovieFab=view.findViewById(R.id.addMovieFab);

         recyclerView=view.findViewById(R.id.myMoviesRecyclerView);
         database= Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,"bounce").allowMainThreadQueries().build();
         myMovieArrayList=database.myMoviesDao().getAllMyMovies();
         Log.d("Movies Size", String.valueOf(myMovieArrayList.size()));
          moviesRecyclerAdapter=new MyMoviesAdapter(myMovieArrayList);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setAdapter(moviesRecyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        final View popUpView=getActivity().getLayoutInflater().inflate(R.layout.add_movie,null);
        final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);

        episode=(Spinner) popUpView.findViewById(R.id.myEpisode);
        Season=popUpView.findViewById(R.id.mySeason);

        episodesAdapter= ArrayAdapter.createFromResource(popupWindow.getContentView().getContext(),R.array.episodes,android.R.layout.simple_spinner_dropdown_item);
        episodesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        episode.setAdapter(episodesAdapter);

        seasonAdapter= ArrayAdapter.createFromResource(popupWindow.getContentView().getContext(),R.array.seasons,android.R.layout.simple_spinner_dropdown_item);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Season.setAdapter(seasonAdapter);

        Button saveButton=(Button) popUpView.findViewById(R.id.saveMyMovie);
        final EditText movieName=(EditText)popUpView.findViewById(R.id.myMoveName);


             //   final EditText Season=(EditText)popUpView.findViewById(R.id.mySeason);
               //         final EditText episode=(EditText)popUpView.findViewById(R.id.myEpisode);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(movieName.getText().equals("")){
                    Toast.makeText(getActivity().getApplicationContext(),"Enter All Values",Toast.LENGTH_LONG).show();
                }else {
                    String movieNameText=movieName.getText().toString();
                    int SeasonInt= Integer.parseInt(Season.getSelectedItem().toString());
                    int EpisodeInt= Integer.parseInt(episode.getSelectedItem().toString());
                    MyMovie myMovie=new MyMovie(movieNameText,SeasonInt,EpisodeInt);
                    myMovieArrayList.add(myMovie);
                    moviesRecyclerAdapter.notifyDataSetChanged();
                    database.myMoviesDao().insertAll(myMovie);
                    movieName.setText("");
                    Season.setSelection(0);
                    episode.setSelection(0);
                    popupWindow.dismiss();
                }


            }
        });


        addMovieFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            }
        });
    return view;
    }

}
