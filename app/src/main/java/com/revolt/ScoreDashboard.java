package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ScoreDashboard extends AppCompatActivity {

    TextView scoreFinish,date,time;
    ImageView goHomeScore;

    MediaPlayer passed, failed;

    DatabaseReference db;

    String id,score;

    int numScore;

    int easyTotal = 0;
    int mediumTotal = 0;
    int hardTotal = 0;

    ValueEventListener updateAllScore;

    boolean exists;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_dashboard);

        Intent intent = getIntent();
        score = intent.getStringExtra("Score");
        String numOfItem = intent.getStringExtra("numOfItem");
        id = intent.getStringExtra("UserId");

//        passed = MediaPlayer.create(this, R.raw.passed);
//        failed = MediaPlayer.create(this, R.raw.fail);

        assert score != null;
        numScore = Integer.parseInt(score);
        assert numOfItem != null;
        float numberItem = Float.parseFloat(numOfItem);



        scoreFinish = findViewById(R.id.scoreFinish);
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

        scoreFinish.setText("Congratulations, you finish the quiz");
        date.setText(currentDate.toString());
        time.setText(formattedDateTime);

        Toast.makeText(ScoreDashboard.this, id,Toast.LENGTH_LONG).show();

        db = FirebaseDatabase.getInstance().getReference("users").child(id);
        updateAllScore = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String key = dataSnapshot.getKey();

//                    Toast.makeText(ScoreDashboard.this, key,Toast.LENGTH_LONG).show();

                    String diff = dataSnapshot.child("Difficulty").getValue(String.class);
                    int score = dataSnapshot.child("score").getValue(Integer.class);

                    switch (Objects.requireNonNull(diff)) {
                        case "Easy":
                            easyTotal += score;
                            break;
                        case "Medium":
                            mediumTotal += score;
                            break;
                        case "Hard":
                            hardTotal += score;
                            break;
                    }

                }

//                Toast.makeText(ScoreDashboard.this, easyTotal+" "+mediumTotal+" "+hardTotal,Toast.LENGTH_LONG).show();
                db.child("score").setValue(easyTotal);
                db.child("scoreMedium").setValue(mediumTotal);
                db.child("scoreHard").setValue(hardTotal);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

//        db.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Check if the key exists
//                exists = dataSnapshot.hasChild("BattleMode");
//                if(exists){
//                    db.child("BattleMode").addValueEventListener(updateAllScore);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle error
//                System.err.println("Error reading from database: " + databaseError.getMessage());
//            }
//        });

        db.child("BattleMode").addValueEventListener(updateAllScore);


        goHomeScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                releaseMediaPlayer();
                db.child("BattleMode").removeEventListener(updateAllScore);
                finish();
            }
        });


    }

//    private void playPassed() {
//        if (passed != null) {
//            passed.start(); // Start playing the sound
//        }
//    }
//
//    private void playFailed() {
//        if (failed != null) {
//            failed.start(); // Start playing the sound
//        }
//    }
//
//    private void releaseMediaPlayer() {
//        if (passed != null) {
//            passed.release();
//            passed = null;
//        }
//        if (failed != null) {
//            failed.release();
//            failed = null;
//        }
//    }







}