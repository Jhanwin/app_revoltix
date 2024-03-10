package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView txtName = findViewById(R.id.txtName);
        TextView srCode = findViewById(R.id.srCode);
        ImageView profileUser = findViewById(R.id.ProfilePic);

        Intent intent = getIntent();
        String data = intent.getStringExtra("id");
        String link = intent.getStringExtra("link");
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");

        String substringToRemove = "@g.batstate-u.edu.ph";
        // Remove the specified substring
        String modCode = email.replace(substringToRemove, "");

        txtName.setText(name);
        srCode.setText(modCode);

        Picasso.get().load(link).into(profileUser);

        Button BtnPracticeMode = findViewById(R.id.PracticeMode);
        Button BtnBattleMode = findViewById(R.id.BattleMode);

        BtnPracticeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subjects = new Intent(getApplicationContext(), Home.class);
                startActivity(subjects);

            }
        });

        BtnPracticeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subjects = new Intent(getApplicationContext(), Home.class);
                startActivity(subjects);

            }
        });







    }
}