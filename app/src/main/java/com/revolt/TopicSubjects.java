package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TopicSubjects extends AppCompatActivity {

    List<String> buttonCont = new ArrayList<>();
    Button btnTopic1,btnTopic2,btnTopic3,btnTopic4;
    ImageView goHome2;
    TextView textSubject;

    DatabaseReference dbUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_subjects);

        textSubject = findViewById(R.id.textSubject);

        Intent intent = getIntent();
        String TextSub = intent.getStringExtra("textToGet");
        String Mode = intent.getStringExtra("Mode");
        String data = intent.getStringExtra("UserId");
        textSubject.setText(TextSub);

        btnTopic1 = findViewById(R.id.btnTopic1);
        btnTopic2 = findViewById(R.id.btnTopic2);
        btnTopic3 = findViewById(R.id.btnTopic3);
        btnTopic4 = findViewById(R.id.btnTopic4);
        goHome2 = findViewById(R.id.goHome2);

        goHome2.setOnClickListener(v -> finish());


//        assert TextSub != null;
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub);
//
//        database.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    // Iterate through all children nodes and add them to the list
//                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//
//                        String key = childSnapshot.getKey();
//                        buttonCont.add(key);
//
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        switch (Objects.requireNonNull(TextSub)) {
            case "Electronics Engineering":
                btnTopic1.setText("DC Electrical Circuits");
                btnTopic2.setText("AC Electrical Circuits");
                btnTopic3.setText("Electronic Devices and Circuits");
                btnTopic4.setText("Electronic Circuit Analysis and Design");
                break;
            case "Electronics Systems and Technologies":
                btnTopic1.setText("Principles of Communications");
                btnTopic2.setText("Transmission and Antenna Systems");
                btnTopic3.setText("Digital Communications");
                btnTopic4.setVisibility(View.GONE);
                break;
            case "General Engineering & Applied Sciences":
                btnTopic1.setText("Chemistry");
                btnTopic2.setText("Physics 1");
                btnTopic3.setText("Physics 2");
                btnTopic4.setText("Engineering Management");
                break;
            case "Mathematics":
                btnTopic1.setText("Differential Calculus");
                btnTopic2.setText("Differential Equations");
                btnTopic3.setVisibility(View.GONE);
                btnTopic4.setVisibility(View.GONE);
                break;
        }



        btnTopic1.setOnClickListener(v -> {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic1.getText().toString());
                quizSubDif.putExtra("textSubToGet", TextSub);
                quizSubDif.putExtra("Mode", Mode);
                quizSubDif.putExtra("UserId", data);
                startActivity(quizSubDif);
                finish();

            });

        btnTopic2.setOnClickListener(v -> {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic2.getText().toString());
                quizSubDif.putExtra("textSubToGet", TextSub);
                quizSubDif.putExtra("UserId", data);
                quizSubDif.putExtra("Mode", Mode);
                startActivity(quizSubDif);
                finish();

            });

        btnTopic3.setOnClickListener(v -> {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic3.getText().toString());
                quizSubDif.putExtra("textSubToGet", TextSub);
                quizSubDif.putExtra("UserId", data);
                quizSubDif.putExtra("Mode", Mode);
                startActivity(quizSubDif);
                finish();

            });

        btnTopic4.setOnClickListener(v -> {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic4.getText().toString());
                quizSubDif.putExtra("textSubToGet", TextSub);
                quizSubDif.putExtra("UserId", data);
                quizSubDif.putExtra("Mode", Mode);
                startActivity(quizSubDif);
                finish();

            });





    }
}