package com.dream.malik.theviralquizzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

     private boolean isRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.dream.malik.theviralquizzle.R.layout.activity_settings);
        Toolbar toolbar = findViewById(com.dream.malik.theviralquizzle.R.id.toolbar);
        setSupportActionBar(toolbar);
        Button reset = findViewById(com.dream.malik.theviralquizzle.R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*
SharedPreferences is an API from Android SDK to store and retrieve application preferences.
SharedPreferences are simply sets of data values that stored persistently. Persistently
 which mean data you stored in the SharedPreferences are still exist even if you stop the application or turn off the device.
 */
 /*
                getSharedPreferences() that returns a SharedPreference instance pointing to the file
                 that contains the values of preferences.
                 By setting this mode, the file can only be accessed using calling application


                 */
                final SharedPreferences pref = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
                /*
                You can save something in the sharedpreferences by using SharedPreferences.Editor class.
                You will call the edit method of SharedPreference instance and will receive it in an editor object
                 */
                SharedPreferences.Editor editor = pref.edit();
/*
apply()

It is an abstract method. It will commit your changes back from editor to the sharedPreference object you are calling


clear()

It will remove all values from the editor


 */
                editor.clear().apply();

                Snackbar.make(v, "Highscore Reseted Successfully", Snackbar.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }


}