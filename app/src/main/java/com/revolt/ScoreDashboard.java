package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreDashboard extends AppCompatActivity {

    TextView scoreFinish,date,time;
    ImageView goHomeScore;

    MediaPlayer passed, failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_dashboard);

        Intent intent = getIntent();
        String score = intent.getStringExtra("Score");
        String numOfItem = intent.getStringExtra("numOfItem");
//        String Timer = intent.getStringExtra("Time");
//        String id = intent.getStringExtra("UserId");
//        String TextDiff = intent.getStringExtra("TextDiff");

        passed = MediaPlayer.create(this, R.raw.passed);
        failed = MediaPlayer.create(this, R.raw.fail);

        assert score != null;
        float numScore = Float.parseFloat(score);
        assert numOfItem != null;
        float numberItem = Float.parseFloat(numOfItem);

//        float comp = (float) (numberItem * 0.7);
//
//        if(comp <= numScore){
//            playPassed();
//            Toast.makeText(ScoreDashboard.this, "You Passed", Toast.LENGTH_LONG).show();
//        }else{
//            playFailed();
//            Toast.makeText(ScoreDashboard.this, "You Failed", Toast.LENGTH_LONG).show();
//        }

        scoreFinish = findViewById(R.id.scoreFinish);
//        summary = findViewById(R.id.summary);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        goHomeScore = findViewById(R.id.goHomeScore);


        // Get the current date
        LocalDate currentDate = LocalDate.now();
        System.out.println("Current Date: " + currentDate);

        // Get the current time
        LocalTime currentTime = LocalTime.now();
        System.out.println("Current Time: " + currentTime);

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current Date and Time: " + currentDateTime);

        // You can also format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedDateTime = currentTime.format(formatter);

        scoreFinish.setText("Congratulation, you finish the quiz");
        date.setText(currentDate.toString());
        time.setText(formattedDateTime);

        goHomeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseMediaPlayer();
                finish();
            }
        });


    }

    private void playPassed() {
        if (passed != null) {
            passed.start(); // Start playing the sound
        }
    }

    private void playFailed() {
        if (failed != null) {
            failed.start(); // Start playing the sound
        }
    }

    private void releaseMediaPlayer() {
        if (passed != null) {
            passed.release();
            passed = null;
        }
        if (failed != null) {
            failed.release();
            failed = null;
        }
    }



}