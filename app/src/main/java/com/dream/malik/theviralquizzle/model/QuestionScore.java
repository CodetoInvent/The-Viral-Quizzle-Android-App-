package com.dream.malik.theviralquizzle.model;

/**
 * Created by malik on 2/19/2018.
 */

public class QuestionScore {
    private String Question_score;
    private  String Users;
    private String Score;
    private String categoryId;
    private String categoryName;

    public QuestionScore() {

    }

    public QuestionScore(String question_score, String users, String score, String categoryId, String categoryName) {
        Question_score = question_score;
        Users = users;
        Score = score;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getQuestion_score() {
        return Question_score;
    }

    public void setQuestion_score(String question_score) {
        Question_score = question_score;
    }

    public String getUsers() {
        return Users;
    }

    public void setUsers(String users) {
        Users = users;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
