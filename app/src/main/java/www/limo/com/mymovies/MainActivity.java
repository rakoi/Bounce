package www.limo.com.mymovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public NavigationPageAdapter navigationPageAdapter;
    public ViewPager viewPager;
    public GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInOptions gso;
    public FirebaseAuth mAuth;
    GoogleSignInAccount account;
    public String Uid;
    FirebaseDatabase database;
    DatabaseReference myRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bounce");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();
         if(mAuth!=null) {
             //Uid = mAuth.getCurrentUser().getEmail();
         }myRef = database.getReference("userData");




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
        Toast.makeText(MainActivity.this,Uid,Toast.LENGTH_LONG).show();
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
                mGoogleSignInClient.signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.faqBtn:
                startActivity(new Intent(this,FaqActivity.class));
                break;
            case R.id.backup:
                    uploadData();
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

        AppDatabase roomdatabase = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "bounce").allowMainThreadQueries().build();
        List<MyMovie> myMovieArrayList=roomdatabase.myMoviesDao().getAllMyMovies();

        for (MyMovie movie:myMovieArrayList){
            String movieId=String.valueOf(movie.getId());
            myRef.child(Uid).child("MyMovie").setValue("12");

        }


    }
}
