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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Objects;

public class TopicSubjects extends AppCompatActivity {

    List<String> buttonCont = new ArrayList<>();
    Button btnTopic1,btnTopic2,btnTopic3,btnTopic4;
    ImageView goHome2;
    TextView textSubject;

    DatabaseReference database;

    String TextSub, Mode, data;
    int sEasy, sMedium, sHard;

    ValueEventListener listTopic;

    boolean isDone = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_subjects);

        textSubject = findViewById(R.id.textSubject);

        Intent intent = getIntent();
        TextSub = intent.getStringExtra("textToGet");
        Mode = intent.getStringExtra("Mode");
        data = intent.getStringExtra("UserId");
        textSubject.setText(TextSub);
        goHome2 = findViewById(R.id.goHome2);

        goHome2.setOnClickListener(v -> finish());



        assert TextSub != null;
        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub);

//        listTopic = new ValueEventListener(){
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    buttonCont.clear();
//                    // Iterate through all children nodes and add them to the list
//                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                        String key = childSnapshot.getKey();
//                        buttonCont.add(key);
//                    }
//                    // After populating the list, create the button and set its text
//                    createButton();
//                    isDone = true;
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//



//        database.addValueEventListener(listTopic);
//        database.removeEventListener(listTopic);

        fetchData(new ValueEventListenerCallback() {
            @Override
            public void onDataChangeCompleted() {
                database.removeEventListener(listTopic);
            }
        });





    }

    public interface ValueEventListenerCallback {
        void onDataChangeCompleted();
    }


    public void fetchData(ValueEventListenerCallback callback) {
        listTopic = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Handle data change here
                // This method will be called when the initial data is loaded and whenever the data changes

                if(dataSnapshot.exists()){
                    buttonCont.clear();
                    // Iterate through all children nodes and add them to the list
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        buttonCont.add(key);
                    }
                    // After populating the list, create the button and set its text
                    createButton();
                }


                callback.onDataChangeCompleted();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        };

        // Attach the ValueEventListener to the database reference
        database.addValueEventListener(listTopic);
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
                    quizSubDif.putExtra("sEasy", sEasy);
                    quizSubDif.putExtra("sMedium", sMedium);
                    quizSubDif.putExtra("sHard", sHard);

                    startActivity(quizSubDif);

                }
            });

            LinearLayout layout = findViewById(R.id.topicButtons);
            layout.addView(button);
        }

    }


}