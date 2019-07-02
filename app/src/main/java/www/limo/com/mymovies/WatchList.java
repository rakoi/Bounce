package www.limo.com.mymovies;


import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.List;

import www.limo.com.mymovies.Dao.WatchListDao;


/**
 * A simple {@link Fragment} subclass.
 */
public class WatchList extends Fragment {

    public FloatingActionButton addWatchListFab;
    public RecyclerView myWatchListRecyclerView;
    public AppDatabase database;
    public List<www.limo.com.mymovies.entities.WatchList> myWatchList;
    public PopupWindow popupWindow;
    public View popupView;
    public int width,height;
    public boolean focusable=true;
    public WatchList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_watch_list, container, false);


        addWatchListFab=view.findViewById(R.id.addWatchListFab);
        myWatchListRecyclerView=view.findViewById(R.id.myWatchListRecyclerView);
        database= Room.databaseBuilder(getActivity().getApplicationContext(),AppDatabase.class,"bounce").allowMainThreadQueries().build();
        myWatchList=database.myWatchListDao().getAllWatchListItems();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        final WatchListRecyclerAdapter watchListRecyclerAdapter=new WatchListRecyclerAdapter(myWatchList,getContext());
        myWatchListRecyclerView.setAdapter(watchListRecyclerAdapter);
        myWatchListRecyclerView.setLayoutManager(layoutManager);

        popupView=getLayoutInflater().inflate(R.layout.add_watch_list,null);
        width = LinearLayout.LayoutParams.WRAP_CONTENT;
        height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow=new PopupWindow(popupView,width,height,focusable);
        Button saveWatchListItemBtn=popupView.findViewById(R.id.saveWatchListItem);
        final EditText watchListItemName=popupView.findViewById(R.id.AddwatchListMovieName);




        saveWatchListItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(watchListItemName.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Add Movie Name",Toast.LENGTH_SHORT).show();
                }else{
                    String movieName=watchListItemName.getText().toString();
                    www.limo.com.mymovies.entities.WatchList watchList = new www.limo.com.mymovies.entities.WatchList(movieName);

                    database.myWatchListDao().saveWatchListItem(watchList);
                    myWatchList.add(watchList);
                    watchListRecyclerAdapter.notifyDataSetChanged();
                    watchListItemName.setText("");
                    popupWindow.dismiss();

                }
            }
        });
        addWatchListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            }
        });




        return view;
    }

}
