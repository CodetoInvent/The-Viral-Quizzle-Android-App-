package com.dream.malik.theviralquizzle;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dream.malik.theviralquizzle.Common.Common;
import com.dream.malik.theviralquizzle.model.Question;
import com.dream.malik.theviralquizzle.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //a constant for detecting the login intent result
    private static final int RC_SIGN_IN = 234;

    //Tag for the logs optional
    private static final String TAG = "MainActivity";

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;

    //And also a Firebase Auth object
    FirebaseAuth mAuth;
    ProgressDialog mProgressDialog;
    FirebaseDatabase database;
    DatabaseReference questions; //To read or write data from the database, we need an instance of DatabaseReference
    SignInButton mSignInButton;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        /*
        onCreate(Bundle) is where you initialize your activity. When Activity is started and application is not loaded,
         then both onCreate() methods will be called.
         But for subsequent starts of Activity , the onCreate() of application will not be called.
         */
        super.onCreate(savedInstanceState);
         /*
        the savedInstanceState is a reference to a Bundle object that is passed into the onCreate method of every Android Activity.
        Activities have the ability,
        under special circumstances, to restore themselves to a previous state using the data stored in this bundle
         */
        /*
        Bundle is generally used for passing data between various component.
        Bundle class which can be retrieved from the intent via the getExtras() method.
         You can also add data directly to the Bundle via the overloaded putExtra() methods of the Intent objects.
        Extras are key/value pairs, the key is always of type String.
         */
        setContentView(R.layout.activity_main);
        /*

        Basically what this function does is display the Layout created thorugh XML or the Dynamically created layout view in the Screen.
         */
       // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");


        loadQuestion(Common.categoryId);
        //first we intialized the FirebaseAuth object
        mAuth = FirebaseAuth.getInstance();
        mSignInButton = findViewById(com.dream.malik.theviralquizzle.R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);




        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.dream.malik.theviralquizzle.R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //inside onClick() method we are calling the signIn() method that will open
        //google sign in intent


    }


    private void loadQuestion(String categoryId) {//method to check questions and load it
        Collections.shuffle(Common.questionList);//tto shuffle questions each time we play
        if (Common.questionList.size() > 0)//if we have questions then this logic will works
            Common.questionList.clear();//clearing the old questions in our mobile while we update new questions in our database
        questions.orderByChild("categoryId").equalTo(categoryId)//it is a child  in our database
                //when our Questions categoryId =the CategoryId of Our Category  acc to  that Questions will shown
                .addValueEventListener(new ValueEventListener() {
                    //To read data at a path and listen for changes, we use the addValueEventListener()( DatabaseReference)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            /*
                            A DataSnapshot instance contains data from a Firebase Database location.
                             Any time you read Database data, you receive the data as a DataSnapshot
                             */
                            Question ques = postSnapshot.getValue(Question.class);//we made the Object of our Question class(dynamic)
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







    @Override
    protected void onStart() {
        super.onStart();

        //if the user is already signed in
        //we will close this activity
        //and take the user to profile activity
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, NavigationActivity.class));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            @SuppressLint("RestrictedApi") Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        showProgressDialog();

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(MainActivity.this,NavigationActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication fail",
                                    Toast.LENGTH_SHORT).show();

                        }
                        hideProgressDialog();

                        // ...
                    }
                });
    }


    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        @SuppressLint("RestrictedApi") Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(com.dream.malik.theviralquizzle.R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onClick(View view) {

        Utils utils = new Utils(this);
        int id = view.getId();

        if (id == com.dream.malik.theviralquizzle.R.id.sign_in_button){
            if (utils.isNetworkAvailable()){
                signIn();
            }else {
                Toast.makeText(MainActivity.this, "Oops! no internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }



}

