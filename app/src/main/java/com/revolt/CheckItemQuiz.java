package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckItemQuiz extends AppCompatActivity {

    DatabaseReference dbRef;

    RecyclerView recyclerViewHis;
    finishItemAdapter myAdapterHis;
    ArrayList<finishItems> listHis;

    String IdProfile,time;

    ValueEventListener checkAllItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item_quiz);


        Intent intent = getIntent();
        IdProfile = intent.getStringExtra("idprofile");
        time = intent.getStringExtra("TimeBattleMode");

        ImageView goHome = findViewById(R.id.goHome);


        dbRef = FirebaseDatabase.getInstance().getReference("users").child(IdProfile);
        recyclerViewHis = findViewById(R.id.checkItemQuestion);
        recyclerViewHis.setHasFixedSize(true);
        recyclerViewHis.setLayoutManager(new LinearLayoutManager(this));
        listHis = new ArrayList<>();
        myAdapterHis = new finishItemAdapter(this, listHis);
        recyclerViewHis.setAdapter(myAdapterHis);

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.child("BattleMode").child(time).child("Item").removeEventListener(checkAllItem);
                finish();
            }
        });



        ShowHistory();




    }

    @Override
    public void onBackPressed() {
        // Call finish to close the current activity when the back button is pressed
        super.onBackPressed();
        dbRef.child("BattleMode").child(time).child("Item").removeEventListener(checkAllItem);
        Toast.makeText(CheckItemQuiz.this, "Back", Toast.LENGTH_LONG).show();
        finish();
    }


    public void ShowHistory(){

        checkAllItem = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listHis.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String itemQuestion = dataSnapshot.child("question").getValue(String.class);
                    String itemCorrect = dataSnapshot.child("correctAnswer").getValue(String.class);
                    String itemSelected = dataSnapshot.child("selectedAnswer").getValue(String.class);
                    String itemRemarks = dataSnapshot.child("remarks").getValue(String.class);

                    listHis.add(new finishItems(itemQuestion,itemCorrect,itemSelected,itemRemarks));
                }

                // Check the size of the list after adding data
                Log.d("TAG", "Size of listHis: " + listHis.size());

                myAdapterHis.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbRef.child("BattleMode").child(time).child("Item").addValueEventListener(checkAllItem);



    }



}