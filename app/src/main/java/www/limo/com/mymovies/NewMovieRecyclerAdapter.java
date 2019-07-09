package www.limo.com.mymovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import www.limo.com.mymovies.entities.Result;
import www.limo.com.mymovies.entities.TMBMovie;

public class NewMovieRecyclerAdapter extends RecyclerView.Adapter<NewMovieRecyclerAdapter.NewMoviesViewHolder> {

    public List<Result> newmoviesresult;
    public Context context;

    public NewMovieRecyclerAdapter(List<Result> newmovies, Context context) {
        this.newmoviesresult = newmovies;
        this.context = context;
    }

    @NonNull
    @Override
    public NewMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_movie_ticket,parent,false);

        NewMoviesViewHolder viewHolder=new NewMoviesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewMoviesViewHolder holder, int position) {


        final Result result=newmoviesresult.get(position);
        String imageUrl="https://image.tmdb.org/t/p/w500/"+result.getPosterPath();
        Picasso.get().load(imageUrl).into(holder.movieimage);

        holder.movieName.setText(result.getOriginalTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson=new Gson();
                String movie=gson.toJson(result);
                Intent intent=new Intent(context,SingleMovieActivity.class);
                intent.putExtra("movie",movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newmoviesresult.size();
    }

    public class NewMoviesViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieimage;
        public TextView movieName;
        public NewMoviesViewHolder(View itemView) {
            super(itemView);
            movieimage=itemView.findViewById(R.id.newmoimage);
            movieName=itemView.findViewById(R.id.newmoviename);
        }
    }
}
