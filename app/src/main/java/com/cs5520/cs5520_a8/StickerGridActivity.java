package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

// Code referred from https://www.viralandroid.com/2016/03/display-images-in-android-gridview.html
public class StickerGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_grid);

        Integer[] imageIdArray = {
                R.drawable.angel, R.drawable.angry, R.drawable.angry_man,
                R.drawable.bored, R.drawable.happy
        };

        GridView stickerGridView = (GridView) findViewById(R.id.sticker_grid_view);
        StickerGridViewAdapter adapter = new StickerGridViewAdapter(this, imageIdArray);
        stickerGridView.setAdapter(adapter);

        stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}