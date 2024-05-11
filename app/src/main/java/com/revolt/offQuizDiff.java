package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class offQuizDiff extends AppCompatActivity {


    Button btnOffEasy, btnOffMedium, btnOffHard;
    String subject, offTopicGet, mode;

    EditText numOfItem;

    String selectedItem;

    int countEasy, countMedium, countHard;

    LinearLayout layoutOff;

    ImageView goBackOff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_off_quiz_diff);

        new ReadJsonTask().execute();

        layoutOff = findViewById(R.id.layoutOff);

        goBackOff = findViewById(R.id.goBackOff);

        goBackOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOffEasy = findViewById(R.id.btnOffEasy);
        btnOffMedium = findViewById(R.id.btnOffMedium);
        btnOffHard = findViewById(R.id.btnOffHard);

        numOfItem = findViewById(R.id.numOfItem);

        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        offTopicGet = intent.getStringExtra("offTopicGet");
        mode = intent.getStringExtra("quizMode");

        TextView textTopicNum = findViewById(R.id.textTopicNum);

        textTopicNum.setText(offTopicGet);

        Spinner spinner = findViewById(R.id.spinnerOff);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_options, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Perform action based on selected item
                selectedItem = spinner.getSelectedItem().toString();
                Toast.makeText(offQuizDiff.this, selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        if(mode.equalsIgnoreCase("PracticeMode")){
            spinner.setVisibility(View.GONE);
        }else{
            spinner.setVisibility(View.VISIBLE);
        }


        btnOffEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equalsIgnoreCase("PracticeMode")){

                    if(numOfItem.getText().toString().equals("")){
                        Toast.makeText(offQuizDiff.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();
                    }else if(numOfItem.getText().toString().equals("0")){
                        Toast.makeText(offQuizDiff.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(numOfItem.getText().toString()) > countEasy){
                        Toast.makeText(offQuizDiff.this, "Too much value. Number of Question:"+countEasy, Toast.LENGTH_SHORT).show();
                    }else{

                        Intent offQuizBattle = new Intent(getApplicationContext(), offPractice.class);
                        offQuizBattle.putExtra("subject", subject);
                        offQuizBattle.putExtra("offTopicGet", offTopicGet);
                        offQuizBattle.putExtra("diff", btnOffEasy.getText().toString());
                        offQuizBattle.putExtra("quizMode", mode);
                        offQuizBattle.putExtra("numItem", numOfItem.getText().toString());
                        startActivity(offQuizBattle);

                    }

                }else{

                    if(numOfItem.getText().toString().equals("")){
                        Toast.makeText(offQuizDiff.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();
                    }else if(numOfItem.getText().toString().equals("0")){
                        Toast.makeText(offQuizDiff.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(numOfItem.getText().toString()) > countEasy){
                        Toast.makeText(offQuizDiff.this, "Too much value. Number of Question:"+countEasy, Toast.LENGTH_SHORT).show();
                    }else if (selectedItem.equalsIgnoreCase("Hours")) {
                        Toast.makeText(offQuizDiff.this, "Select Time", Toast.LENGTH_SHORT).show();
                    }else{

                        Intent offQuizBattle = new Intent(getApplicationContext(), offlineQuizBattle.class);
                        offQuizBattle.putExtra("subject", subject);
                        offQuizBattle.putExtra("offTopicGet", offTopicGet);
                        offQuizBattle.putExtra("diff", btnOffEasy.getText().toString());
                        offQuizBattle.putExtra("quizMode", mode);
                        offQuizBattle.putExtra("numItem", numOfItem.getText().toString());
                        offQuizBattle.putExtra("selectedTime", selectedItem);
                        startActivity(offQuizBattle);

                    }
                }

            }
        });

        btnOffMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equalsIgnoreCase("PracticeMode")){

                    if(numOfItem.getText().toString().equals("")){
                        Toast.makeText(offQuizDiff.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();
                    }else if(numOfItem.getText().toString().equals("0")){
                        Toast.makeText(offQuizDiff.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(numOfItem.getText().toString()) > countMedium){
                        Toast.makeText(offQuizDiff.this, "Too much value. Number of Question:"+countMedium, Toast.LENGTH_SHORT).show();
                    }else {
                        Intent offQuizBattle = new Intent(getApplicationContext(), offPractice.class);
                        offQuizBattle.putExtra("subject", subject);
                        offQuizBattle.putExtra("offTopicGet", offTopicGet);
                        offQuizBattle.putExtra("diff", btnOffMedium.getText().toString());
                        offQuizBattle.putExtra("quizMode", mode);
                        offQuizBattle.putExtra("numItem", numOfItem.getText().toString());
                        startActivity(offQuizBattle);
                    }

                }else{

                    if(numOfItem.getText().toString().equals("")){
                        Toast.makeText(offQuizDiff.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();
                    }else if(numOfItem.getText().toString().equals("0")){
                        Toast.makeText(offQuizDiff.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(numOfItem.getText().toString()) > countMedium){
                        Toast.makeText(offQuizDiff.this, "Too much value. Number of Question:"+countMedium, Toast.LENGTH_SHORT).show();
                    }else if (selectedItem.equalsIgnoreCase("Hours")) {
                        Toast.makeText(offQuizDiff.this, "Select Time", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent offQuizBattle = new Intent(getApplicationContext(), offlineQuizBattle.class);
                        offQuizBattle.putExtra("subject", subject);
                        offQuizBattle.putExtra("offTopicGet", offTopicGet);
                        offQuizBattle.putExtra("diff", btnOffMedium.getText().toString());
                        offQuizBattle.putExtra("quizMode", mode);

                        offQuizBattle.putExtra("numItem", numOfItem.getText().toString());
                        offQuizBattle.putExtra("selectedTime", selectedItem);
                        startActivity(offQuizBattle);
                    }
                }

            }
        });

        btnOffHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mode.equalsIgnoreCase("PracticeMode")){

                    if(numOfItem.getText().toString().equals("")){
                        Toast.makeText(offQuizDiff.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();
                    }else if(numOfItem.getText().toString().equals("0")){
                        Toast.makeText(offQuizDiff.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(numOfItem.getText().toString()) > countHard){
                        Toast.makeText(offQuizDiff.this, "Too much value. Number of Question:"+countHard, Toast.LENGTH_SHORT).show();
                    }else {

                        Intent offQuizBattle = new Intent(getApplicationContext(), offPractice.class);
                        offQuizBattle.putExtra("subject", subject);
                        offQuizBattle.putExtra("offTopicGet", offTopicGet);
                        offQuizBattle.putExtra("diff", btnOffHard.getText().toString());
                        offQuizBattle.putExtra("quizMode", mode);
                        offQuizBattle.putExtra("numItem", numOfItem.getText().toString());
                        startActivity(offQuizBattle);
                    }

                }else{

                    if(numOfItem.getText().toString().equals("")){
                        Toast.makeText(offQuizDiff.this, "Please enter the Number of Item", Toast.LENGTH_SHORT).show();
                    }else if(numOfItem.getText().toString().equals("0")){
                        Toast.makeText(offQuizDiff.this, "Enter the Number higher than 0", Toast.LENGTH_SHORT).show();
                    }else if (Integer.parseInt(numOfItem.getText().toString()) > countHard){
                        Toast.makeText(offQuizDiff.this, "Too much value. Number of Question:"+countHard, Toast.LENGTH_SHORT).show();
                    }else if (selectedItem.equalsIgnoreCase("Hours")) {
                        Toast.makeText(offQuizDiff.this, "Select Time", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent offQuizBattle = new Intent(getApplicationContext(), offlineQuizBattle.class);
                        offQuizBattle.putExtra("subject", subject);
                        offQuizBattle.putExtra("offTopicGet", offTopicGet);
                        offQuizBattle.putExtra("diff", btnOffHard.getText().toString());
                        offQuizBattle.putExtra("quizMode", mode);

                        offQuizBattle.putExtra("numItem", numOfItem.getText().toString());
                        offQuizBattle.putExtra("selectedTime", selectedItem);
                        startActivity(offQuizBattle);
                    }
                }

            }
        });

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
            layoutOff.setVisibility(View.VISIBLE);
            Toast.makeText(offQuizDiff.this, countEasy+" "+countMedium+" "+countHard, Toast.LENGTH_LONG).show();

        }
    }

    private void parseJsonAndPopulateUI(String jsonData) {
        TextView textView = findViewById(R.id.textView);
        StringBuilder resultBuilder = new StringBuilder();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject appDataObject = jsonObject.getJSONObject("AppData");
            JSONObject electronicsEngineeringObject = appDataObject.getJSONObject(subject);
            JSONObject acElectricalCircuitsObject = electronicsEngineeringObject.getJSONObject(offTopicGet);

            JSONObject easyObject = acElectricalCircuitsObject.getJSONObject("Easy");
            countEasy = easyObject.length();

            JSONObject mediumObject = acElectricalCircuitsObject.getJSONObject("Medium");
            countMedium = mediumObject.length();

            JSONObject hardObject = acElectricalCircuitsObject.getJSONObject("Hard");
            countHard = hardObject.length();

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