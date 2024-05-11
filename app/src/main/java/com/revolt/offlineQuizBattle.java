package com.revolt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class offlineQuizBattle extends AppCompatActivity {

    List<String> questions = new ArrayList<>();
    List<String> correctAnswers = new ArrayList<>();
    List<List<String>> confusionChoices = new ArrayList<>();



    private RadioGroup answerRadioGroup;
    TextView questionsB,timerTextView,itemTextView, textDebug;
    RadioButton c1,c2,c3,c4;
    private int currentQuestionIndex = 0;
    int score;
    int itemNum = 1;
    int item = 1;
    int questionCounter, numTime;
    //    private static final long TIMER_DURATION = 10000; // 20 seconds
    long TIMER_DURATION;

    private CountDownTimer countDownTimer;

    String subject, offTopicGet, mode, diff, numItem, selectedTime;
    int numItem_int, selectedTime_int;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_quiz_battle);


        Intent intent = getIntent();
        subject = intent.getStringExtra("subject");
        offTopicGet = intent.getStringExtra("offTopicGet");
        mode = intent.getStringExtra("quizMode");
        diff = intent.getStringExtra("diff");
        numItem = intent.getStringExtra("numItem");
        selectedTime = intent.getStringExtra("selectedTime");

        numItem_int = Integer.parseInt(numItem);
        selectedTime_int = Integer.parseInt(selectedTime);

//        Toast.makeText(offlineQuizBattle.this, subject+""+offTopi, Toast.LENGTH_SHORT).show();

        textDebug = findViewById(R.id.textDebug);

//        textDebug.setText(subject+"-"+offTopicGet+"-"+mode+"-"+diff+"-"+numItem+"-"+selectedTime);


        TIMER_DURATION = 3600000L * selectedTime_int;



        timerTextView = findViewById(R.id.timerTextView);
        itemTextView = findViewById(R.id.itemTextView);
        questionsB = findViewById(R.id.questionsB);

        answerRadioGroup = findViewById(R.id.answerRadioGroupB);

        c1 = findViewById(R.id.answerOption1B);
        c2 = findViewById(R.id.answerOption2B);
        c3 = findViewById(R.id.answerOption3B);
        c4 = findViewById(R.id.answerOption4B);

        new ReadJsonTask().execute();


        c1.setOnClickListener(v -> {
            checkAnswer();
        });
        c2.setOnClickListener(v -> {
            checkAnswer();
        });
        c3.setOnClickListener(v -> {
            checkAnswer();
        });
        c4.setOnClickListener(v -> {
            checkAnswer();
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
            displayQuestion();
            itemTextView.setText("Item "+itemNum);
            startTimer();
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
            JSONObject easyObject = acElectricalCircuitsObject.getJSONObject(diff);

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

                questions.add(questionText);
                correctAnswers.add(correctChoice);

                List<String> confusion = new ArrayList<>();

                confusion.add(choice2);
                confusion.add(choice3);
                confusion.add(choice4);

                confusionChoices.add(confusion);

                if(item == numItem_int){
                    break;
                }else{
                    item++;
                }
            }

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












    private void startTimer() {
        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
//                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
                long seconds = millisUntilFinished / 1000;
                long hours = seconds / 3600;
                long minutes = (seconds % 3600) / 60;
                seconds = seconds % 60;

                @SuppressLint("DefaultLocale") String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timerTextView.setText("Time: " + timeString);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                onDestroy();

            }
        }.start();
    }

//    private void resetTimer() {
//        countDownTimer.cancel();
//        startTimer();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


















    private void displayQuestion() {

        questionsB.setText(questions.get(currentQuestionIndex));

        //resetting the radio button
        answerRadioGroup.clearCheck();

        //using set answer options
        setAnswerOptions();
    }


    private void setAnswerOptions(){

        RadioButton option1 = findViewById(R.id.answerOption1B);
        RadioButton option2 = findViewById(R.id.answerOption2B);
        RadioButton option3 = findViewById(R.id.answerOption3B);
        RadioButton option4 = findViewById(R.id.answerOption4B);

        List<String> confusionRow = confusionChoices.get(currentQuestionIndex);

        // Create a list of answer options
        List<String> answerOptions = Arrays.asList(
                correctAnswers.get(currentQuestionIndex),
                confusionRow.get(0),
                confusionRow.get(1),
                confusionRow.get(2)
        );

        // Shuffle the list
        Collections.shuffle(answerOptions);

        // Set the shuffled options to radio buttons
        option1.setText(answerOptions.get(0));
        option2.setText(answerOptions.get(1));
        option3.setText(answerOptions.get(2));
        option4.setText(answerOptions.get(3));

    }



    private void checkAnswer(){

        int selectedId = answerRadioGroup.getCheckedRadioButtonId();

        //getting the selected radio button
        RadioButton selectedRadioButton = findViewById(selectedId);

        //getting the text of the selected radio button
        String selectedAnswer = selectedRadioButton.getText().toString();

        //comparing the text to the correctAnswer array if it is correct answer
        if (selectedAnswer.equals(correctAnswers.get(currentQuestionIndex))) {
            Toast.makeText(offlineQuizBattle.this, "correct", Toast.LENGTH_LONG).show();
            score++;
        } else {
            Toast.makeText(offlineQuizBattle.this, "incorrect", Toast.LENGTH_LONG).show();
        }

        currentQuestionIndex++;
        // Move to the next question
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
            itemNum++;
            itemTextView.setText("Item " +itemNum);
        } else {

            questions.clear();
            correctAnswers.clear();
            confusionChoices.clear();
            Toast.makeText(offlineQuizBattle.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
            Intent offScoreDash = new Intent(getApplicationContext(), offScoreBoard.class);
            offScoreDash.putExtra("score", String.valueOf(score));
            startActivity(offScoreDash);
            finish();

            //ending of quiz
        }



    }








}