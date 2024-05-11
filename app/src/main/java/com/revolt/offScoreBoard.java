package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class offScoreBoard extends AppCompatActivity {

    TextView scoreFinishOff, date, time;
    ImageView backScore;

    String score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_score_board);

        scoreFinishOff = findViewById(R.id.scoreFinishOff);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        backScore = findViewById(R.id.backScore);

        Intent intent = getIntent();
        score = intent.getStringExtra("score");

        backScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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

        scoreFinishOff.setText(score);
        date.setText(currentDate.toString());
        time.setText(formattedDateTime);













    }
}