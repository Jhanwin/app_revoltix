package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class offlineSubOption extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4;

    String mode;

    ImageView goBackOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_sub_option);

        btn1 = findViewById(R.id.btnSub1);
        btn2 = findViewById(R.id.btnSub2);
        btn3 = findViewById(R.id.btnSub3);
        btn4 = findViewById(R.id.btnSub4);

        Intent intent = getIntent();
        mode = intent.getStringExtra("quizMode");

        goBackOff = findViewById(R.id.goBackOff);

        goBackOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offAct = new Intent(getApplicationContext(), offlineTopics.class);
                offAct.putExtra("subject", btn1.getText().toString());
                offAct.putExtra("quizMode", mode);
                startActivity(offAct);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offAct = new Intent(getApplicationContext(), offlineTopics.class);
                offAct.putExtra("subject", btn2.getText().toString());
                offAct.putExtra("quizMode", mode);
                startActivity(offAct);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offAct = new Intent(getApplicationContext(), offlineTopics.class);
                offAct.putExtra("subject", btn3.getText().toString());
                offAct.putExtra("quizMode", mode);
                startActivity(offAct);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offAct = new Intent(getApplicationContext(), offlineTopics.class);
                offAct.putExtra("subject", btn4.getText().toString());
                offAct.putExtra("quizMode", mode);
                startActivity(offAct);
            }
        });



    }
}