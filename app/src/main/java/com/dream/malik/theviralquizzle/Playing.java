package com.dream.malik.theviralquizzle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.malik.theviralquizzle.Common.Common;
import com.dream.malik.theviralquizzle.utils.Utils;
import com.squareup.picasso.Picasso;

import static java.lang.String.format;

public class Playing extends AppCompatActivity implements  View.OnClickListener {
    final static long INTERVAL = 1000;//1 sec interval
    final static long TIMEOUT = 90000;//90 sec timeout
    int progressValue = 0;
    CountDownTimer mCountDown;
    int index = 0, score = 0, thisQuestion=1, totalQuestion, correctAnswer, chance = 3;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD,morechances;
    TextView txtscore, txtQuestionNum, question_text, tchance;
   private boolean isRunning;
     Utils util;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.dream.malik.theviralquizzle.R.layout.activity_playing);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        question_image = findViewById(com.dream.malik.theviralquizzle.R.id.question_images);
        txtQuestionNum = findViewById(com.dream.malik.theviralquizzle.R.id.txtTotalQuestion);
        txtscore = findViewById(com.dream.malik.theviralquizzle.R.id.tScore);
        question_text = findViewById(com.dream.malik.theviralquizzle.R.id.question_texts);
        progressBar = findViewById(com.dream.malik.theviralquizzle.R.id.progressbar);
        tchance = findViewById(com.dream.malik.theviralquizzle.R.id.tchance);
        btnA = findViewById(com.dream.malik.theviralquizzle.R.id.btnAnswerA);
        morechances = findViewById(com.dream.malik.theviralquizzle.R.id.morechance);
        btnB = findViewById(com.dream.malik.theviralquizzle.R.id.btnAnswerB);
        btnC = findViewById(com.dream.malik.theviralquizzle.R.id.btnAnswerC);
        btnD = findViewById(com.dream.malik.theviralquizzle.R.id.btnAnswerD);
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);










        }





    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        btnA.setEnabled(false);
        btnB.setEnabled(false);
        btnC.setEnabled(false);
        btnD.setEnabled(false);
        mCountDown.cancel();
        if (index < totalQuestion) {//if list still contain questions
            Button clickedButton = (Button) view;
            /*
            -->clickedButton when  any of 4 buttons clicked our database will matches the option String
            with the database getCorrectAnswer()  String if both are equal this statement will executed
             */
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                //choose correct answer
                //if the answer is correct the button color will change to Green
                clickedButton.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorGreen));
                score += 5;
                correctAnswer++;//Correct Answer Count


                //  Handler used for:  Managing messages in the queue.

                final Handler handler = new Handler();

            /*
            Android.OS.Handler.PostDelayed Method.
            Causes the Runnable thread to be added to the message queue, to be run after the specified amount of time elapses
            in our case time delay is 1 sec
             */
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnA.setEnabled(true);
                        btnB.setEnabled(true);
                        btnC.setEnabled(true);
                        btnD.setEnabled(true);
                        //set all buttons purple(default color)
                        btnA.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));
                        btnB.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));
                        btnC.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));
                        btnD.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));

                        showQuestion(++index);//next question

                    }
                }, 1000);

            }
            //when button String doesn't matches with our Database Answer this else statement will execute
            else {
                //clicked button will be set to Red if the pressed Answer is Wrong
                clickedButton.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPrimary));
                String answerText = Common.questionList.get(index).getCorrectAnswer();//we are storing Database Answer in our String
                //match DB answer with one of the 3 buttons , turn the particular button   green if it has correct answer String
                if (btnA.getText().equals(answerText)) {
                    btnA.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorGreen));

                } else if (btnB.getText().equals(answerText)) {
                    btnB.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorGreen));
                } else if (btnC.getText().equals(answerText)) {
                    btnC.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorGreen));
                } else if (btnD.getText().equals(answerText)) {
                    btnD.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorGreen));
                }

                chance--;//chance will be decremented
                tchance.setText(format("%d", chance));//we will update the current chances


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //there is a delay of 1.5 sec in that we will set all buttons to it default color(purple)
                        btnA.setEnabled(true);
                        btnB.setEnabled(true);
                        btnC.setEnabled(true);
                        btnD.setEnabled(true);
                        btnA.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));
                        btnB.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));
                        btnC.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));
                        btnD.setBackgroundColor(getResources().getColor(com.dream.malik.theviralquizzle.R.color.colorPurple));

                        showQuestion(++index);//next question


                    }
                }, 1500);


                if (chance == 0) {//when chance is 0 this Statement will be Executed
                    Intent intent = new Intent(this, Done.class);//initialing next  activity
                    Bundle datasend = new Bundle();//Bundle obj  will store the data
                    datasend.putInt("SCORE", score);//key ,Value pair we can pass this data to another activity by its key
                    datasend.putInt("TOTAL", totalQuestion);
                    datasend.putInt("CORRECT", correctAnswer);
                    intent.putExtras(datasend);//it send data b/w the activity
                    startActivity(intent);
                    finish();
                }

            }

            txtscore.setText(format("%d", score));//depending upon correct or wrong answer basis Score will be updated


        }

    }

    private void showQuestion(int index) {
        if (index < totalQuestion) {//if index of ques is less than totalques the index count will be incremented this eg: 9/10
            thisQuestion++;
            txtQuestionNum.setText(format(getString(com.dream.malik.theviralquizzle.R.string.dd), thisQuestion, totalQuestion));//%d/%d thisQuestion/totalQuestion
            progressBar.setProgress(0);//reset prog bar
            progressValue = 0;
            //if the ques is of image type this code will execute
            if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                    if(util.isNetworkAvailable()) {
                        //it is Image question
                        Picasso.with(getBaseContext())
                                .load(Common.questionList.get(index).getQuestion())
                                .into(question_image);
                        question_image.setVisibility(View.VISIBLE);//we will make image view visible
                        question_text.setVisibility(View.INVISIBLE);//we will make text view invisible
                    }
                    else {
                        Toast.makeText(Playing.this,"oops! no internet connection",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Playing.this, NavigationActivity.class);
                        startActivity(intent);
                        finish();
                    }

            }
            //if the ques type is of TexView this code will be executed
            else {
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);//imageview will set to invisible
                question_text.setVisibility(View.VISIBLE);//txtview will be visible

            }
            //load answers from database to buttons through getters of Common.questionList class
            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());
            mCountDown.start();//start timer
        } else {
            //if it is a last question
            Intent intent = new Intent(this, Done.class);
            Bundle datasend = new Bundle();
            datasend.putInt("SCORE", score);
            datasend.putInt("TOTAL", totalQuestion);
            datasend.putInt("CORRECT", correctAnswer);
            intent.putExtras(datasend);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();//while Resuming count down starts from begining
        totalQuestion = Common.questionList.size();
        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(index);

            }
        };
        showQuestion(index);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }


    @Override
    public void onResume() {


        thisQuestion--;

        super.onResume();
    }

    @Override
    public void onPause() {

mCountDown.cancel();
        super.onPause();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
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


