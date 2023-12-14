package com.example.geekschatsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.geekschatsystem.activities.FrontPage;

import java.util.Timer;
import java.util.TimerTask;


// appCompatActivity is a base class for activities in Android that use the AppCompat library,
public class MainActivity extends AppCompatActivity {


    // used to schedule a task to be executed after a specified delay.
    Timer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


     //TimerTask is created and scheduled to run after a delay of 2000 milliseconds (2 seconds)
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                //The run method of the TimerTask is overridden to create an Intent that navigates from the current activity (MainActivity) to the FrontPage activity.
                Intent intent = new Intent(MainActivity.this, FrontPage.class);

                startActivity(intent);  //this method is then called to initiate the activity transition.

            }
        },2000);
    }

}

/*
 this is  an introductory screen that waits for 2 seconds  and then automatically navigates to the FrontPage activity.
  introductory screen are often used to provide a visual introduction to an app
 */