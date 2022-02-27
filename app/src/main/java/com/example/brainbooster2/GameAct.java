package com.example.brainbooster2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameAct extends AppCompatActivity {

    TextView qtnTxtVGameAct, timeTxtVGameAct, scoreTxtVGameAct,gameOverTxtV,txtV2,universalScoreResultTxtV;
    ImageView gameOverTimeImgV;
    ChipGroup chipGrpGameAct;
    Chip chip0,chip1,chip2,chip3;
    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<Float> answersForDiv = new ArrayList<>();
    List<Object> func;
    int locationOfCorrectAnswer,score = 0,noOfQues = 0;
    String tagFrmIntent,tagType2Func = "add";
    int tagIntent;
    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MediaPlayer mediaPlayer,mediaPlayerWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.game_background);
        mediaPlayerWin = MediaPlayer.create(getApplicationContext(),R.raw.win_sound);

        Intent intent = getIntent();
        tagFrmIntent = intent.getStringExtra("Tag");
        tagIntent = Integer.parseInt(tagFrmIntent);

        universalScoreResultTxtV = findViewById(R.id.universalScoreResultTxtV);
        gameOverTimeImgV = findViewById(R.id.gameOverTimeImgV);
        gameOverTxtV = findViewById(R.id.gameOverTextV);
        qtnTxtVGameAct = findViewById(R.id.qTxtVGKAct);
        timeTxtVGameAct = findViewById(R.id.timeTxtVGKAct);
        scoreTxtVGameAct = findViewById(R.id.scoreTxtVGkAct);
        chipGrpGameAct = findViewById(R.id.chipGrpGkAct);
        txtV2 = findViewById(R.id.txtV2);
        chip0 = findViewById(R.id.chip0);
        chip1 = findViewById(R.id.chip1);
        chip2 = findViewById(R.id.chip2);
        chip3 = findViewById(R.id.chip3);

        startTimer();
        typeOfMathsProblem(101);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
    private void typeOfMathsProblem(int d){
        switch (tagIntent){
            case 2 :
                subs(d);
                tagType2Func = "subs";
                break;
            case 3 :
                multi(d);
                tagType2Func = "multi";
                break;
            case 4 :
                div(d);
                tagType2Func = "div";
                break;
            default:
                add(d);

        }
    }
    private void div(int d){
        Random r = new Random();
        float a = r.nextInt(d);
        float b = r.nextInt(d);
        while(b==0){
            b = r.nextInt(d);
        }
        qtnTxtVGameAct.setText(a+" ÷ "+b);
        locationOfCorrectAnswer = r.nextInt(4);

        answersForDiv.clear();
        for(int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answersForDiv.add(a/b); //a & b ---> producing range for random no

            }else{
                int wrongAns = r.nextInt(d);
                while (wrongAns == a/b){
                    wrongAns = r.nextInt(d);
                }
                answersForDiv.add(Float.valueOf(wrongAns));
            }
        }
        chip0.setText(Float.toString(answersForDiv.get(0)));
        chip1.setText(Float.toString(answersForDiv.get(1)));
        chip2.setText(Float.toString(answersForDiv.get(2)));
        chip3.setText(Float.toString(answersForDiv.get(3)));

    }
    private void multi(int d){
        Random r = new Random();
        int a = r.nextInt(d);
        int b = r.nextInt(d);

        qtnTxtVGameAct.setText(Integer.toString(a)+" * "+Integer.toString(b));
        locationOfCorrectAnswer = r.nextInt(4);

        answers.clear();
        for(int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a*b); //a & b ---> producing range for random no

            }else{
                int wrongAns = r.nextInt(d);
                while (wrongAns == a+b){
                    wrongAns = r.nextInt(d);
                }
                answers.add(wrongAns);
            }
        }
        chip0.setText(Integer.toString(answers.get(0)));
        chip1.setText(Integer.toString(answers.get(1)));
        chip2.setText(Integer.toString(answers.get(2)));
        chip3.setText(Integer.toString(answers.get(3)));

    }
    private void subs(int d){
        Random random = new Random();
        int a = random.nextInt(d);
        int b = random.nextInt(d);
        while(b>a){
            b = random.nextInt(d);
        }
        qtnTxtVGameAct.setText(Integer.valueOf(a)+ " - " + Integer.valueOf(b));
        locationOfCorrectAnswer = random.nextInt(4);

        answers.clear();
        for(int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a-b); //a & b ---> producing range for random no

            }else{
                int wrongAns = random.nextInt(d);
                while (wrongAns == a-b){
                    wrongAns = random.nextInt(d);
                }
                answers.add(wrongAns);
            }
        }
        chip0.setText(Integer.toString(answers.get(0)));
        chip1.setText(Integer.toString(answers.get(1)));
        chip2.setText(Integer.toString(answers.get(2)));
        chip3.setText(Integer.toString(answers.get(3)));
    }
    private void add(int d){
        Random random = new Random();
        int a = random.nextInt(d);

        int b = random.nextInt(d);

        qtnTxtVGameAct.setText(Integer.toString(a)+" + "+Integer.toString(b));
        locationOfCorrectAnswer = random.nextInt(4);

        answers.clear();
        for(int i=0; i<4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b); //a & b ---> producing range for random no

            }else{
                int wrongAns = random.nextInt(d);
                while (wrongAns == a+b){
                    wrongAns = random.nextInt(d);
                }
                answers.add(wrongAns);
            }
        }
        chip0.setText(Integer.toString(answers.get(0)));
        chip1.setText(Integer.toString(answers.get(1)));
        chip2.setText(Integer.toString(answers.get(2)));
        chip3.setText(Integer.toString(answers.get(3)));
    }
    public void choseAnswer(View view){
        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            Toast.makeText(getApplicationContext(),"Correct 👌",Toast.LENGTH_SHORT).show();
            score++;
        }else{
            Toast.makeText(getApplicationContext(),"Wrong 😣",Toast.LENGTH_SHORT).show();
        }
        noOfQues++;
        scoreTxtVGameAct.setText(Integer.toString(score)+"/"+Integer.toString(noOfQues));
        /** TYPE OF OPERATION FUNC CALL KRO **/
        switch (tagType2Func){
            case "subs":
                subs(101);break;
            case "multi":
                multi(101);break;
            case "div" :
                div(101);break;
            default:
                add(101);

        }
    }
    private void startTimer ()  {
        new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                NumberFormat f = new DecimalFormat("00");
                long min = (l/60000) % 60;
                long sec = (l/ 1000) % 60;
                timeTxtVGameAct.setText(f.format(min) + ":" + f.format(sec));
                mediaPlayer.start();
                mediaPlayer.setLooping(true);

            }
            @Override
            public void onFinish() {
                mediaPlayer.stop();
                mediaPlayerWin.start();
                sendDataFireStore();
                disableAll();
                universalScoreResultTxtV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i  =  new Intent(getApplicationContext(),ResultAct.class);
                        i.putExtra("Maths Score : ",score);
                        startActivity(i);
                        Note note = new Note();
                        note.setMathsScore(score);
                        //Toast.makeText(GameAct.this,"Score : " +note.getMathsScore(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
    }
    private void disableAll(){
        timeTxtVGameAct.setVisibility(View.GONE);
        qtnTxtVGameAct.setVisibility(View.GONE);
        scoreTxtVGameAct.setVisibility(View.GONE);
        chipGrpGameAct.setVisibility(View.GONE);
        gameOverTimeImgV.setVisibility(View.VISIBLE);
        gameOverTxtV.setVisibility(View.VISIBLE);
        gameOverTxtV.setText("Time Up 🤓");
        txtV2.setText("Your Score : "+String.valueOf(score));
        txtV2.setVisibility(View.VISIBLE);
        universalScoreResultTxtV.setVisibility(View.VISIBLE);
    }
    private void sendDataFireStore(){
        Map<String,Object> items = new HashMap<>();
        items.put("userMathsScore", String.valueOf(score));
        dbroot.collection("users")
                .document(auth.getCurrentUser().getUid())
                .update(items)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(GameAct.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GameAct.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}