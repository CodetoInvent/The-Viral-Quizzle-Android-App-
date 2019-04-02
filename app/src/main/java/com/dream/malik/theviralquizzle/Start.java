package com.dream.malik.theviralquizzle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dream.malik.theviralquizzle.Common.Common;
import com.dream.malik.theviralquizzle.model.Question;
import com.dream.malik.theviralquizzle.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {
    private boolean isRunning;
    Button btnplay;
    FirebaseDatabase database;
    DatabaseReference questions; //To read or write data from the database, we need an instance of DatabaseReference
Utils util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dream.malik.theviralquizzle.R.layout.activity_start);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        database=FirebaseDatabase.getInstance();
        /*
        questions is a obj of DatabaseReference
        database.getference("Questions") we will pass the JSON Array obj as parameter for referencing
        -->either pass the parameter or create the JSON Array Obj from Java Code

         */
        questions=database.getReference("Questions");
         loadQuestion(Common.categoryId);//prototype of below function
btnplay=findViewById(com.dream.malik.theviralquizzle.R.id.btnplay);
util=new Utils(this);
btnplay.setOnClickListener(new View.OnClickListener() {//when we click button this will start new activity(playing)
    @Override
    public void onClick(View v) {

if(util.isNetworkAvailable()){
    loadQuestion(Common.categoryId);


    Intent intent =new Intent(Start.this,Playing.class);
        startActivity(intent);
        finish();}
        else {
    Toast.makeText(Start.this,"oops! no internet connection",Toast.LENGTH_LONG).show();
        }
    }
});



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

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();

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
