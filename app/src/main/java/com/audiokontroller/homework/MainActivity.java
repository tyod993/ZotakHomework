package com.audiokontroller.homework;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.audiokontroller.homework.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), "main_fragment")
                    .commitNow();
        }
    }
}
