package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizGame extends AppCompatActivity {
    List<String> questions = new ArrayList<>();
    List<String> imageLink = new ArrayList<>();
    List<String> correctAnswers = new ArrayList<>();
    List<List<String>> confusionChoices = new ArrayList<>();
    DatabaseReference database;
    Button next;
    LinearLayout LayoutL;
    ImageView imageShow,imageShowWrong;
    private RadioGroup answerRadioGroup;
    TextView txt1,ShowCorrectAns,ShowCorrectAns2,itemTextView;
    RadioButton c1,c2,c3,c4;
    private int currentQuestionIndex = 0;
    int questionCounter;

    MediaPlayer Right, Wrong;

    ValueEventListener loadQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        Intent intent = getIntent();
        String TextTopicNum = intent.getStringExtra("textSubToGet");
        String TextSub = intent.getStringExtra("TextSub");
        String TextDiff = intent.getStringExtra("textDifficulties");
        String Mode = intent.getStringExtra("Mode");
        String numOfItem = intent.getStringExtra("NumberOfItem");
//        String data = intent.getStringExtra("UserId");

        assert TextSub != null;
        assert TextTopicNum != null;
        assert TextDiff != null;
        assert Mode != null;

        assert numOfItem != null;
        questionCounter = Integer.parseInt(numOfItem);

        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub).child(TextTopicNum).child(TextDiff);

        txt1 = findViewById(R.id.questions);
        c1 = findViewById(R.id.answerOption1);
        c2 = findViewById(R.id.answerOption2);
        c3 = findViewById(R.id.answerOption3);
        c4 = findViewById(R.id.answerOption4);

        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        LayoutL = findViewById(R.id.LayoutImageAppear);

        ShowCorrectAns = findViewById(R.id.ShowCorrectAns);
        ShowCorrectAns2 = findViewById(R.id.ShowCorrectAns2);
        ImageView goHome = findViewById(R.id.goHome);

        itemTextView = findViewById(R.id.itemTextView);

        Right = MediaPlayer.create(this, R.raw.correct);
        Wrong = MediaPlayer.create(this, R.raw.wrong);


        loadQuestions = new ValueEventListener() {
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

                        if(child.child("correct").hasChild("choiceImageURL1")){
                            String correctAnsImage = child.child("correct").child("choiceImageURL1").getValue(String.class);
                            correctAnswers.add(correctAnsImage);
                        }else{
                            correctAnswers.add(correctAns);
                        }

                        questions.add(question);
//                        correctAnswers.add(correctAns);

                        List<String> confusion = new ArrayList<>();

                                confusion.add(choice2);
                                confusion.add(choice3);
                                confusion.add(choice4);

                                confusionChoices.add(confusion);

                        // Process the retrieved data as required
                    }

                    displayQuestion();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        database.addValueEventListener(loadQuestions);

        //the rest of the code
        c1.setOnClickListener(v -> checkAnswer());
        c2.setOnClickListener(v -> checkAnswer());
        c3.setOnClickListener(v -> checkAnswer());
        c4.setOnClickListener(v -> checkAnswer());


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

    private void displayQuestion() {
        itemTextView.setText("Item "+(currentQuestionIndex+1));
        txt1.setText(questions.get(currentQuestionIndex));

        //resetting the radio button
        answerRadioGroup.clearCheck();

        //using set answer options
        setAnswerOptions();
    }


    private void setAnswerOptions(){

        RadioButton option1 = findViewById(R.id.answerOption1);
        RadioButton option2 = findViewById(R.id.answerOption2);
        RadioButton option3 = findViewById(R.id.answerOption3);
        RadioButton option4 = findViewById(R.id.answerOption4);

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

    @SuppressLint("SetTextI18n")
    private void checkAnswer(){

        int selectedId = answerRadioGroup.getCheckedRadioButtonId();
        next = findViewById(R.id.btnNext);
        answerRadioGroup.setVisibility(View.GONE);

        //getting the selected radio button
        RadioButton selectedRadioButton = findViewById(selectedId);

        //getting the text of the selected radio button
        String selectedAnswer = selectedRadioButton.getText().toString();

        //comparing the text to the correctAnswer array if it is correct answer
        if (selectedAnswer.equals(correctAnswers.get(currentQuestionIndex))) {
            playRight();
//            Toast.makeText(QuizGame.this, "Correct! ", Toast.LENGTH_SHORT).show();
            ShowCorrectAns.setText("You are Correct!");
            ShowCorrectAns2.setText(correctAnswers.get(currentQuestionIndex));

//            imageShow.setVisibility(View.VISIBLE);
        } else {
            playWrong();
//            Toast.makeText(QuizGame.this, "Incorrect!", Toast.LENGTH_SHORT).show();
            ShowCorrectAns.setText("The Correct Answer is");
            ShowCorrectAns2.setText(correctAnswers.get(currentQuestionIndex));

//            imageShowWrong.setVisibility(View.VISIBLE);
        }

        ShowCorrectAns.setVisibility(View.VISIBLE);
        ShowCorrectAns2.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);

        next.setOnClickListener(v -> {
            currentQuestionIndex++;
            // Move to the next question
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                Toast.makeText(QuizGame.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                database.removeEventListener(loadQuestions);
                questions.clear();
                imageLink.clear();
                correctAnswers.clear();
                confusionChoices.clear();
                finish();
            }
            next.setVisibility(View.GONE);
//            imageShow.setVisibility(View.GONE);
//            imageShowWrong.setVisibility(View.GONE);
            ShowCorrectAns.setVisibility(View.GONE);
            ShowCorrectAns2.setVisibility(View.GONE);
            answerRadioGroup.setVisibility(View.VISIBLE);
        });

    }

}