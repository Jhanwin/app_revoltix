package com.revolt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {



    Button btnLogin;
    TextView linkSignup;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    GoogleSignInClient mGoogleSigninClient;
    int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("144939716643-6b0ad7geas0rb7kicv23uag9e2a86man.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSigninClient = GoogleSignIn.getClient(this,gso);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent Home = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(Home);
//                finish();

                googleSignIn();

            }
        });

    }


    private void googleSignIn(){
        Intent intent = mGoogleSigninClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            if (data != null) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuth(account.getIdToken());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Intent data is null", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void firebaseAuth(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){

                            FirebaseUser user = mAuth.getCurrentUser();

                            HashMap<String,Object> map = new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("name",user.getDisplayName());
                            map.put("profile",user.getPhotoUrl().toString());
                            map.put("email",user.getEmail());

                            database.getReference().child("users").child(user.getUid()).setValue(map);

                            Intent second = new Intent(getApplicationContext(), Menu.class);
                            Toast.makeText(MainActivity.this, user.getUid().toString(), Toast.LENGTH_SHORT).show();
                            second.putExtra("id", user.getUid().toString());
                            second.putExtra("link", user.getPhotoUrl().toString());
                            second.putExtra("name", user.getDisplayName());
                            second.putExtra("email", user.getEmail());
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(second);
                            finish();


                        }else{
                            Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }








}