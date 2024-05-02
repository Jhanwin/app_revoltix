package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizGameBattle extends AppCompatActivity {
    List<String> questions = new ArrayList<>();
    List<String> correctAnswers = new ArrayList<>();
    List<List<String>> confusionChoices = new ArrayList<>();
    List<String> imageQuestion = new ArrayList<>();

    List<String> selected = new ArrayList<>();

    List<String> remarks = new ArrayList<>();


    ImageView imgQues;
    DatabaseReference database,db;
    private RadioGroup answerRadioGroup;
    TextView txt1,timerTextView,scoreTextView,itemTextView;
    RadioButton c1,c2,c3,c4;
    private int currentQuestionIndex = 0;
    int score;
    int itemNum = 1;
    int questionCounter, numTime;
//    private static final long TIMER_DURATION = 10000; // 20 seconds
    long TIMER_DURATION;

    private CountDownTimer countDownTimer;
    String data,TextDiff,numOfItem,TextTopicNum,selectedTime;
    String scoreEasy, scoreMedium, scoreHard, hexID;
    MediaPlayer Right, Wrong;

    ValueEventListener valueEventListener, getTheChildCount;

    boolean exists;

    long take;

    String setTimeKey;

    LocalDate currentDate;
    LocalTime currentTime;
    DateTimeFormatter formatter;

    String formattedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game_battle);

        Intent intent = getIntent();
        String TextSub = intent.getStringExtra("TextSub");
//        String Mode = intent.getStringExtra("Mode");
        TextTopicNum = intent.getStringExtra("textSubToGet");
        TextDiff = intent.getStringExtra("textDifficulties");
        data = intent.getStringExtra("UserId");
        numOfItem = intent.getStringExtra("NumberOfItem");
        selectedTime = intent.getStringExtra("selectedTime");



        assert numOfItem != null;
        questionCounter = Integer.parseInt(numOfItem);

        assert selectedTime != null;
        numTime = Integer.parseInt(selectedTime);

        TIMER_DURATION = 3600000L * 1;

        assert TextSub != null;
        assert TextTopicNum != null;
        assert TextDiff != null;

        db = FirebaseDatabase.getInstance().getReference("users");

        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub).child(TextTopicNum).child(TextDiff);

        txt1 = findViewById(R.id.questionsB);
        c1 = findViewById(R.id.answerOption1B);
        c2 = findViewById(R.id.answerOption2B);
        c3 = findViewById(R.id.answerOption3B);
        c4 = findViewById(R.id.answerOption4B);
        answerRadioGroup = findViewById(R.id.answerRadioGroupB);
        timerTextView = findViewById(R.id.timerTextView);
        itemTextView = findViewById(R.id.itemTextView);

//        Right = MediaPlayer.create(this, R.raw.correct);
//        Wrong = MediaPlayer.create(this, R.raw.wrong);

        imgQues = findViewById(R.id.imgQues);


        setKey();

        hexID = generateHexID(setTimeKey);

