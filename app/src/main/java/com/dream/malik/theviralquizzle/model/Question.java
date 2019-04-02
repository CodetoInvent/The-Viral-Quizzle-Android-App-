package com.dream.malik.theviralquizzle.model;

/**
 * Created by malik on 2/19/2018.
 */

public class Question {
    //this is used as argument of a ArrayList in Common class this all are as it is mention in Json Question Array
    private  String Question,AnswerA,AnswerB,AnswerC,AnswerD,CorrectAnswer,IsImageQuestion;
    public Question(){

    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, String categoryId, String isImageQuestion) {
        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        CorrectAnswer = correctAnswer;

        this.IsImageQuestion = isImageQuestion;
    }

    public String getQuestion() {
        return Question;
    }



    public String getAnswerA() {
        return AnswerA;
    }



    public String getAnswerB() {
        return AnswerB;
    }



    public String getAnswerC() {
        return AnswerC;
    }



    public String getAnswerD() {
        return AnswerD;
    }



    public String getCorrectAnswer() {
        return CorrectAnswer;
    }



    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

}
