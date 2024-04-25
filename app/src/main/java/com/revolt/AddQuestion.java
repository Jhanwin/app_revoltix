package com.revolt;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AddQuestion extends AppCompatActivity {


    List<String> buttonCont = new ArrayList<>();

    String TextSub,Mode,data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);


        Intent intent = getIntent();
        TextSub = intent.getStringExtra("textToGet");
        Mode = intent.getStringExtra("Mode");
        data = intent.getStringExtra("UserId");




        assert TextSub != null;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub);
        database.addValueEventListener(new ValueEventListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    buttonCont.clear();
                    // Iterate through all children nodes and add them to the list
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        buttonCont.add(key);
                    }
                    // After populating the list, create the button and set its text
                    createButton();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private void createButton() {

        for(int i = 0;i< buttonCont.size();i++){
            Button button = new Button(this);
            if (!buttonCont.isEmpty()) {
                button.setText(buttonCont.get(i));
            }

            button.setBackgroundResource(R.drawable.bg_btn_subjects);
            button.setTextColor(Color.WHITE);
            button.setTypeface(ResourcesCompat.getFont(this, R.font.futura_display));
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            button.setLayoutParams(layoutParams);
            layoutParams.setMargins(20, 0, 20, 20);

            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent quizSubDif = new Intent(getApplicationContext(), QuizSubDifficulties.class);
                    quizSubDif.putExtra("textTopicToGet", buttonCont.get(finalI));
                    quizSubDif.putExtra("textSubToGet", TextSub);
                    quizSubDif.putExtra("Mode", Mode);
                    quizSubDif.putExtra("UserId", data);
                    startActivity(quizSubDif);
                    finish();
                }
            });

            LinearLayout layout = findViewById(R.id.layoutButtons);
            layout.addView(button);
        }

    }



}