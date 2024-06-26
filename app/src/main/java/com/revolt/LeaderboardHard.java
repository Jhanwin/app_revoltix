package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaderboardHard extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference database;
    AdapterHard myAdapter;
    ArrayList<UserNew> list;

    LinearLayout linLayout4;

    ImageView ProfilePicture;

    Button btnBoardEasy, btnBoardMedium, btnBoardHard;

    TextView testing, rankval, txtNameUser, txtScore;

    String nameGet;

    ValueEventListener hardLeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_hard);


        testing = findViewById(R.id.Testing);
        rankval = findViewById(R.id.RankValH);
        txtNameUser = findViewById(R.id.txtNameUserH);
        txtScore = findViewById(R.id.txtScoreH);
        ProfilePicture = findViewById(R.id.ProfilePictureH);

        Intent intent = getIntent();
        nameGet = intent.getStringExtra("NameUser");

        recyclerView = findViewById(R.id.userListNewLead);
        database = FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        myAdapter = new AdapterHard(this, list);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        linLayout4 = findViewById(R.id.linearLayout4);

        btnBoardEasy = findViewById(R.id.btnBoardEasy);
        btnBoardMedium = findViewById(R.id.btnBoardMedium);
        btnBoardHard = findViewById(R.id.btnBoardHard);

        btnBoardEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEasy = new Intent(getApplicationContext(), LeaderVersionTwo.class);
                toEasy.putExtra("NameUser",nameGet);
                database.removeEventListener(hardLeader);
                startActivity(toEasy);
                finish();
            }
        });

        btnBoardMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMedium = new Intent(getApplicationContext(), LeaderboardMedium.class);
                toMedium.putExtra("NameUser",nameGet);
                database.removeEventListener(hardLeader);
                startActivity(toMedium);
                finish();
            }
        });

        LoadLeaderboard();


    }



    public void LoadLeaderboard(){

        hardLeader = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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

                list.sort((user1, user2) -> {
                    return Integer.compare(user2.getScoreHard(), user1.getScoreHard());
                });

                linLayout4.setVisibility(View.VISIBLE);

                for(int i=0;i< list.size();i++){
                    String name = list.get(i).getName();
                    if(name.equals(nameGet)){
                        String text = String.valueOf(i+1);
                        Picasso.get().load(list.get(i).getProfile()).into(ProfilePicture);
                        txtNameUser.setText(name);
                        rankval.setText(text);
                        txtScore.setText(String.valueOf(list.get(i).getScoreHard()));
                        break;
                    }
                }

                myAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        database.addValueEventListener(hardLeader);

    }

    @Override
    public void onBackPressed() {
        // Call finish to close the current activity when the back button is pressed
        super.onBackPressed();
        database.removeEventListener(hardLeader);
        Toast.makeText(LeaderboardHard.this, "Back", Toast.LENGTH_LONG).show();
        finish();
    }


}