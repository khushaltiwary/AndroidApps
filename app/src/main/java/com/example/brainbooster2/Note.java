package com.example.brainbooster2;

public class Note {
    private int GkScore;
    private int MathsScore;
    String username;

    public String getUsername() {
        return username;
    }

    public String setUsername(String username) {
        return username;
    }

    public int getGkScore() {
        return GkScore;
    }

    public int setGkScore(int gkScore) {
        GkScore = gkScore;
        return gkScore;
    }

    public int getMathsScore() {
        return MathsScore;
    }

    public int setMathsScore(int mathsScore) {
        MathsScore = mathsScore;
        return mathsScore;
    }

    public Note() {
        //default no-args constructor needed
    }

    public Note(int MathsScore, int GkScore, String username) {
        this.MathsScore = MathsScore;
        this.GkScore = GkScore;
        this.username = username;
    }


}
