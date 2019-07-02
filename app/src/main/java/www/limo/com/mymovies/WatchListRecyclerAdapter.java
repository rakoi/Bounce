package www.limo.com.mymovies;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.limo.com.mymovies.entities.WatchList;

class WatchListRecyclerAdapter extends RecyclerView.Adapter<WatchListRecyclerAdapter.WatchListViewHolder> {

    public ArrayList<WatchList> selectedItems=new ArrayList<>();

    List<WatchList> myWatchList;
    Context context;
    public  AppDatabase database;
    public ActionMode actionMode;

    public WatchListRecyclerAdapter(List<WatchList> myWatchList, Context context) {
        this.myWatchList = myWatchList;
        this.context = context;
    }

    @NonNull
    @Override
    public WatchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_list_item,parent,false);

        WatchListRecyclerAdapter.WatchListViewHolder viewHolder=new WatchListRecyclerAdapter.WatchListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WatchListViewHolder holder, final int position) {

        final WatchList watchList=myWatchList.get(position);
        holder.watchListMovieName.setText(watchList.getMovieName());
        holder.itemView.setBackgroundColor(watchList.isSelected() ? Color.GRAY : Color.WHITE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchList clickedItem=myWatchList.get(position);
                if(selectedItems.size()>0&&selectedItems.contains(clickedItem)){
                    togleState(clickedItem);
                    selectedItems.remove(clickedItem);
                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                WatchList selectedItem=myWatchList.get(position);

                    togleState(selectedItem);


                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return myWatchList.size();
    }

    public void togleState(WatchList watchList){


        if(selectedItems.contains(watchList)){
            selectedItems.remove(watchList);
            watchList.setSelected(!watchList.isSelected());
        }else{
            selectedItems.add(watchList);
            watchList.setSelected(!watchList.isSelected());
        }

        if(selectedItems.size()==0&actionMode!=null){
            actionMode.finish();
            return;
        }
        if(actionMode==null){

            actionMode = ((AppCompatActivity)context).startSupportActionMode(new WatchListActionModeCallBack());
        }else{
            actionMode.invalidate();
        }
        notifyDataSetChanged();
    }

    public class WatchListViewHolder extends RecyclerView.ViewHolder {

        TextView watchListMovieName;

        public WatchListViewHolder(View itemView) {
            super(itemView);
            watchListMovieName=itemView.findViewById(R.id.watchListMovieName);

        }

    }
    private class WatchListActionModeCallBack implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {


            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            database = Room.databaseBuilder(context, AppDatabase.class, "bounce").allowMainThreadQueries().build();


            if(item.getItemId()==R.id.deleteWatchListItem) {
                for (int i = 0; i < selectedItems.size(); i++) {
                    WatchList removeItem = selectedItems.get(i);
                    database.myWatchListDao().deleteWatchListItem(removeItem);
                    myWatchList.remove(removeItem);
                    notifyDataSetChanged();
                }
            }else if(item.getItemId()==R.id.makedAsWatched){
                for (int i = 0; i < selectedItems.size(); i++) {
                    WatchList watchListItem = selectedItems.get(i);
                    database.myWatchListDao().updateWatchedStatus(watchListItem.getId(),watchListItem.isSelected);
                    notifyDataSetChanged();
                }

            }
            actionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mode=null;
            selectedItems.clear();
            actionMode=null;
        }
    }
}
