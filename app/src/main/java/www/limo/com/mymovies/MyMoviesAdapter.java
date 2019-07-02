package www.limo.com.mymovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.limo.com.mymovies.entities.MyMovie;

public class MyMoviesAdapter extends RecyclerView.Adapter<MyMoviesAdapter.ViewHolder> {


    public List<MyMovie> myMovieList;

    public MyMoviesAdapter(List<MyMovie> myMovieList) {
        this.myMovieList = myMovieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_ticket,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyMovie myMovie=myMovieList.get(position);
        String episode=String.valueOf(myMovie.getEpisode());
        String season=String.valueOf(myMovie.getSeason());
        holder.myEpisode.setText(episode);
        holder.mymovieName.setText(myMovie.getMovieName());
        holder.mySeason.setText(season);
    }

    @Override
    public int getItemCount() {
        return myMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView myMovieImage;
        TextView mymovieName;
        TextView myEpisode;
        TextView mySeason;

        public ViewHolder(View itemView) {
            super(itemView);
            myMovieImage=itemView.findViewById(R.id.myMovieImage);
            mymovieName=itemView.findViewById(R.id.mySavedMoveName);
            myEpisode=itemView.findViewById(R.id.myLatestEp);
            mySeason=itemView.findViewById(R.id.myLatestSn);
        }
    }

}
