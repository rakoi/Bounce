package www.limo.com.mymovies;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.limo.com.mymovies.entities.MyMovie;
import www.limo.com.mymovies.entities.WatchList;

public class MyMoviesAdapter extends RecyclerView.Adapter<MyMoviesAdapter.ViewHolder> {


    public ActionMode actionMode;
    public ArrayList<MyMovie> selectedItems=new ArrayList<>();
    public Context context;
    public AppDatabase database;
    public List<MyMovie> myMovieList;

    Activity activity;
    ArrayAdapter<CharSequence> episodesAdapter;
    ArrayAdapter<CharSequence> seasonAdapter;
    View view;

    public MyMoviesAdapter(List<MyMovie> myMovieList,Context context,Activity activity,View view) {
        this.myMovieList = myMovieList;
        this.context=context;
        this.activity=activity;
        this.view=view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_ticket,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MyMovie myMovie=myMovieList.get(position);
        String episode=String.valueOf(myMovie.getEpisode());
        final String season=String.valueOf(myMovie.getSeason());
        holder.myEpisode.setText(episode);
        holder.itemView.setBackgroundColor(myMovie.isSelected() ? Color.GRAY : Color.WHITE);

        holder.mymovieName.setText(myMovie.getMovieName());
        holder.mySeason.setText(season);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyMovie selectedMovie=myMovieList.get(position);
                togleState(selectedMovie);
                selectedItems.add(selectedMovie);
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyMovie clickedItem=myMovieList.get(position);
                //Toast.makeText(context,String.valueOf(clickedItem.isWatched),Toast.LENGTH_SHORT).show();
                if(selectedItems.size()>0&&selectedItems.contains(clickedItem)){
                    togleState(clickedItem);
                    selectedItems.remove(clickedItem);
                    notifyDataSetChanged();
                    if(selectedItems.size()==0){
                        actionMode.finish();
                    }
                }else if(selectedItems.size()==0){

                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                    final View popUpView=activity.getLayoutInflater().inflate(R.layout.add_movie,null);
                    final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
                    final Spinner episode=(Spinner) popUpView.findViewById(R.id.myEpisode);
                    final Spinner Season=popUpView.findViewById(R.id.mySeason);
                    seasonAdapter= ArrayAdapter.createFromResource(popupWindow.getContentView().getContext(),R.array.seasons,android.R.layout.simple_spinner_dropdown_item);
                    seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Season.setAdapter(seasonAdapter);

                    Button saveButton=(Button) popUpView.findViewById(R.id.saveMyMovie);
                    final EditText movieName=(EditText)popUpView.findViewById(R.id.myMoveName);
                    final TextView warning=popUpView.findViewById(R.id.addMovieWarning);

                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    saveButton.setText("Update");
                    movieName.setText(clickedItem.getMovieName());
                    database= Room.databaseBuilder(activity.getApplicationContext(),AppDatabase.class,"bounce").allowMainThreadQueries().build();


                    episodesAdapter= ArrayAdapter.createFromResource(popupWindow.getContentView().getContext(),R.array.episodes,android.R.layout.simple_spinner_dropdown_item);
                    episodesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    episode.setAdapter(episodesAdapter);

                    episode.setSelection(clickedItem.getEpisode());
                    Season.setSelection(clickedItem.getSeason()>0?clickedItem.getSeason()-1:clickedItem.getSeason());

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(movieName.getText().toString().equals("")){
                                warning.setText("Enter Movie Name");
                            }else {
                                String movieNameText=movieName.getText().toString();
                                int SeasonInt= Integer.parseInt(Season.getSelectedItem().toString());
                                int EpisodeInt= Integer.parseInt(episode.getSelectedItem().toString());
                                database.myMoviesDao().updatemyMovie(clickedItem.getId(),movieNameText,SeasonInt,EpisodeInt);
                                clickedItem.setEpisode(EpisodeInt);
                                clickedItem.setSeason(SeasonInt);
                                clickedItem.setMovieName(movieNameText);
                                notifyDataSetChanged();
                                popupWindow.dismiss();
                            }

                        }
                    });



                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return myMovieList.size();
    }


    public void togleState(MyMovie myMovie){

        myMovie.setSelected(!myMovie.isSelected());
        if(selectedItems.contains(myMovie)){
            selectedItems.remove(myMovie);

        }else{
            selectedItems.add(myMovie);
        }

        if(selectedItems.size()==0){
            actionMode.finish();
            return;
        }
        if(actionMode==null){

            actionMode = ((AppCompatActivity)context).startSupportActionMode(new MyListActionModeCallBack());
        }else{
            actionMode.invalidate();
        }
        notifyDataSetChanged();
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

    private class MyListActionModeCallBack implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.delete_mylist_item, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {


            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            database = Room.databaseBuilder(context, AppDatabase.class, "bounce").allowMainThreadQueries().build();


            if(item.getItemId()==R.id.deleteMyListItem) {
                for (int i = 0; i < selectedItems.size(); i++) {
                    MyMovie removeItem = selectedItems.get(i);
                    database.myMoviesDao().deletemyMovie(removeItem);
                    myMovieList.remove(removeItem);
                    notifyDataSetChanged();
                }
            }

            if(actionMode!=null){
                actionMode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {


            for(int i=0;i<selectedItems.size();i++){
                MyMovie item=selectedItems.get(i);
                item.setSelected(false);
                notifyDataSetChanged();

            }
            selectedItems.clear();
            actionMode=null;
            // Toast.makeText(context,String.valueOf(selectedItems.size()),Toast.LENGTH_LONG).show();
        }
    }

}
