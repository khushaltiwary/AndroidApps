package com.example.brainbooster2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.brainbooster2.databinding.ActivityGsignBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GsignAct extends AppCompatActivity {

    ActivityGsignBinding binding;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String str,password,UId;
    FirebaseFirestore dbroot = FirebaseFirestore.getInstance();
    CollectionReference ref = dbroot.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGsignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        hideSoftInput();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getApplicationContext(),SelectionAct.class));
        } else {
            loginBtn();
        }
    }

    private void checkNullNSignIn(){
        if(binding.editTextPersonNameGsign.getText() != null && binding.editTextPasswordGsign.getText() != null){
            str = binding.editTextPersonNameGsign.getText().toString().trim();
            password = binding.editTextPasswordGsign.getText().toString().trim();
            if(!str.matches("") && !password.matches(""))
            {
                mAuth.signInWithEmailAndPassword(str,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    createCollectionNSetUsername();
                                    Toast.makeText(GsignAct.this, "Sign-in successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(),SelectionAct.class);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(GsignAct.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else{
                Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_baseline_error_24);
                customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(),
                        customErrorDrawable.getIntrinsicHeight());
                binding.editTextPasswordGsign.setError("Please enter data",customErrorDrawable);
                binding.editTextPersonNameGsign.setError("Please enter data",customErrorDrawable);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Fields cannot be empty",Toast.LENGTH_SHORT).show();
        }
    }

    private void loginBtn(){
        binding.loginBtnGsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNullNSignIn();
            }
        });
    }
    private void createCollectionNSetUsername(){
        Map<String,Object> items = new HashMap<>();
        items.put("userName",mAuth.getCurrentUser().getEmail());
        UId = mAuth.getCurrentUser().getUid();
        ref.document(UId).set(items).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(GsignAct.this, "Done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GsignAct.this, "Problem Found", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void hideSoftInput(){
        binding.editTextPersonNameGsign.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) || (keyEvent.getAction() == KeyEvent.ACTION_DOWN)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(binding.editTextPersonNameGsign.getWindowToken(),0);
                }
                return false;
            }
        });

        binding.editTextPasswordGsign.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) || (keyEvent.getAction() == KeyEvent.ACTION_DOWN)){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(binding.editTextPasswordGsign.getWindowToken(),0);
                }
                return false;
            }
        });
    }

}