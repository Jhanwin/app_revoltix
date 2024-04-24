package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class QuizSubDifficulties extends AppCompatActivity {
    TextView textTopic;
    Button btnEasy,btnMedium,btnHard;
    EditText numOfItem;

    List<String> easyQ = new ArrayList<>();
    List<String> mediumQ = new ArrayList<>();
    List<String> hardQ = new ArrayList<>();

    DatabaseReference database;

    ImageView goHome3;

    int easy, medium, hard;

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

        assert TextSub != null;
        assert TextTopicNum != null;
        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub).child(TextTopicNum);
        database.child("Easy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // Iterate through all children nodes and add them to the list
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        easy++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        database.child("Medium").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // Iterate through all children nodes and add them to the list
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        medium++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        database.child("Hard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    // Iterate through all children nodes and add them to the list
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        hard++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });






        btnEasy.setOnClickListener(v -> {
            assert Mode != null;
            if(Mode.equals("PracticeMode")){


                if(numOfItem.getText().toString().equals("")){

                    Toast.makeText(QuizSubDifficulties.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();

                }else if(numOfItem.getText().toString().equals("0")){

                    Toast.makeText(QuizSubDifficulties.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();

                } else if (Integer.parseInt(numOfItem.getText().toString()) > easy) {

                    Toast.makeText(QuizSubDifficulties.this, "Too much value. Number of Question:"+easy, Toast.LENGTH_SHORT).show();

                }else{

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGame.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnEasy.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId",data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }



            }else{

                if(numOfItem.getText().toString().equals("")){

                    Toast.makeText(QuizSubDifficulties.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();

                }else if(numOfItem.getText().toString().equals("0")){

                    Toast.makeText(QuizSubDifficulties.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();

                }else if (Integer.parseInt(numOfItem.getText().toString()) > easy) {

                    Toast.makeText(QuizSubDifficulties.this, "Too much value. Number of Question:"+easy, Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnEasy.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }

        });

        btnMedium.setOnClickListener(v -> {
            assert Mode != null;
            if(Mode.equals("PracticeMode")){

                if(numOfItem.getText().toString().equals("")){

                    Toast.makeText(QuizSubDifficulties.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();

                }else if(numOfItem.getText().toString().equals("0")){

                    Toast.makeText(QuizSubDifficulties.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();

                }else if (Integer.parseInt(numOfItem.getText().toString()) > medium) {

                    Toast.makeText(QuizSubDifficulties.this, "Too much value. Number of Question:"+medium, Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGame.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnMedium.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();
                }

            }else{

                if(numOfItem.getText().toString().equals("")){

                    Toast.makeText(QuizSubDifficulties.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();

                }else if(numOfItem.getText().toString().equals("0")){

                    Toast.makeText(QuizSubDifficulties.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();

                }else if (Integer.parseInt(numOfItem.getText().toString()) > medium) {

                    Toast.makeText(QuizSubDifficulties.this, "Too much value. Number of Question:"+medium, Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnMedium.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }

        });

        btnHard.setOnClickListener(v -> {
            assert Mode != null;
            if(Mode.equals("PracticeMode")){

                if(numOfItem.getText().toString().equals("")){

                    Toast.makeText(QuizSubDifficulties.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();

                }else if(numOfItem.getText().toString().equals("0")){

                    Toast.makeText(QuizSubDifficulties.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();

                }else if (Integer.parseInt(numOfItem.getText().toString()) > hard) {

                    Toast.makeText(QuizSubDifficulties.this, "Too much value. Number of Question:"+hard, Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGame.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnHard.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }else{

                if(numOfItem.getText().toString().equals("")){

                    Toast.makeText(QuizSubDifficulties.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();

                }else if(numOfItem.getText().toString().equals("0")){

                    Toast.makeText(QuizSubDifficulties.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();

                }else if (Integer.parseInt(numOfItem.getText().toString()) > hard) {

                    Toast.makeText(QuizSubDifficulties.this, "Too much value. Number of Question:"+hard, Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnHard.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    startActivity(addQuestAct);
                    finish();

                }

            }

        });









    }
}