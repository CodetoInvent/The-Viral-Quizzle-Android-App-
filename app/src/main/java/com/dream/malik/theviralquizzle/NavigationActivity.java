package com.dream.malik.theviralquizzle;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dream.malik.theviralquizzle.Common.Common;
import com.dream.malik.theviralquizzle.model.Question;
import com.dream.malik.theviralquizzle.utils.SharedPrefManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;


public class NavigationActivity extends AppCompatActivity {
    CircleImageView imageView;
    TextView textName, textEmail;
    FirebaseAuth mAuth;
    android.support.v7.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    Context mContext = this;
    FirebaseDatabase database;
    DatabaseReference questions; //To read or write data from the database, we need an instance of DatabaseReference

    SharedPrefManager sharedPrefManager;
    FirebaseUser firebaseUser;
    BottomNavigationView bottomNavigationView;
    Start start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dream.malik.theviralquizzle.R.layout.activity_navigation);
        toolbar =  findViewById(com.dream.malik.theviralquizzle.R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        isNetworkAvailable();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

         database= FirebaseDatabase.getInstance();
        questions=database.getReference("Questions");
        loadQuestion(Common.categoryId);



       navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);

        textName = header.findViewById(com.dream.malik.theviralquizzle.R.id.textViewName);
        textEmail=  header.findViewById(com.dream.malik.theviralquizzle.R.id.textViewEmail);
        imageView =  header.findViewById(com.dream.malik.theviralquizzle.R.id.imageView);

        // create an object of sharedPreferenceManager and get stored user data
        sharedPrefManager = new SharedPrefManager(mContext);
        String mUsername = sharedPrefManager.getName();
        String mEmail = sharedPrefManager.getUserEmail();
         mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        //Set data gotten from SharedPreference to the Navigation Header view
        textName.setText(firebaseUser != null ? firebaseUser.getDisplayName() : null);//it will display user name on nav header
        textEmail.setText(firebaseUser.getEmail());//it will display email on nav header
        Picasso.with(this)//this will load the image of user on nav header
                .load(firebaseUser.getPhotoUrl())
                .into(imageView);

bottomNavigationView=findViewById(com.dream.malik.theviralquizzle.R.id.navigation);
bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        Fragment selectedFrgment;
        switch (item.getItemId()){
            case com.dream.malik.theviralquizzle.R.id.action_category://we will display our category Fragment in first item (Category Fragment)
                selectedFrgment=CategoryFragment.newInstance();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(com.dream.malik.theviralquizzle.R.id.frame_layout,selectedFrgment);
                transaction.commit();
                break;
            case com.dream.malik.theviralquizzle.R.id.action_ranking://this is the ranking activity of second item(LeaderBoard)
                Intent intent=new Intent(NavigationActivity.this,LeaderBoard.class);
                startActivity(intent);
                break;
        }


    }
});

