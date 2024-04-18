package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PretestGame extends AppCompatActivity {

    LinearLayout layoutPretestGame,layoutMechanics;
    Button btnStartPretest;

    List<String> questionsGen = new ArrayList<>();
    List<String> questionsEE = new ArrayList<>();
    List<String> correctAnswersGen = new ArrayList<>();
    List<String> correctAnswersEE = new ArrayList<>();
    List<List<String>> confusionChoicesGen = new ArrayList<>();
    List<List<String>> confusionChoicesEE = new ArrayList<>();

    DatabaseReference database;
    TextView questionText;

    RadioButton c1,c2,c3,c4;
    private int currentQuestionIndex = 0;



    RadioGroup answerRadioGroupPre;
    RadioButton answerOption1Pre,answerOption2Pre,answerOption3Pre,answerOption4Pre;

    int questionCounter = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretest_game);

        layoutPretestGame = findViewById(R.id.layoutPretestGame);
        layoutMechanics = findViewById(R.id.layoutMechanics);

        btnStartPretest = findViewById(R.id.btnStartPretest);

        questionText = findViewById(R.id.questionsBPre);

        answerOption1Pre = findViewById(R.id.answerOption1Pre);
        answerOption2Pre = findViewById(R.id.answerOption2Pre);
        answerOption3Pre = findViewById(R.id.answerOption3Pre);
        answerOption4Pre = findViewById(R.id.answerOption4Pre);

        answerRadioGroupPre = findViewById(R.id.answerRadioGroupPre);
        btnStartPretest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutPretestGame.setVisibility(View.VISIBLE);
                layoutMechanics.setVisibility(View.GONE);
            }
        });









    }

    //methods




















}