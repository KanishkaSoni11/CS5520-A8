package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class StickerGridActivity extends AppCompatActivity {

    private final Integer[] imageIdArray = {
            R.drawable.angel, R.drawable.angry, R.drawable.angry,
            R.drawable.bored, R.drawable.happy
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_grid);

        GridView stickerGridView = (GridView) findViewById(R.id.sticker_grid_view);
        StickerGridViewAdapter adapter = new StickerGridViewAdapter(this, imageIdArray);
        stickerGridView.setAdapter(adapter);

        stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}