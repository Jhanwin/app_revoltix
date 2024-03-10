package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicSubjects extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_subjects);

        TextView textSubject = findViewById(R.id.textSubject);

        Intent intent = getIntent();
        String TextSub = intent.getStringExtra("textToGet");
        textSubject.setText(TextSub);

        Button btnTopic1 = findViewById(R.id.btnTopic1);
        Button btnTopic2 = findViewById(R.id.btnTopic2);
        Button btnTopic3 = findViewById(R.id.btnTopic3);
        Button btnTopic4 = findViewById(R.id.btnTopic4);
        ImageView goHome = findViewById(R.id.goHome);



        btnTopic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic1.getText().toString());
                startActivity(quizSubDif);

            }
        });

        btnTopic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic2.getText().toString());
                startActivity(quizSubDif);

            }
        });

        btnTopic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic3.getText().toString());
                startActivity(quizSubDif);

            }
        });

        btnTopic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                quizSubDif.putExtra("textTopicToGet", btnTopic4.getText().toString());
                startActivity(quizSubDif);

            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainMenu = new Intent(getApplicationContext(), Menu.class);
                startActivity(MainMenu);

            }
        });






    }
}