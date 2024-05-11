package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class OpenJson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_json);


        TextView textView = findViewById(R.id.textView);

        new ReadJsonTask().execute();



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
        }
    }

    private void parseJsonAndPopulateUI(String jsonData) {
        TextView textView = findViewById(R.id.textView);
        StringBuilder resultBuilder = new StringBuilder();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject appDataObject = jsonObject.getJSONObject("AppData");
            JSONObject electronicsEngineeringObject = appDataObject.getJSONObject("Electronics Engineering");
            JSONObject acElectricalCircuitsObject = electronicsEngineeringObject.getJSONObject("AC Electrical Circuits");
            JSONObject easyObject = acElectricalCircuitsObject.getJSONObject("Medium");

            Iterator<String> easyKeysIterator = easyObject.keys();

            while (easyKeysIterator.hasNext()) {
                String questionId = easyKeysIterator.next();
                JSONObject questionObject = easyObject.getJSONObject(questionId);

                String questionText = questionObject.getJSONObject("question").getString("questionText");
                String correctChoice = questionObject.getJSONObject("correct").getString("choice1");

                JSONObject incorrectObject = questionObject.getJSONObject("incorrect");
                String choice2 = incorrectObject.getJSONObject("choice2").getString("choiceText");
                String choice3 = incorrectObject.getJSONObject("choice3").getString("choiceText");
                String choice4 = incorrectObject.getJSONObject("choice4").getString("choiceText");

                resultBuilder.append("Question: ").append(questionText).append("\n");
                resultBuilder.append("Correct Choice: ").append(correctChoice).append("\n");
                resultBuilder.append("Incorrect Choices: \n");
                resultBuilder.append("- ").append(choice2).append("\n");
                resultBuilder.append("- ").append(choice3).append("\n");
                resultBuilder.append("- ").append(choice4).append("\n\n");
            }

            textView.setText(resultBuilder.toString());

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