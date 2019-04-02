package com.dream.malik.theviralquizzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dream.malik.theviralquizzle.Common.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LeaderBoard extends AppCompatActivity {
TextView tv_score,user_ka_name;
int lastscore,best1,best2,best3;
    FirebaseAuth mAuth;
    String getusername;
    FirebaseUser firebaseUser;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dream.malik.theviralquizzle.R.layout.activity_leader_board);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tv_score = findViewById(com.dream.malik.theviralquizzle.R.id.tv_score);
        user_ka_name = findViewById(com.dream.malik.theviralquizzle.R.id.user_ka_name);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        getusername = firebaseUser != null ? firebaseUser.getDisplayName() : null;

       SharedPreferences preferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);//name and mode of our SP
/*
HIGH_SCOREs is the main key we have get it from Done Activity now each time user play game HIGH_SCOREs will be updated
and we will use sorting tech to compare and sort top 3 scores of all categories
 */
        lastscore = preferences.getInt("HIGH_SCOREs", 0);
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);
        user_ka_name.setText(getusername);

        if (lastscore > best3) {//Swapping tech to sort
//if score3 is less than lastscore then we will assign best3 =lastscore

            best3 = lastscore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.apply();

        }
        if (lastscore > best2) {
//if lastscore is greater than best2 then best2=lastscore and best3=best2

            int temp = best2;
            best2 = lastscore;
            best3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.putInt("best2", best2);

            editor.apply();


        }

        if (lastscore > best1) {
//if lastscore is greater than best1 then best1=lastscore and best2=best1

            int temp = best1;
            best1 = lastscore;
            best2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);
            editor.apply();

        }


        tv_score.setText(Common.categoryName + ":" + "Last Score:" + lastscore + "\n" + "\n" + "\n" + "High Score 1:" + best1 + "\n" + "\n" + "\n" + "High Score 2:" + best2 + "\n" + "\n" + "\n" + "High Score 3:" + best3 + "\n");


    }





    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
