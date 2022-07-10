package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onChildAdded1(View view) {
        RealTimeDbActivity stickerExchangeDetails = new RealTimeDbActivity();
        stickerExchangeDetails.onChildAdded1(view);
    }
}