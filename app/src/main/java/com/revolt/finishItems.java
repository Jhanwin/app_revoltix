package com.revolt;

public class finishItems {


    public String correctAnswers, question, remarks, selectedAnswer;

    public finishItems(String question,String correctAnswers, String selectedAnswer, String remarks){

        this.correctAnswers = correctAnswers;
        this.question = question;
        this.remarks = remarks;
        this.selectedAnswer = selectedAnswer;

    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }
}
