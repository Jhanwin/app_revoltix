package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnMath = findViewById(R.id.btnMath);
        Button btnElect = findViewById(R.id.btnElect);
        Button btnGenEn = findViewById(R.id.btnGenEn);
        Button btnElectTech = findViewById(R.id.btnElectTech);
        ImageView goHome = findViewById(R.id.goHome);

        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TopicSubjects = new Intent(getApplicationContext(), TopicSubjects.class);
                TopicSubjects.putExtra("textToGet", btnMath.getText().toString());
                startActivity(TopicSubjects);

            }
        });


        btnElect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TopicSubjects = new Intent(getApplicationContext(), TopicSubjects.class);
                TopicSubjects.putExtra("textToGet", btnElect.getText().toString());
                startActivity(TopicSubjects);

            }
        });


        btnGenEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TopicSubjects = new Intent(getApplicationContext(), TopicSubjects.class);
                TopicSubjects.putExtra("textToGet", btnGenEn.getText().toString());
                startActivity(TopicSubjects);

            }
        });

        btnElectTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TopicSubjects = new Intent(getApplicationContext(), TopicSubjects.class);
                TopicSubjects.putExtra("textToGet", btnElectTech.getText().toString());
                startActivity(TopicSubjects);

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