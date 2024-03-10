package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizSubDifficulties extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_sub_difficulties);

        TextView textTopic = findViewById(R.id.textTopicNum);
        Button btnEasy = findViewById(R.id.btnEasy);
        Button btnMedium = findViewById(R.id.btnMedium);
        Button btnHard = findViewById(R.id.btnHard);

        Intent intent = getIntent();
        String TextTopicNum = intent.getStringExtra("textTopicToGet");
        textTopic.setText(TextTopicNum);
        ImageView goHome = findViewById(R.id.goHome);


        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








    }
}