package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;


public class Result extends AppCompatActivity {
    ImageView scoreImageView;
    TextView textViewQuotes;TextView scoreResultTextView;
    ArrayList<String> Quotes = new ArrayList<String>();
    int scoreR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        textViewQuotes = findViewById(R.id.textViewQuotes);

        scoreR = getIntent().getExtras().getInt("UserScore");

        Quotes.add("“You always pass failure on the way to success.”");
        Quotes.add("“Make each day your masterpiece.”");
        Quotes.add("“Reach high, for stars lie hidden in your soul. Dream deep, for every dream precedes the goal.”");
        Quotes.add("If you believe than you can do it.");
        Quotes.add("It's lonely at the top that's why Ferrari has two seat but bus has 60");
        Quotes.add("When you grow intelligent,your company becomes small cause lion don't hunt with packs");
        Quotes.add("Chose pen over sword");
        Quotes.add("One who forgives is the greatest warrior");
        Quotes.add(("Accept each other with flaws, everyone is born different"));
        Quotes.add("Sometimes is 6 and 9, try looking another way down");
        Quotes.add("Believe it or not parents are only people available always for you");
        Quotes.add("Try doing small surprises for your parents, miracles will happen to you");
        Quotes.add("See you got a Wonder women together with a Superman with you,Mom & Dad");
        Quotes.add("Believe me every missed friend's party,every late night hustling would pay grand one day");

        scoreResultTextView = findViewById(R.id.scoreResultTextView);
        scoreImageView = findViewById(R.id.imageView);


        scoreCheck();

    }//OnCreate end

    public void selectRandomQuotes() {
        Random random = new Random();
        textViewQuotes.setText(Quotes.get(random.nextInt(Quotes.size())));
    }

    public void scoreCheck() {
        if(scoreR<=10){
            sound();
            scoreResultTextView.setText("Your Score :"+scoreR);
            scoreImageView.setBackgroundResource(R.drawable.work_hard);
            selectRandomQuotes();
            Toast.makeText(getApplicationContext(),"Work Hard Buddy",Toast.LENGTH_LONG).show();
            scoreImageView.clearAnimation();
        }else if(scoreR>10 && scoreR<=20){
            sound();
            scoreResultTextView.setText("Your Score :"+scoreR);
            scoreImageView.setBackgroundResource(R.drawable.badge_gold);
            selectRandomQuotes();
            Toast.makeText(getApplicationContext(),"Well Done Buddy",Toast.LENGTH_LONG).show();
            scoreImageView.clearAnimation();
        }else{
            sound();
            scoreResultTextView.setText("Your Score :"+scoreR);
            selectRandomQuotes();
            scoreImageView.setBackgroundResource(R.drawable.win);
            Toast.makeText(getApplicationContext(),"Great Work Buddy",Toast.LENGTH_LONG).show();
            scoreImageView.clearAnimation();
        }
    }

    public void sound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.ta_da);
        mediaPlayer.start();
    }
}//Main end