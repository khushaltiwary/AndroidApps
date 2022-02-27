package com.example.brainbooster2;

public class FirestoreUser {
    String userMathsScore,userName;

    public FirestoreUser(){} //default no-args constructor

    public FirestoreUser(String userMathsScore, String userName) {
        this.userMathsScore = userMathsScore;
        this.userName = userName;
    }



    public String getUserMathsScore() {
        return userMathsScore;
    }

    public void setUserMathsScore(String userMathsScore) {
        this.userMathsScore = userMathsScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
