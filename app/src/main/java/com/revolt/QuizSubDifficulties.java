package com.revolt;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
    DatabaseReference database, databaseUser;

    ImageView goHome3;

    int easy, medium, hard;

    int score, scoreMedium, scoreHard;

    int scoreE, scoreM, scoreH;

    int sEasy, sMedium, sHard;

    String selectedItem;

    ValueEventListener getNumEasy,getNumMedium,getNumHard;
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
        String sEasy = intent.getStringExtra("sEasy");
        String sMedium = intent.getStringExtra("sMedium");
        String sHard = intent.getStringExtra("sHard");

        textTopic.setText(TextTopicNum);
        goHome3 = findViewById(R.id.goHome3);
        goHome3.setOnClickListener(v -> finish());

        // In your activity's onCreate method
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Perform action based on selected item
                selectedItem = (String) parentView.getItemAtPosition(position);
                Toast.makeText(QuizSubDifficulties.this, selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });





        assert TextSub != null;
        assert TextTopicNum != null;
        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub).child(TextTopicNum);
        getNumEasy = new ValueEventListener() {
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
        };

        database.child("Easy").addValueEventListener(getNumEasy);

        getNumMedium = new ValueEventListener() {
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
        };

        database.child("Medium").addValueEventListener(getNumMedium);

        getNumHard = new ValueEventListener() {
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
        };

        database.child("Hard").addValueEventListener(getNumHard);

        if(Mode.equals("PracticeMode")){
            spinner.setVisibility(View.GONE);
        }




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
                    database.child("Easy").removeEventListener(getNumEasy);
                    database.child("Medium").removeEventListener(getNumMedium);
                    database.child("Hard").removeEventListener(getNumHard);
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

                }else if (selectedItem.equalsIgnoreCase("Hours")) {

                    Toast.makeText(QuizSubDifficulties.this, "Select Time", Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnEasy.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    addQuestAct.putExtra("sEasy", sEasy);
                    addQuestAct.putExtra("sMedium", sMedium);
                    addQuestAct.putExtra("sHard", sHard);
                    addQuestAct.putExtra("selectedTime", selectedItem);

                    database.child("Easy").removeEventListener(getNumEasy);
                    database.child("Medium").removeEventListener(getNumMedium);
                    database.child("Hard").removeEventListener(getNumHard);
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

                    database.child("Easy").removeEventListener(getNumEasy);
                    database.child("Medium").removeEventListener(getNumMedium);
                    database.child("Hard").removeEventListener(getNumHard);
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

                }else if (selectedItem.equalsIgnoreCase("Hours")) {

                    Toast.makeText(QuizSubDifficulties.this, "Select Time", Toast.LENGTH_SHORT).show();

                }else {

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnMedium.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());

                    addQuestAct.putExtra("sEasy", sEasy);
                    addQuestAct.putExtra("sMedium", sMedium);
                    addQuestAct.putExtra("sHard", sHard);

                    addQuestAct.putExtra("selectedTime", selectedItem);

                    database.child("Easy").removeEventListener(getNumEasy);
                    database.child("Medium").removeEventListener(getNumMedium);
                    database.child("Hard").removeEventListener(getNumHard);
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

                    database.child("Easy").removeEventListener(getNumEasy);
                    database.child("Medium").removeEventListener(getNumMedium);
                    database.child("Hard").removeEventListener(getNumHard);
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

                }else if (selectedItem.equalsIgnoreCase("Hours")) {

                    Toast.makeText(QuizSubDifficulties.this, "Select Time", Toast.LENGTH_SHORT).show();

                }else{

                    Intent addQuestAct = new Intent(getApplicationContext(), QuizGameBattle.class);
                    addQuestAct.putExtra("TextSub", TextSub);
                    addQuestAct.putExtra("textSubToGet", TextTopicNum);
                    addQuestAct.putExtra("textDifficulties", btnHard.getText().toString());
                    addQuestAct.putExtra("Mode", Mode);
                    addQuestAct.putExtra("UserId", data);
                    addQuestAct.putExtra("NumberOfItem", numOfItem.getText().toString());
                    addQuestAct.putExtra("sEasy", sEasy);
                    addQuestAct.putExtra("sMedium", sMedium);
                    addQuestAct.putExtra("sHard", sHard);
                    addQuestAct.putExtra("selectedTime", selectedItem);

                    database.child("Easy").removeEventListener(getNumEasy);
                    database.child("Medium").removeEventListener(getNumMedium);
                    database.child("Hard").removeEventListener(getNumHard);
                    startActivity(addQuestAct);
                    finish();

                }

            }

        });









    }


}