//        getTheChildCount = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Check if the key exists
//                if (dataSnapshot.hasChild("BattleMode")) {
//                    exists = true;
//                    long count = dataSnapshot.child("BattleMode").getChildrenCount();
//                    assert hexID != null;
//                    db.child(data).child("BattleMode").child(hexID).child("Take").setValue(count+1);
//
//
//                }else{
//                    assert hexID != null;
//                    db.child(data).child("BattleMode").child(hexID).child("Take").setValue(1);
//
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle error
//                System.err.println("Error reading from database: " + databaseError.getMessage());
//            }
//        };
//
//        db.child(data).addListenerForSingleValueEvent(getTheChildCount);





        valueEventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    List<DataSnapshot> children = new ArrayList<>();

                    // Iterate through all children nodes and add them to the list
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        children.add(childSnapshot);
                    }

                    // Shuffle the list randomly
                    Collections.shuffle(children);

                    // Retrieve a subset of children nodes based on your desired number
                    List<DataSnapshot> randomChildren = children.subList(0, questionCounter);

                    // Now you can process the randomly selected children nodes
                    for (DataSnapshot child : randomChildren) {
                        // Retrieve data from each child node as needed
                        String question = child.child("question").child("questionText").getValue(String.class);
                        String correctAns = child.child("correct").child("choice1").getValue(String.class);
                        String choice2 = child.child("incorrect").child("choice2").child("choiceText").getValue(String.class);
                        String choice3 = child.child("incorrect").child("choice3").child("choiceText").getValue(String.class);
                        String choice4 = child.child("incorrect").child("choice4").child("choiceText").getValue(String.class);

                        if(child.child("question").hasChild("questionImageURL")){
                            String questionImage = child.child("question").child("questionImageURL").getValue(String.class);
                            imageQuestion.add(questionImage);
                        }else {
                            imageQuestion.add("blank");
                        }

                        questions.add(question);
                        correctAnswers.add(correctAns);

                        List<String> confusion = new ArrayList<>();

                        confusion.add(choice2);
                        confusion.add(choice3);
                        confusion.add(choice4);

                        confusionChoices.add(confusion);
                        // Process the retrieved data as required
                    }


                    displayQuestion();
                    startTimer();
                    itemTextView.setText("Item "+itemNum);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        database.addValueEventListener(valueEventListener);

        //the rest of the code
        c1.setOnClickListener(v -> {
            checkAnswer();
//            setToFalse();
        });
        c2.setOnClickListener(v -> {
            checkAnswer();
//            setToFalse();
        });
        c3.setOnClickListener(v -> {
            checkAnswer();
//            setToFalse();
        });
        c4.setOnClickListener(v -> {
            checkAnswer();
//            setToFalse();
        });

    }

    public void setKey(){
        currentDate = LocalDate.now();
        System.out.println("Current Date: " + currentDate);

        // Get the current time
        currentTime = LocalTime.now();
        System.out.println("Current Time: " + currentTime);

        // You can also format the date and time
        formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        formattedDateTime = currentTime.format(formatter);

        setTimeKey = formattedDateTime;

//        hexID = generateHexID(formattedDateTime);
    }

    public void setToFalse(){
        c1.setClickable(false);
        c2.setClickable(false);
        c3.setClickable(false);
        c4.setClickable(false);
    }

    public void setToTrue(){
        c1.setClickable(true);
        c2.setClickable(true);
        c3.setClickable(true);
        c4.setClickable(true);
    }





    private void playRight() {
        if (Right != null) {
            Right.start(); // Start playing the sound
        }
    }

    private void playWrong() {
        if (Wrong != null) {
            Wrong.start(); // Start playing the sound
        }
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
                currentQuestionIndex++;

                // Move to the next question
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                    itemNum++;
                    itemTextView.setText("Item " +itemNum);
                } else {
                    countDownTimer.cancel();
                    questions.clear();
                    correctAnswers.clear();
                    confusionChoices.clear();
                    Toast.makeText(QuizGameBattle.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                    SaveDataToDatabase();
                    finish();
                    //ending of quiz
                }
            }
        }.start();
    }

    private void resetTimer() {
        countDownTimer.cancel();
        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void displayQuestion() {

//        Toast.makeText(QuizGameBattle.this, hexID, Toast.LENGTH_LONG).show();

//        setToTrue();

        if(!imageQuestion.get(currentQuestionIndex).equals("blank")){
            Picasso.get().load(imageQuestion.get(currentQuestionIndex)).into(imgQues);
            imgQues.setVisibility(View.VISIBLE);
        }else{
            imgQues.setVisibility(View.GONE);
        }

        txt1.setText(questions.get(currentQuestionIndex));

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




    private static String generateHexID(String input) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add input string to digest
            md.update(input.getBytes());

            // Generate the hash
            byte[] byteData = md.digest();

            // Convert byte array to hex format
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }









    private void SaveDataToDatabase() {


//        db = FirebaseDatabase.getInstance().getReference("users");





        assert hexID != null;
        db.child(data).child("BattleMode").child(hexID).child("score").setValue(score);
        db.child(data).child("BattleMode").child(hexID).child("Date").setValue(currentDate.toString());
        db.child(data).child("BattleMode").child(hexID).child("Time").setValue(formattedDateTime);
        db.child(data).child("BattleMode").child(hexID).child("Topic").setValue(TextTopicNum);
        db.child(data).child("BattleMode").child(hexID).child("Difficulty").setValue(TextDiff);

        Intent ScoreBoard = new Intent(getApplicationContext(), ScoreDashboard.class);
        ScoreBoard.putExtra("Score",Integer.toString(score));
        ScoreBoard.putExtra("numOfItem",numOfItem);
        ScoreBoard.putExtra("UserId", data);
        startActivity(ScoreBoard);
    }


    @SuppressLint("SetTextI18n")
    private void checkAnswer(){

        int selectedId = answerRadioGroup.getCheckedRadioButtonId();

        //getting the selected radio button
        RadioButton selectedRadioButton = findViewById(selectedId);

        //getting the text of the selected radio button
        String selectedAnswer = selectedRadioButton.getText().toString();

        //comparing the text to the correctAnswer array if it is correct answer
        if (selectedAnswer.equals(correctAnswers.get(currentQuestionIndex))) {
//            playRight();
//            Toast.makeText(QuizGameBattle.this, "Correct! ", Toast.LENGTH_SHORT).show();
            String questionKey = "question" + (currentQuestionIndex + 1);
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions.get(currentQuestionIndex));
            questionData.put("selectedAnswer", selectedAnswer);
            questionData.put("remarks", "correct");
            questionData.put("correctAnswer", correctAnswers.get(currentQuestionIndex));
            db.child(data).child("BattleMode").child(hexID).child("Item").child(questionKey).setValue(questionData);
            score++;
        } else {
//            playWrong();
            String questionKey = "question" + (currentQuestionIndex + 1);
//            Toast.makeText(QuizGameBattle.this, "Incorrect!", Toast.LENGTH_SHORT).show();
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", questions.get(currentQuestionIndex));
            questionData.put("selectedAnswer", selectedAnswer);
            questionData.put("remarks", "wrong");
            questionData.put("correctAnswer", correctAnswers.get(currentQuestionIndex));
            db.child(data).child("BattleMode").child(hexID).child("Item").child(questionKey).setValue(questionData);

        }

        currentQuestionIndex++;
        // Move to the next question
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
            itemNum++;
            itemTextView.setText("Item " +itemNum);
        } else {
            SaveDataToDatabase();
            countDownTimer.cancel();
            questions.clear();
            correctAnswers.clear();
            confusionChoices.clear();
            imageQuestion.clear();
            selected.clear();
            remarks.clear();
            database.removeEventListener(valueEventListener);
//            db.child(data).removeEventListener(getTheChildCount);
            Toast.makeText(QuizGameBattle.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
            finish();

            //ending of quiz
        }

//        Right.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                resetTimer();
//                currentQuestionIndex++;
//                // Move to the next question
//                if (currentQuestionIndex < questions.size()) {
//                    displayQuestion();
//                    itemNum++;
//                    itemTextView.setText("Item " +itemNum);
//                } else {
//                    SaveDataToDatabase();
//                    countDownTimer.cancel();
//                    questions.clear();
//                    correctAnswers.clear();
//                    confusionChoices.clear();
//                    imageQuestion.clear();
//                    selected.clear();
//                    remarks.clear();
//                    database.removeEventListener(valueEventListener);
////                    db.child(data).removeEventListener(getTheChildCount);
//                    Toast.makeText(QuizGameBattle.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
//                    finish();
//
//                    //ending of quiz
//                }
//            }
//        });
//
//        Wrong.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                resetTimer();
//                currentQuestionIndex++;
//                // Move to the next question
//                if (currentQuestionIndex < questions.size()) {
//                    displayQuestion();
//                    itemNum++;
//                    itemTextView.setText("Item " +itemNum);
//                } else {
//                    SaveDataToDatabase();
//                    countDownTimer.cancel();
//                    questions.clear();
//                    correctAnswers.clear();
//                    confusionChoices.clear();
//                    imageQuestion.clear();
//                    selected.clear();
//                    remarks.clear();
//                    database.removeEventListener(valueEventListener);
//                    db.child(data).removeEventListener(getTheChildCount);
//                    Toast.makeText(QuizGameBattle.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
//                    finish();
//
//                    //ending of quiz
//                }
//            }
//        });


    }



}