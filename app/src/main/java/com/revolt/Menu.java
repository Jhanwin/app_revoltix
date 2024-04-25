package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Menu extends AppCompatActivity {

    String data,link,email,name,substringToRemove,modCode;
    Button BtnPracticeMode,BtnBattleMode,BtnLeaderboards,PretestMode,addQuestionToDb;
    TextView txtName,srCode;
    ImageView profileUser,btnToUserProfile, menuBtn, app;

    MenuItem menuitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtName = findViewById(R.id.txtName);
        srCode = findViewById(R.id.srCode);
        profileUser = findViewById(R.id.ProfilePic);

        Intent intent = getIntent();
        data = intent.getStringExtra("id");
        link = intent.getStringExtra("link");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        substringToRemove = "@g.batstate-u.edu.ph";
        modCode = email.replace(substringToRemove, "");


        txtName.setText(name);
        srCode.setText(modCode);
        Picasso.get().load(link).into(profileUser);

        BtnPracticeMode = findViewById(R.id.PracticeMode);
        BtnBattleMode = findViewById(R.id.BattleMode);
        BtnLeaderboards = findViewById(R.id.btnLeaderboards);
        PretestMode = findViewById(R.id.PretestMode);
//        addQuestionToDb = findViewById(R.id.addQuestionToDb);
        btnToUserProfile = findViewById(R.id.btnToUserProfile);

        menuBtn = findViewById(R.id.menuBtn);

        final DrawerLayout dlayout = findViewById(R.id.drawerLayout);

        LinearLayout lt = findViewById(R.id.buttonsLayout);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dlayout.openDrawer(GravityCompat.START);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lt.getLayoutParams();
                layoutParams.gravity = Gravity.NO_GRAVITY; // Setting gravity to NO_GRAVITY removes it
                lt.setLayoutParams(layoutParams);
            }
        });

        BtnPracticeMode.setOnClickListener(v -> {
            Intent subjects = new Intent(getApplicationContext(), Home.class);
            subjects.putExtra("Mode","PracticeMode");
            subjects.putExtra("UserId",data);
            startActivity(subjects);
        });

        PretestMode.setOnClickListener(v -> {
            Intent pre = new Intent(getApplicationContext(), PretestGame.class);

            startActivity(pre);
        });

//        addQuestionToDb.setOnClickListener(v -> {
//            Intent add = new Intent(getApplicationContext(), AddQuestion.class);
//            startActivity(add);
//        });

        BtnLeaderboards.setOnClickListener(v -> {
            Intent leaderboards = new Intent(getApplicationContext(), LeaderVersionTwo.class);
            leaderboards.putExtra("NameUser",name);
            startActivity(leaderboards);
        });

        BtnBattleMode.setOnClickListener(v -> {
            Intent add = new Intent(getApplicationContext(), Home.class);
            add.putExtra("Mode","BattleMode");
            add.putExtra("UserId",data);
            startActivity(add);
        });

        btnToUserProfile.setOnClickListener(v -> {
            Intent profile = new Intent(getApplicationContext(), UserProfile.class);
            profile.putExtra("idprofile",data);
            startActivity(profile);
        });


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, (android.view.Menu) menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Home) {
            Toast.makeText(Menu.this, "Home", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }








}