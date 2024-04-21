package com.revolt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizGame extends AppCompatActivity {
    List<String> questions = new ArrayList<>();
    List<String> correctAnswers = new ArrayList<>();
    List<List<String>> confusionChoices = new ArrayList<>();





    DatabaseReference database,keyTake,dbGetKeyToUser;
    Button next;
    LinearLayout LayoutL;

    ImageView imageShow,imageShowWrong;

    private RadioGroup answerRadioGroup;
    TextView txt1,ShowCorrectAns, text;
    RadioButton c1,c2,c3,c4;
    private int currentQuestionIndex = 0;

    int questionCounter;


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
        String data = intent.getStringExtra("UserId");

        assert TextSub != null;
        assert TextTopicNum != null;
        assert TextDiff != null;
        assert Mode != null;
//

        assert numOfItem != null;
        questionCounter = Integer.parseInt(numOfItem);

        database = FirebaseDatabase.getInstance().getReference("AppData").child(TextSub).child(TextTopicNum).child(TextDiff);
        assert data != null;
        keyTake = FirebaseDatabase.getInstance().getReference("users").child(data);

        txt1 = findViewById(R.id.questions);
        c1 = findViewById(R.id.answerOption1);
        c2 = findViewById(R.id.answerOption2);
        c3 = findViewById(R.id.answerOption3);
        c4 = findViewById(R.id.answerOption4);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        LayoutL = findViewById(R.id.LayoutImageAppear);
        imageShow = findViewById(R.id.imgToShow);
        imageShowWrong = findViewById(R.id.imgToShowWrong);
        ShowCorrectAns = findViewById(R.id.ShowCorrectAns);
//        text = findViewById(R.id.textDebug);
//        text.setText(TextTopicNum + " " + TextSub + " " + TextDiff + " " + Mode);


        // Create a random number generator
//        Random random = new Random();
//
//        // Generate and print five random numbers without repetition
//        for (int i = 0; i < 5; i++) {
//            int index = random.nextInt(numbers.size());
//            int randomNumber = numbers.remove(index);
////                        System.out.println("Random Number " + (i + 1) + ": " + randomNumber);
//            Toast.makeText(QuizGame.this, "Random Number " + (i + 1) + ": " + randomNumber, Toast.LENGTH_SHORT).show();
//
//        }
//
//        QuestionIndex = numbers.get(currentQuestionIndex);






        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

//                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                        String childKey = childSnapshot.getKey();
//
//                            if (childSnapshot.hasChild( "question" )) {
//                                DataSnapshot ques = childSnapshot.child( "question" );
//                                String quesAdd = ques.child( "questionText" ).getValue(String.class);
//                                questions.add(quesAdd);
//                            }
//
//                            if (childSnapshot.hasChild( "correct" )) {
//                                DataSnapshot choicesCorrect = childSnapshot.child( "correct" );
//                                String choice1 = choicesCorrect.child( "choice1" ).getValue(String.class);
//                                correctAnswers.add(choice1);
//                            }
//
//                            if (childSnapshot.hasChild( "incorrect" )) {
//                                DataSnapshot choicesIncorrect = childSnapshot.child( "incorrect" );
//                                String choice2 = choicesIncorrect.child( "choice2" ).child("choiceText").getValue(String.class);
//                                String choice3 = choicesIncorrect.child( "choice3" ).child("choiceText").getValue(String.class);
//                                String choice4 = choicesIncorrect.child( "choice4" ).child("choiceText").getValue(String.class);
//
//
//                                List<String> confusion = new ArrayList<>();
//
//                                confusion.add(choice2);
//                                confusion.add(choice3);
//                                confusion.add(choice4);
//
//                                confusionChoices.add(confusion);
//                            }
//
////
////                            questionCounter--;
////
////                            if(questionCounter==0){
////                                break;
////                            }
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

    private void displayQuestion() {
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
            Toast.makeText(QuizGame.this, "Correct! ", Toast.LENGTH_SHORT).show();
            ShowCorrectAns.setText("You are Correct! Sereep Mo AHHH");
            imageShow.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(QuizGame.this, "Incorrect!", Toast.LENGTH_SHORT).show();
            ShowCorrectAns.setText(String.format("The Correct Answer is %s", correctAnswers.get(currentQuestionIndex)));
            imageShowWrong.setVisibility(View.VISIBLE);
        }

        ShowCorrectAns.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);

        next.setOnClickListener(v -> {
            currentQuestionIndex++;
            // Move to the next question
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                Toast.makeText(QuizGame.this, "Quiz completed!", Toast.LENGTH_SHORT).show();
                finish();
            }
            next.setVisibility(View.GONE);
            imageShow.setVisibility(View.GONE);
            imageShowWrong.setVisibility(View.GONE);
            ShowCorrectAns.setVisibility(View.GONE);
            answerRadioGroup.setVisibility(View.VISIBLE);
        });




    }





}