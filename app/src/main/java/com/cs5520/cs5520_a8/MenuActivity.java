package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button stickersButton = (Button) findViewById(R.id.stickers_button);
        Button history = (Button) findViewById(R.id.history_button);
        Button receiveHistoryButton = findViewById(R.id.receive_history_button);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String value = sharedPreferences.getString("username","");

        TextView welcome = findViewById(R.id.welcome);

        welcome.setText("Welcome " + value + "!");

        stickersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, StickerGridActivity.class);
                startActivity(intent);
            }
        });

        receiveHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ReceivedHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}