setDefaultFragment();

    }



    private void loadQuestion(String categoryId) {//method to check questions and load it
        Collections.shuffle(Common.questionList);//tto shuffle questions each time we play
        if(Common.questionList.size()>0)//if we nave questions then this logic will works
            Common.questionList.clear();//clearing the old questions in our mobile while we update new questions in our database
        questions.orderByChild("categoryId").equalTo(categoryId)//it is a child  in our database
                //when our Questions categoryId =the CategoryId of Our Category  acc to  that Questions will shown
                .addValueEventListener(new ValueEventListener() {
                    //To read data at a path and listen for changes, we use the addValueEventListener()( DatabaseReference)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            /*
                            A DataSnapshot instance contains data from a Firebase Database location.
                             Any time you read Database data, you receive the data as a DataSnapshot
                             */
                            Question ques=postSnapshot.getValue(Question.class);//we made the Object of our Question class(dynamic)
                            Common.questionList.add(ques);
                        }
                        Collections.shuffle(Common.questionList);//to shuffle questions

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        //This will generate random list questions
        Collections.shuffle(Common.questionList);


    }



    private void setDefaultFragment() {//method for  setting default Fragment
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(com.dream.malik.theviralquizzle.R.id.frame_layout,CategoryFragment.newInstance());
        transaction.commit();

    }


    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (firebaseUser == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }
    }
    @Override
    protected void onStop() {
        super.onStop();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(com.dream.malik.theviralquizzle.R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
            super.onBackPressed();


    }

    // Initialize and add Listener to NavigationDrawer
    public void initNavigationDrawer(){


        navigationView =  findViewById(com.dream.malik.theviralquizzle.R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id){
                    //we can select the items of Navigation drawer
                    case com.dream.malik.theviralquizzle.R.id.moreapps:

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=MOHAMMAD ABDUL MALIK")));

                           } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:MOHAMMAD ABDUL MALIK")));

                        }

                        //Toast.makeText(getApplicationContext(),"More Apps",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case com.dream.malik.theviralquizzle.R.id.leaderboard:
                       // Toast.makeText(getApplicationContext(),"Leader Board",Toast.LENGTH_SHORT).show();
                        Intent intent3=new Intent(NavigationActivity.this,LeaderBoard.class);
                       startActivity(intent3);
                        drawerLayout.closeDrawers();
                        break;
                    case com.dream.malik.theviralquizzle.R.id.share:
                      //  Toast.makeText(getApplicationContext(),"share our app",Toast.LENGTH_SHORT).show();
                        try {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, "The Viral Quizzle[online]");
                            String sAux = "\nLet's try this Awesome Viral Quiz App \n\n";
                            sAux = sAux + "https://play.google.com/store/apps/details?id=com.dream.malik.theviralquizzle \n\n";
                            i.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(Intent.createChooser(i, "choose one"));
                        } catch(Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.dream.malik.theviralquizzle" )));

                        }


                        drawerLayout.closeDrawers();
                        break;
                    case com.dream.malik.theviralquizzle.R.id.logout:
                        if(isNetworkAvailable()){
                            mAuth.signOut();
                            finish();
                            startActivity(new Intent(NavigationActivity.this,MainActivity.class));
                        }else{
                        Toast.makeText(getApplicationContext(),"oops no internet connection",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();}
                        break;

                    case com.dream.malik.theviralquizzle.R.id.settings:
                      //  Toast.makeText(getApplicationContext(),"settings",Toast.LENGTH_SHORT).show();
                        Intent intent12=new Intent(NavigationActivity.this,SettingsActivity.class);
                        startActivity(intent12);


                        drawerLayout.closeDrawers();
                        break;
                    case com.dream.malik.theviralquizzle.R.id.feedback:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("mail to:"));
                        String[] recipents = {"092abdulmalik@gmail.com"};
                        intent.setType("message/rfc822");
                        intent.putExtra(Intent.EXTRA_EMAIL, recipents);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "The Viral Quizzle");
                        Intent chooser = Intent.createChooser(intent, "Send Feedback Via");
                        startActivity(chooser);
                        drawerLayout.closeDrawers();

                        break;
                    case com.dream.malik.theviralquizzle.R.id.rate_us:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.dream.malik.theviralquizzle" )));

                        } catch (android.content.ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.dream.malik.theviralquizzle" )));

                        }
                            break;

                    case com.dream.malik.theviralquizzle.R.id.about_us:
                       // Toast.makeText(getApplicationContext(),"We are currently developing android app",Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(NavigationActivity.this,AboutUS.class);
                       startActivity(intent2);

                        drawerLayout.closeDrawers();
                        break;

                }
                return false;
            }
        });

        //set up navigation drawer
        drawerLayout =  findViewById(com.dream.malik.theviralquizzle.R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, com.dream.malik.theviralquizzle.R.string.drawer_open, com.dream.malik.theviralquizzle.R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);



            }
        };
        try {
            assert  getSupportActionBar()!=null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSupportActionBar().setHomeButtonEnabled(true);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    public  boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager

                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }



}
