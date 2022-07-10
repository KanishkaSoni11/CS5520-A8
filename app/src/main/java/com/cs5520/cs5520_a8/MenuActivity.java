package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button stickersButton = (Button) findViewById(R.id.stickers_button);
        Button history = (Button) findViewById(R.id.history_button);

        stickersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, StickerGridActivity.class);
                startActivity(intent);
            }
        });
    }
}