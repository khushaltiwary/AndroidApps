package com.example.brainbooster2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.brainbooster2.databinding.ActivitySelectionBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SelectionAct extends AppCompatActivity {
    ActivitySelectionBinding binding;
    FirebaseAuth auth;
    String tag;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        add();multiply();subs();div();
        //getTag();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        binding.userIdTxtVSelectionAct.setText(user.getEmail().toString());

        binding.imgVMaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgVGK.setEnabled(false);
                binding.radioGrpAddNSubsAct.setVisibility(View.VISIBLE);
                binding.radioGrpMultiNDiv.setVisibility(View.VISIBLE);
                binding.imgVPlaySelectionAct.setVisibility(View.VISIBLE);
            }
        });

        binding.imgVPlaySelectionAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tag != null) {
                    Intent intent = new Intent(getApplicationContext(), GameAct.class);
                    intent.putExtra("Tag",tag);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SelectionAct.this, "Please select maths operation", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.imgVGK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.imgVMaths.setEnabled(false);
                binding.imagePlayVGk.setVisibility(View.VISIBLE);
                binding.imagePlayVGk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(),gkQAct.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        });

    }//End OnCreate

    private void add(){
        binding.AddRadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = (String) binding.AddRadioButton1.getTag();
            }
        });
    }

    private void multiply(){
        binding.MultiradioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = (String) binding.MultiradioButton3.getTag();
            }
        });
    }

    private void subs(){
        binding.subsRadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = (String) binding.subsRadioButton2.getTag();
            }
        });
    }
    private void div(){
        binding.DivradioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tag = (String) binding.DivradioButton4.getTag();
            }
        });
    }

}