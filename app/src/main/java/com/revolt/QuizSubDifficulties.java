package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuizSubDifficulties extends AppCompatActivity {
    TextView textTopic;
    Button btnEasy,btnMedium,btnHard;
    EditText numOfItem;

    ImageView goHome3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_sub_difficulties);

        textTopic = findViewById(R.id.textTopicNum);
        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);
        numOfItem = findViewById(R.id.numOfItem);

        Intent intent = getIntent();
        String TextTopicNum = intent.getStringExtra("textTopicToGet");
        String TextSub = intent.getStringExtra("textSubToGet");
        String Mode = intent.getStringExtra("Mode");
        String data = intent.getStringExtra("UserId");

        textTopic.setText(TextTopicNum);
        goHome3 = findViewById(R.id.goHome3);

        goHome3.setOnClickListener(v -> finish());


        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mode.equals("PracticeMode")){

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGame.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnEasy.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }else{

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnEasy.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mode.equals("PracticeMode")){

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGame.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnMedium.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }else{

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnMedium.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mode.equals("PracticeMode")){

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGame.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnHard.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }else{

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnHard.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }
        });









    }
}