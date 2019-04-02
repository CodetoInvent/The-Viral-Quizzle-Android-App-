package com.dream.malik.theviralquizzle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dream.malik.theviralquizzle.Common.Common;
import com.dream.malik.theviralquizzle.model.QuestionScore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends AppCompatActivity {
    Button btnTryAgain;
    TextView txtResultscore,getTxtResultQuestion,highscores;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference question_score;
    FirebaseAuth mAuth;
    String getusername;
    FirebaseUser firebaseUser;
   private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dream.malik.theviralquizzle.R.layout.activity_done);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        getusername = firebaseUser != null ? firebaseUser.getDisplayName() : null;
        //getusername=mAuth.getCurrentUser().getDisplayName();
        database = FirebaseDatabase.getInstance();
        //storing JSON Question_score in  question_score of  DatabaseReference
        question_score = database.getReference("Question_score");
        txtResultscore = findViewById(com.dream.malik.theviralquizzle.R.id.txtTotalScores);
        highscores = findViewById(com.dream.malik.theviralquizzle.R.id.highscore);
        getTxtResultQuestion = findViewById(com.dream.malik.theviralquizzle.R.id.txtTotalQuestions);
        progressBar = findViewById(com.dream.malik.theviralquizzle.R.id.doneProgressBar);
        btnTryAgain = findViewById(com.dream.malik.theviralquizzle.R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //if we press TryAgn btn it will take you back to game play
                Intent intent = new Intent(Done.this, Playing.class);
                startActivity(intent);
                finish();
            }
        });
        //here we will get data from bundle and set it to the view
        Bundle extra = getIntent().getExtras();
        //when game is over we we will retrive the score,total,correct answer from Playing Activity using get pre de method by pssng key
        if (extra != null) {
            //we will store the Playing Activity Data in int type now we can use it in this activity as our wish
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int Answered = extra.getInt("CORRECT");
            //in the txtview we have set score answered ,totalquestion as our wish to display
            txtResultscore.setText(String.format(getString(com.dream.malik.theviralquizzle.R.string.se), score));
            getTxtResultQuestion.setText(String.format(getString(com.dream.malik.theviralquizzle.R.string.ee), Answered, totalQuestion));
            //this progres bar will set progressed acc to our totalques/answered
            progressBar.setMax(totalQuestion);
            progressBar.setProgress(Answered);
            /*
            by using   question_score we will create a User name(JSON ARRAY) Dynamically from java code
            now   question_score is the sub root for Displaying score
            1st %s is UserName (getusername) 2nd %s is categodyId num
             */

            question_score.child(String.format("%s_%s", getusername, Common.categoryId))

                    .setValue(new QuestionScore(String.format("%s_%s", getusername, Common.categoryId),
                            //this are child of database
                            Common.categoryId,

                            String.valueOf(score),
                            Common.categoryId,
                            Common.categoryName));
            SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
            int highscore = settings.getInt("HIGH_SCORE", 0);//we will create  a key to store Highscore
            if (score > highscore) {//now we will compare curent score with highscore and update the highscore via swapping tech
                highscores.setText("HIGH SCORE :" + score);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("HIGH_SCORE", score);
                editor.apply();
            } else {
                highscores.setText("HIGH SCORE:" + highscore);
            }
            SharedPreferences.Editor editor = settings.edit();
            //we have passed(put) HIGH_SCOREs (key) and score now we can get the score where ever  we use HIGH_SCOREs(key) followed by getMethod
            editor.putInt("HIGH_SCOREs", score);
            editor.apply();


        }


    }



    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }



}
