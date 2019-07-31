package www.limo.com.mymovies;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import www.limo.com.mymovies.entities.Movies;
import www.limo.com.mymovies.entities.MyMovie;
import www.limo.com.mymovies.entities.WatchList;


public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public NavigationPageAdapter navigationPageAdapter;
    public ViewPager viewPager;
    public int counter;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions gso;
    public FirebaseAuth mAuth;
    public ProgressDialog progressDialog;
    GoogleSignInAccount account;
    public String Uid;
    FirebaseDatabase database;
    DatabaseReference myRef ;
    private DatabaseReference MoviesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bounce");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewpager);


        tabLayout.addTab(tabLayout.newTab().setText("My"+System.getProperty("line.separator")+"Series"));
        tabLayout.addTab(tabLayout.newTab().setText("Watch"+System.getProperty("line.separator")+"List"));
        tabLayout.addTab(tabLayout.newTab().setText("New"+System.getProperty("line.separator")+"Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("Latest "+System.getProperty("line.separator")+"Episodes"));

        navigationPageAdapter=new NavigationPageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(navigationPageAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
          Uid = mAuth.getCurrentUser().getUid();

            myRef = database.getReference("userData/");
            myRef=myRef.child(Uid);




      }
    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the8 menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.signoutbtn:
                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                account = GoogleSignIn.getLastSignedInAccount(this);




                    //mAuth.signOut();

                mGoogleSignInClient.signOut();
                mAuth.signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));

                break;
            case R.id.faqBtn:
                startActivity(new Intent(this,FaqActivity.class));
                break;
            case R.id.backup:

                          uploadData();
                break;
            case R.id.downloadbackup:
                downloadBackUp();
                break;

                default:
                    break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    public void uploadData(){

        UploadAsynTask asynClass=new UploadAsynTask();
        asynClass.execute();


    }
    public void downloadBackUp(){
        donwloadAsynTask donwloadAsynTask=new donwloadAsynTask();
        donwloadAsynTask.execute();

       /* MoviesRef=myRef.child("myMovies");
        DatabaseReference WatchListRef = myRef.child(Uid).child("watchList");
        final AppDatabase myroomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();

        MoviesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MyMovie> databasemovies=new ArrayList<>();
                final AppDatabase myroomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();
                if(dataSnapshot!=null){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        MyMovie movie=ds.getValue(MyMovie.class);
                        databasemovies.add(movie);
                        try {
                            myroomdatabase.myMoviesDao().insertAll(movie);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }

                }else if(dataSnapshot==null){
                    Toast.makeText(MainActivity.this,"Un able to process",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

        WatchListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final AppDatabase myroomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();
                ArrayList<WatchList> databaseWatchList=new ArrayList<>();
                Log.d("myFb", "onDataChange: "+dataSnapshot);
                if(dataSnapshot!=null){

                    for(DataSnapshot ds:dataSnapshot.getChildren()){

                        WatchList watchListItem=ds.getValue(WatchList.class);
                        try {
                            myroomdatabase.myWatchListDao().saveWatchListItem(watchListItem);

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                    }
                }else if(dataSnapshot==null){
                    Toast.makeText(MainActivity.this,"Un able to process",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Un able to process",Toast.LENGTH_SHORT).show();
                Log.d("Data", "onCancelled: "+databaseError.toString());
            }
        });

*/
/*
       Intent intent = getIntent();
       finish();
        startActivity(intent);
*/




    }

    private class donwloadAsynTask extends AsyncTask<String,String,String> {
        String response;

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        @Override
        protected String doInBackground(String... strings) {
            MoviesRef=myRef.child("myMovies");
            DatabaseReference WatchListRef = myRef.child(Uid).child("watchList");
            final AppDatabase myroomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();


            if(!isNetworkAvailable()){
               response="Check network connection";
            }

            MoviesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<MyMovie> databasemovies=new ArrayList<>();
                    final AppDatabase myroomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();
                    if(dataSnapshot!=null){
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            MyMovie movie=ds.getValue(MyMovie.class);
                            databasemovies.add(movie);
                            response="Data Downloaded";
                            try {
                                myroomdatabase.myMoviesDao().insertAll(movie);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }

                    }else if(dataSnapshot==null){
                        response="something went wrong";
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    response="something went wrong";
                     }
            });
            WatchListRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    final AppDatabase myroomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();
                          if(dataSnapshot!=null){

                        for(DataSnapshot ds:dataSnapshot.getChildren()){

                            WatchList watchListItem=ds.getValue(WatchList.class);
                            try {
                                myroomdatabase.myWatchListDao().saveWatchListItem(watchListItem);

                            }catch (Exception ex){
                                ex.printStackTrace();
                            }


                        }
                    }else if(dataSnapshot==null){
                        response="Something went wrong";
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    response="Something went wrong";
                     }
            });

            return null;
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            if(!response.equals("Check network connection")){
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }



    }


        private class UploadAsynTask extends AsyncTask<String,String,String>{

        public String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        @Override
        protected String doInBackground(String... params) {
             AppDatabase roomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();
             List<MyMovie> myMovieArrayList=roomdatabase.myMoviesDao().getAllMyMovies();
            final String[] myresp = new String[1];

            myRef.getRef().child("myMovies").setValue(null);



            if(!isNetworkAvailable()){
                setResponse("Check Network Connection");
            }else{
                setResponse("Updated ");
            }

            if(myMovieArrayList.size()>0) {
                for (MyMovie movie : myMovieArrayList) {

                    myRef.child("myMovies").push().setValue(movie);

                }
            }


            List<WatchList> watchListList=roomdatabase.myWatchListDao().getAllWatchListItems();
            myRef.child("watchList").setValue(null);

            if(watchListList.size()>0) {
                for (WatchList watchList : watchListList) {

                    myRef.child("watchList").push().setValue(watchList).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            setResponse("Check Network Connection");
                        }
                    });

                }

            }




            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();


        }
        protected void onPreExecute() {
            progressDialog.show();
        }
    }
}

