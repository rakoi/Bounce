package www.limo.com.mymovies;

import android.graphics.Color;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import www.limo.com.mymovies.entities.Movies;

public class NewMoviesRecyclerAdapter extends RecyclerView.Adapter<NewMoviesRecyclerAdapter.ViewHolder> {

    List<Movies> moviesArrayList;

    public NewMoviesRecyclerAdapter(List<Movies> movies) {
        moviesArrayList=movies;
     }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_ticket,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

       Movies movies=moviesArrayList.get(position);
       String season=String.valueOf(movies.getSeason());

        String episode=String.valueOf(movies.getNumber());
      if(movies.getShow().getImage()!=null){
          Picasso.get().load(movies.getShow().getImage().getOriginal()).into(holder.movieImage);
          Picasso.get().load(movies.getShow().getImage().getMedium()).into(holder.MediumImage);

      }
      holder.newMovieTitle.setText(movies.getShow().getName());

      holder.moviename.setText(movies.getShow().getName());
         holder.season.setText(season);
        holder.episode.setText(episode);
            holder.summary.setText(android.text.Html.fromHtml(movies.getShow().getSummary()));
            if(movies.getShow().getGenres().length>0){
            String[] genre=movies.getShow().getGenres();
                holder.genre.setText(genre[0]);
            }else{
                holder.genre.setText("Unknown");
            }




        if(!movies.getShow().getSummary().equals("")||movies.getShow().getSummary().isEmpty()){
            holder.fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.fc.toggle(false);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView newMovieTitle;
        ImageView movieImage;
        ImageView MediumImage;
        TextView moviename;
        TextView episode;
        TextView season;
        TextView genre;
        TextView summary;
        FoldingCell fc;
        public ViewHolder(View itemView) {

            super(itemView);
            newMovieTitle=itemView.findViewById(R.id.newMovieTitle);
            movieImage=itemView.findViewById(R.id.newImage);
            moviename=itemView.findViewById(R.id.newMovieName);
            episode=itemView.findViewById(R.id.Episode);
            season=itemView.findViewById(R.id.Season);
            genre=itemView.findViewById(R.id.genre);
            summary=itemView.findViewById(R.id.Summary);
            MediumImage=itemView.findViewById(R.id.movieImage);
          fc=itemView.findViewById(R.id.folding_cell);

        }
    }
}
