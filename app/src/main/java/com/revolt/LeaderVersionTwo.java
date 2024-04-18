package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderVersionTwo extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapterNew myAdapter;
    ArrayList<UserNew> list;

    TextView testing,rankval,txtNameUser,txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_version_two);

        testing = findViewById(R.id.Testing);
        rankval = findViewById(R.id.RankVal);
        txtNameUser = findViewById(R.id.txtNameUser);
        txtScore = findViewById(R.id.txtScore);
        ImageView ProfilePicture = findViewById(R.id.ProfilePicture);

        Intent intent = getIntent();
        String nameGet = intent.getStringExtra("NameUser");

        recyclerView = findViewById(R.id.userListNewLead);
        database = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new MyAdapterNew(this, list);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String profile = dataSnapshot.child("profile").getValue(String.class);
                    int score = dataSnapshot.child("score").getValue(Integer.class);
                    int scoreM = dataSnapshot.child("scoreMedium").getValue(Integer.class);
                    int scoreH = dataSnapshot.child("scoreHard").getValue(Integer.class);
                    list.add(new UserNew(name,email,id,profile,score,scoreM,scoreH));
                }
                // Sort the list based on the score in descending order
                list.sort((user1, user2) -> {
                    // Sort in descending order
                    return Integer.compare(user2.getScoreHard(), user1.getScoreHard());
                });

                for(int i=0;i< list.size();i++){
                    String namee = list.get(i).getName();
                    if(namee.equals(nameGet)){
                        String text = String.valueOf(i+1);
                        Picasso.get().load(list.get(i).getProfile()).into(ProfilePicture);
                        txtNameUser.setText(namee);
                        rankval.setText(text);
                        txtScore.setText(String.valueOf(list.get(i).getScore()));
                        break;
                    }

                }
                myAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }
}