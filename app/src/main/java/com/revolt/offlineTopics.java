package com.revolt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class offlineTopics extends AppCompatActivity {

    List<String> buttonCont = new ArrayList<>();
    TextView txt;
    String subject,mode;

    ImageView goBackOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_topics);

        txt  = findViewById(R.id.txtLoaded);

        goBackOff = findViewById(R.id.goBackOff);

        goBackOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        mode = intent.getStringExtra("quizMode");

        new ReadJsonTask().execute();

        createButton();


    }

    private void createButton() {

        txt.setVisibility(View.GONE);

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
                    Intent offQuizSubDif = new Intent(getApplicationContext(), offQuizDiff.class);
                    offQuizSubDif.putExtra("offTopicGet", buttonCont.get(finalI));
                    offQuizSubDif.putExtra("quizMode", mode);
                    offQuizSubDif.putExtra("subject", subject);

                    startActivity(offQuizSubDif);

                    Toast.makeText(offlineTopics.this,button.getText().toString(), Toast.LENGTH_LONG).show();

                }
            });

            LinearLayout layout = findViewById(R.id.SubLinearLayout1);
            layout.addView(button);
        }

    }






    @SuppressLint("StaticFieldLeak")
    private class ReadJsonTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return readJSONFromAssets(getApplicationContext(), "revoltix24-default-rtdb-export.json");
        }

        @Override
        protected void onPostExecute(String jsonData) {
            // Now that JSON data is loaded, you can parse it and update UI
            parseJsonAndPopulateUI(jsonData);
            createButton();
            txt.setVisibility(View.GONE);
        }
    }

    private void parseJsonAndPopulateUI(String jsonData) {
        TextView textView = findViewById(R.id.textView);
        StringBuilder resultBuilder = new StringBuilder();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject appDataObject = jsonObject.getJSONObject("AppData");
            JSONObject electronicsEngineeringObject = appDataObject.getJSONObject(subject);

            Iterator<String> topicKey = electronicsEngineeringObject.keys();

            while (topicKey.hasNext()) {
                String key = topicKey.next();
                buttonCont.add(key);
                resultBuilder.append(key+"\n");
            }

            txt.setText(resultBuilder);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON", "Error parsing JSON: " + e.getMessage());
        }
    }

    private String readJSONFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("JSON", "Error reading JSON file: " + e.getMessage());
        }
        return stringBuilder.toString();
    }





}