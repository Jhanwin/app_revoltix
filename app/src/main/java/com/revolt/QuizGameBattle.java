package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizGameBattle extends AppCompatActivity {
    List<String> questions = new ArrayList<>();
    List<String> correctAnswers = new ArrayList<>();
    List<List<String>> confusionChoices = new ArrayList<>();
    DatabaseReference database;
    private RadioGroup answerRadioGroup;
    TextView txt1,timerTextView,scoreTextView,itemTextView,references;
    RadioButton c1,c2,c3,c4;
    private int currentQuestionIndex = 0;
    int score;
    int itemNum = 1;
    int questionCounter;
    private static final long TIMER_DURATION = 20000; // 20 seconds
    private CountDownTimer countDownTimer;
    String data,TextDiff,numOfItem,TextTopicNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game_battle);

        Intent intent = getIntent();
        String TextSub = intent.getStringExtra("TextSub");
        String Mode = intent.getStringExtra("Mode");
        TextTopicNum = intent.getStringExtra("textSubToGet");
        TextDiff = intent.getStringExtra("textDifficulties");
        data = intent.getStringExtra("UserId");
        numOfItem = intent.getStringExtra("NumberOfItem");

        assert numOfItem != null;
        questionCounter = Integer.parseInt(numOfItem);

        assert TextSub != null;
        assert TextTopicNum != null;
        assert TextDiff != null;
        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub).child(TextTopicNum).child(TextDiff);

        txt1 = findViewById(R.id.questionsB);
        c1 = findViewById(R.id.answerOption1B);
        c2 = findViewById(R.id.answerOption2B);
        c3 = findViewById(R.id.answerOption3B);
        c4 = findViewById(R.id.answerOption4B);
        answerRadioGroup = findViewById(R.id.answerRadioGroupB);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        itemTextView = findViewById(R.id.itemTextView);
        references = findViewById(R.id.references);

        String ref = TextTopicNum +" "+ TextSub +" "+ TextDiff +" "+ data +" "+ numOfItem;
        references.setText(ref);

        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

//                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                        String childKey = childSnapshot.getKey();
//                        questions.add(childKey);
//
//                        if (childSnapshot.hasChild( "correct" )) {
//                            DataSnapshot choicesCorrect = childSnapshot.child( "correct" );
//                            String choice1 = choicesCorrect.child( "choices1" ).getValue(String.class);
//                            correctAnswers.add(choice1);
//                        }
//
//                        if (childSnapshot.hasChild( "incorrect" )) {
//                            DataSnapshot choicesIncorrect = childSnapshot.child( "incorrect" );
//                            String choice2 = choicesIncorrect.child( "choices2" ).getValue(String.class);
//                            String choice3 = choicesIncorrect.child( "choices3" ).getValue(String.class);
//                            String choice4 = choicesIncorrect.child( "choices4" ).getValue(String.class);
//
//
//                            List<String> confusion = new ArrayList<>();
//
//                            confusion.add(choice2);
//                            confusion.add(choice3);
//                            confusion.add(choice4);
//
//                            confusionChoices.add(confusion);
//                        }
//
//                        questionCounter--;
//
//                        if(questionCounter==0){
//                            break;
//                        }
//
//                    }

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
                        String correctAnswe = child.child("correct").child("choice1").getValue(String.class);
                        String choice2 = child.child("incorrect").child("choice2").child("choiceText").getValue(String.class);
                        String choice3 = child.child("incorrect").child("choice3").child("choiceText").getValue(String.class);
                        String choice4 = child.child("incorrect").child("choice4").child("choiceText").getValue(String.class);

                        questions.add(question);
                        correctAnswers.add(correctAnswe);

                        List<String> confusion = new ArrayList<>();

                        confusion.add(choice2);
                        confusion.add(choice3);
                        confusion.add(choice4);

                        confusionChoices.add(confusion);

                        // Process the retrieved data as required
                    }


                    displayQuestion();
                    startTimer();
                    scoreTextView.setText("Score: 0");
                    itemTextView.setText("Item "+itemNum);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //the rest of the code
        c1.setOnClickListener(v -> checkAnswer());
        c2.setOnClickListener(v -> checkAnswer());
        c3.setOnClickListener(v -> checkAnswer());
        c4.setOnClickListener(v -> checkAnswer());

    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                finish();
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

    private void SaveDataToDatabase() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        System.out.println("Current Date: " + currentDate);

        // Get the current time
        LocalTime currentTime = LocalTime.now();
        System.out.println("Current Time: " + currentTime);

        // You can also format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedDateTime = currentTime.format(formatter);

        assert data != null;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(data);
        db.child("BattleMode").child(formattedDateTime).child("score").setValue(score);
        db.child("BattleMode").child(formattedDateTime).child("Date").setValue(currentDate.toString());
        db.child("BattleMode").child(formattedDateTime).child("Time").setValue(formattedDateTime);
        db.child("BattleMode").child(formattedDateTime).child("Topic").setValue(TextTopicNum);
        db.child("BattleMode").child(formattedDateTime).child("Difficulty").setValue(TextDiff);

        Intent ScoreBoard = new Intent(getApplicationContext(), ScoreDashboard.class);
        ScoreBoard.putExtra("Score",Integer.toString(score));
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
            Toast.makeText(QuizGameBattle.this, "Correct! ", Toast.LENGTH_SHORT).show();
            score++;
            scoreTextView.setText("Score: "+score);
        } else {
            Toast.makeText(QuizGameBattle.this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        resetTimer();
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







}