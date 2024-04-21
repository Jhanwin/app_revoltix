package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreDashboard extends AppCompatActivity {

    TextView scoreFinish,date,time;
    ImageView goHomeScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_dashboard);

        Intent intent = getIntent();
        String score = intent.getStringExtra("Score");
//        String Timer = intent.getStringExtra("Time");
//        String id = intent.getStringExtra("UserId");
//        String TextDiff = intent.getStringExtra("TextDiff");

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

        scoreFinish.setText(score);
        date.setText(currentDate.toString());
        time.setText(formattedDateTime);

        goHomeScore.setOnClickListener(v -> finish());


    }
}