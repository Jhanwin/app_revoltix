package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ModeOption extends AppCompatActivity {

    Button btnPracticeOffline, btnBattleOffline;
    ImageView goBackOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_option);


        btnPracticeOffline = findViewById(R.id.PracticeModeOffline);
        btnBattleOffline = findViewById(R.id.BattleModeOffline);
        goBackOff = findViewById(R.id.goBackOff);


        goBackOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPracticeOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offSub = new Intent(getApplicationContext(), offlineSubOption.class);
                offSub.putExtra("quizMode", "PracticeMode");
                startActivity(offSub);
            }
        });

        btnBattleOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offSub = new Intent(getApplicationContext(), offlineSubOption.class);
                offSub.putExtra("quizMode", "BattleMode");
                startActivity(offSub);
            }
        });



    }
}