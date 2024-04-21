package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicSubjects extends AppCompatActivity {

    Button btnTopic1,btnTopic2,btnTopic3,btnTopic4;
    ImageView goHome2;
    TextView textSubject;

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

        goHome2.setOnClickListener(v -> {
            finish();
        });




        if(TextSub.equals("Electronics Engineering")) {
            btnTopic1.setText("DC Electrical Circuits");
            btnTopic2.setText("Electronic Devices and Circuits");
            btnTopic3.setText("tba");
            btnTopic4.setText("tba");
        }else if(TextSub.equals("Electronics Systems and Technologies")){
            btnTopic1.setText("Principles of Communications");
            btnTopic2.setText("tba");
            btnTopic3.setText("tba");
            btnTopic4.setText("tba");
        }else if(TextSub.equals("General Engineering & Applied Sciences")){
            btnTopic1.setText("Chemistry");
            btnTopic2.setText("tba");
            btnTopic3.setText("tba");
            btnTopic4.setText("tba");
        }else if(TextSub.equals("Mathematics")){
            btnTopic1.setText("Differential Calculus");
            btnTopic2.setText("Differential Equations");
            btnTopic3.setText("tba");
            btnTopic4.setText("tba");
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