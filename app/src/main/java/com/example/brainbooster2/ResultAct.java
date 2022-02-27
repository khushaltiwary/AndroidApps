package com.example.brainbooster2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultAct extends AppCompatActivity {
    FirebaseFirestore dbroot =FirebaseFirestore.getInstance();
    Integer score;
    TabLayout resultActTabLayout;
    ViewPager vPager;
    TabItem mathsTabItem,gkTabItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        resultActTabLayout = findViewById(R.id.resultActTabLayout);
        vPager = findViewById(R.id.vPager);
        mathsTabItem = findViewById(R.id.mathsTabItem);
        gkTabItem = findViewById(R.id.gkTabItem);

        resultActTabLayout.setupWithViewPager(vPager);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pageAdapter.addFrag(new FragmentMaths(),"Maths");
        pageAdapter.addFrag(new FragmentGk(),"General Knowledge");
        vPager.setAdapter(pageAdapter);
    }
}