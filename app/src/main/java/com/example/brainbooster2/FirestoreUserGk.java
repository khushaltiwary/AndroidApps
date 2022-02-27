package com.example.brainbooster2;

public class FirestoreUserGk {
    String userName,userGkScore;

    public FirestoreUserGk(){} //no-args constructor

    public FirestoreUserGk(String userName, String userGkScore) {
        this.userName = userName;
        this.userGkScore = userGkScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGkScore() {
        return userGkScore;
    }

    public void setUserGkScore(String userGkScore) {
        this.userGkScore = userGkScore;
    }

}
