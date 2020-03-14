package com.sistempakarthtanak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // load database
        DatabaseHelper mDBHelper = new DatabaseHelper(this);
        // update database, jika versi yang di set di databasehelper lebih baru
        try {
            mDBHelper.updateDatabase();
        } catch (IOException e) {
            throw new Error("UnableToUpdateDatabase");
        }

//        delay splash screen selama 2 detik
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
