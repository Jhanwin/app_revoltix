package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ScoreDashboard extends AppCompatActivity {

    TextView scoreFinish,date,time;
    ImageView goHomeScore;



    DatabaseReference db;

    String id,score;

    int numScore;

    int easyTotal = 0;
    int mediumTotal = 0;
    int hardTotal = 0;

    ValueEventListener updateAllScore;



    @SuppressLint({"RestrictedApi", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_dashboard);

        Intent intent = getIntent();
        score = intent.getStringExtra("Score");
        id = intent.getStringExtra("UserId");



        assert score != null;
        numScore = Integer.parseInt(score);


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

        db = FirebaseDatabase.getInstance().getReference("users").child(id);
        updateAllScore = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {



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

                db.child("score").setValue(easyTotal);
                db.child("scoreMedium").setValue(mediumTotal);
                db.child("scoreHard").setValue(hardTotal);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };



        db.child("BattleMode").addValueEventListener(updateAllScore);

        goHomeScore.setOnClickListener(v -> {
            db.child("BattleMode").removeEventListener(updateAllScore);
            finish();
        });


    }

    @Override
    public void onBackPressed() {
        // Call finish to close the current activity when the back button is pressed
        super.onBackPressed();
        db.child("BattleMode").removeEventListener(updateAllScore);
        Toast.makeText(ScoreDashboard.this, "Back", Toast.LENGTH_LONG).show();
        finish();
    }






}