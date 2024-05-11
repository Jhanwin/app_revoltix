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
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Menu extends AppCompatActivity {

    String data,link,email,name,substringToRemove,modCode;
    Button BtnPracticeMode,BtnBattleMode,BtnLeaderboards,BtnOfflineMode;
    TextView txtName,srCode;
    ImageView profileUser,btnToUserProfile, menuBtn, app;

    MenuItem menuitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);




        if(isOnline(getApplicationContext())) {
            // Device is connected to the internet
            Toast.makeText(Menu.this, "You are online!!!", Toast.LENGTH_LONG).show();
        } else {
            // Device is not connected to the internet
            Toast.makeText(Menu.this, "You are offline! Connect to the internet", Toast.LENGTH_LONG).show();
        }



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
        BtnOfflineMode = findViewById(R.id.OfflineMode);


        final DrawerLayout dlayout = findViewById(R.id.drawerLayout);

        LinearLayout lt = findViewById(R.id.buttonsLayout);



        BtnPracticeMode.setOnClickListener(v -> {
            if(isOnline(getApplicationContext())) {
                Intent subjects = new Intent(getApplicationContext(), Home.class);
                subjects.putExtra("Mode","PracticeMode");
                subjects.putExtra("UserId",data);
                startActivity(subjects);
            } else {
                // Device is not connected to the internet
                Toast.makeText(Menu.this, "You are offline! Connect to the internet", Toast.LENGTH_LONG).show();
            }


        });

        BtnOfflineMode.setOnClickListener(v -> {
            Intent offlineMode = new Intent(getApplicationContext(), ModeOption.class);
            startActivity(offlineMode);
        });



        BtnLeaderboards.setOnClickListener(v -> {
            if(isOnline(getApplicationContext())) {
                Intent leaderboards = new Intent(getApplicationContext(), LeaderVersionTwo.class);
                leaderboards.putExtra("NameUser", name);
                startActivity(leaderboards);
            }else {
                // Device is not connected to the internet
                Toast.makeText(Menu.this, "You are offline! Connect to the internet", Toast.LENGTH_LONG).show();
            }
        });

        BtnBattleMode.setOnClickListener(v -> {

            if(isOnline(getApplicationContext())) {
                Intent add = new Intent(getApplicationContext(), Home.class);
                add.putExtra("Mode", "BattleMode");
                add.putExtra("UserId", data);
                startActivity(add);
            }else {
                // Device is not connected to the internet
                Toast.makeText(Menu.this, "You are offline! Connect to the internet", Toast.LENGTH_LONG).show();
            }

        });

        profileUser.setOnClickListener(v -> {
            if(isOnline(getApplicationContext())) {
                Intent profile = new Intent(getApplicationContext(), UserProfile.class);
                profile.putExtra("idprofile", data);
                startActivity(profile);
            }else {
                // Device is not connected to the internet
                Toast.makeText(Menu.this, "You are offline! Connect to the internet", Toast.LENGTH_LONG).show();
            }
        });


    }





    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }










}