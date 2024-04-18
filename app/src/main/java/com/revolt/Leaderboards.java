package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class Leaderboards extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Users> list;

    TextView testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        Intent intent = new Intent();
        String nameGet = intent.getStringExtra("Name");

        testing = findViewById(R.id.Testing);


        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    if (user != null) {
                        list.add(user);
                    }
                }
                // Sort the list based on the score in descending order
                list.sort((user1, user2) -> {
                    // Sort in descending order
                    return Integer.compare(user2.getScore(), user1.getScore());
                });

                myAdapter.notifyDataSetChanged();

                if (!list.isEmpty()) {
                    String Name = list.get(0).getName();
                    testing.setText(Name);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}