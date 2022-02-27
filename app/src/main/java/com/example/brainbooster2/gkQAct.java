package com.example.brainbooster2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.brainbooster2.databinding.ActivityGkQactBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class gkQAct extends AppCompatActivity {

    ActivityGkQactBinding binding;
    String questions, correctAns, in_correctAns;
    int locationOfCorrectAnswer, score = 0, noOfQues = 0;

    ArrayList<String> answers = new ArrayList<String>();

    JSONObject jsonObject;
    JSONArray result_array;
    JSONObject jsonObj_ResultArr;
    JSONArray inCorrectAns_Arr;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MediaPlayer mediaPlayer,mediaPlayerWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGkQactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.game_background);
        mediaPlayerWin = MediaPlayer.create(getApplicationContext(),R.raw.win_sound);

        binding.resultScoreGkTxtV.setVisibility(View.GONE);
        binding.MkLoader.setVisibility(View.VISIBLE);
        binding.qTxtVGKAct.setVisibility(View.INVISIBLE);
        binding.divider2.setVisibility(View.INVISIBLE);
        binding.linearLayoutGkAct.setVisibility(View.INVISIBLE);
        binding.chipGrpGkAct.setVisibility(View.INVISIBLE);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("https://opentdb.com/api.php?amount=24&category=9&difficulty=medium&type=multiple");

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            binding.MkLoader.setVisibility(View.INVISIBLE);
            binding.qTxtVGKAct.setVisibility(View.VISIBLE);
            binding.divider2.setVisibility(View.VISIBLE);
            binding.linearLayoutGkAct.setVisibility(View.VISIBLE);
            binding.chipGrpGkAct.setVisibility(View.VISIBLE);
            try {
                jsonObject = new JSONObject(s);

                questionsNAns();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }//End OnPost

        public void startTimer() {
            new CountDownTimer(600000, 1000) {
                @Override
                public void onTick(long l) {

                    // Used for formatting digit to be in 2 digits only
                    NumberFormat f = new DecimalFormat("00");
                    long min = (l/60000) % 60;
                    long sec = (l/ 1000) % 60;
                    binding.timeTxtVGKAct.setText(f.format(min) + ":" + f.format(sec));
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }

                @Override
                public void onFinish() {
                    mediaPlayer.stop();
                    mediaPlayerWin.start();
                    sendGkDataFireStore();
                    disableAll();
                    Toast.makeText(gkQAct.this, "Time Up", Toast.LENGTH_SHORT).show();
                    binding.universalGkScoreTxtV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(),ResultAct.class);
                            i.putExtra("GKScore",score);
                            startActivity(i);
                        }
                    });
                }
            }.start();
        }

        private void disableAll(){
            binding.timeTxtVGKAct.setVisibility(View.GONE);
            binding.qTxtVGKAct.setVisibility(View.GONE);
            binding.scoreTxtVGkAct.setVisibility(View.GONE);
            binding.divider2.setVisibility(View.GONE);
            binding.chipGrpGkAct.setVisibility(View.GONE);

            binding.userScoreGkTv.setVisibility(View.VISIBLE);
            binding.userScoreGkTv.setText("Score : " + score);
            binding.imageViewGk.setVisibility(View.VISIBLE);
            binding.universalGkScoreTxtV.setVisibility(View.VISIBLE);
        }

        public DownloadTask() {
            startTimer();
        }

    }//End Task Class
    public void questionsNAns(){
        Random r = new Random();
        int numb = r.nextInt(9);
        try {
            /** Get Json Array containing objects of results-Array **/
            result_array = jsonObject.getJSONArray("results");

            /** Get JSON Object from RESULT JSON ARRAY which "NUMB" will select  **/
            jsonObj_ResultArr = result_array.getJSONObject(numb);

            questions = jsonObj_ResultArr.getString("question");
            correctAns = jsonObj_ResultArr.getString("correct_answer");
            answers.add(correctAns);

            binding.qTxtVGKAct.setText(questions);


            //binding.chip1.setText(correctAns);
            /**Get ARRAY of IN-Correct Ans from JSON Object inside JSON RESULT ARRAY **/
            inCorrectAns_Arr = jsonObj_ResultArr.getJSONArray("incorrect_answers");

            /** Adding elements into Answer array **/
            for (int k = 0; k < inCorrectAns_Arr.length(); k++) {
                answers.add(String.valueOf(inCorrectAns_Arr.get(k)));
            }
            Collections.shuffle(answers);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        binding.chip0.setText(answers.get(0));
        binding.chip1.setText(answers.get(1));
        binding.chip2.setText(answers.get(2));
        binding.chip3.setText(answers.get(3));
        compareAnswers();
    }
    public void compareAnswers(){
        for(int i=0;i<answers.size();i++){
            if(answers.get(i).toString().equals(correctAns)){
                locationOfCorrectAnswer = i;
                //Log.i("location of correct ans ^^^^^^^^^ ", String.valueOf(locationOfCorrectAnswer));
                break;
            }
        }
    }
    public void choseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            Toast.makeText(getApplicationContext(), "correct 👌", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(getApplicationContext(), "wrong 😣", Toast.LENGTH_SHORT).show();
        }
        noOfQues++;
        binding.scoreTxtVGkAct.setText(Integer.toString(score) + "/" + Integer.toString(noOfQues));
        questionsNAns();

    }
    private void sendGkDataFireStore(){
        Map<String,Object> items = new HashMap<>();
        items.put("userGkScore", String.valueOf(score));

        firebaseFirestore.collection("users")
                .document(auth.getCurrentUser().getUid()).update(items)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(gkQAct.this, "Score Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(gkQAct.this, "Could not connect database", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}