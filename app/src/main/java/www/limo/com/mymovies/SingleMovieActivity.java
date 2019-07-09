package www.limo.com.mymovies;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import www.limo.com.mymovies.entities.Result;
import www.limo.com.mymovies.entities.WatchList;

public class SingleMovieActivity extends AppCompatActivity {

    public ImageView backdrop;
    public TextView releaseDate;
    public  TextView rating;
    public TextView Summary;
    public TextView genre;
    public Button addtowatchList;
    public AppDatabase database;
    public TextView singleMovieName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_movie_ticket);

        genre=findViewById(R.id.singlemoviegenre);
        backdrop=findViewById(R.id.backdrop);
        releaseDate=findViewById(R.id.releasedate);
        rating=findViewById(R.id.rating);
        Summary=findViewById(R.id.movieSummary);
        singleMovieName=findViewById(R.id.singleMovieName);
        addtowatchList=findViewById(R.id.addToWatchListbtn);

        Gson gson=new Gson();
        final Result result=gson.fromJson(getIntent().getExtras().getString("movie"),Result.class);

        String imageUrl="https://image.tmdb.org/t/p/w500/"+result.getBackdropPath();
        Picasso.get().load(imageUrl).into(backdrop);
        releaseDate.setText(result.getReleaseDate());
        genre.setText("Unknown");
        String avg=String.valueOf(Math.round(result.getVoteAverage()));
        rating.setText(avg);
        Summary.setText(result.getOverview());
        singleMovieName.setText(result.getTitle());

        addtowatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                www.limo.com.mymovies.entities.WatchList watchList=new WatchList(result.getOriginalTitle());
                database= Room.databaseBuilder(SingleMovieActivity.this,AppDatabase.class,"bounce").allowMainThreadQueries().build();
                database.myWatchListDao().saveWatchListItem(watchList);

                Toast.makeText(SingleMovieActivity.this,"Item Added !",Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }
}
