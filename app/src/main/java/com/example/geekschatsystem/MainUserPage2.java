package com.example.geekschatsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.geekschatsystem.databinding.ActivityMainUserPage2Binding;

public class MainUserPage2 extends AppCompatActivity {
    private MeowBottomNavigation meowBottomNavigation;
    private ActivityMainUserPage2Binding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_page2);
        meowBottomNavigation = findViewById(R.id.mewoView);

        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_settings_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_chat_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_search_24));

    }
}