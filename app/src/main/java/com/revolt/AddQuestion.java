package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddQuestion extends AppCompatActivity {

    DatabaseReference database;
    EditText subject,topic,Difficulty,QuestionAdd,CorrectAnswer,WrongAnswer1,WrongAnswer2,WrongAnswer3;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        subject = findViewById(R.id.subject);
        topic = findViewById(R.id.topic);
        Difficulty = findViewById(R.id.Difficulty);
        QuestionAdd = findViewById(R.id.QuestionsAdd);
        CorrectAnswer = findViewById(R.id.CorrectAnswer);
        WrongAnswer1 = findViewById(R.id.WrongAnswer1);
        WrongAnswer2 = findViewById(R.id.WrongAnswer2);
        WrongAnswer3 = findViewById(R.id.WrongAnswer3);

        btnAdd = findViewById(R.id.btnAddQuestions);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance().getReference("Subject").child(subject.getText().toString()).child(topic.getText().toString()).child(Difficulty.getText().toString());
                database.child(QuestionAdd.getText().toString()).child("correct").child("choices1").setValue(CorrectAnswer.getText().toString());
                database.child(QuestionAdd.getText().toString()).child("incorrect").child("choices2").setValue(WrongAnswer1.getText().toString());
                database.child(QuestionAdd.getText().toString()).child("incorrect").child("choices3").setValue(WrongAnswer2.getText().toString());
                database.child(QuestionAdd.getText().toString()).child("incorrect").child("choices4").setValue(WrongAnswer3.getText().toString());

            }
        });








    }
}