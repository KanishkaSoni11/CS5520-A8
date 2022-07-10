package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

// Code referred from https://www.viralandroid.com/2016/03/display-images-in-android-gridview.html
public class StickerGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_grid);

        String[] imageIdArray = {
                "angel", "angry", "angry_man",
               "bored", "happy"
        };

        GridView stickerGridView = (GridView) findViewById(R.id.sticker_grid_view);
        StickerGridViewAdapter adapter = new StickerGridViewAdapter(this, imageIdArray);
        stickerGridView.setAdapter(adapter);

        View parentLayout = findViewById(android.R.id.content);
        stickerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
//                Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected",
//                        Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(StickerGridActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.sticker_send_dialog_layout, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();

                Button submitButton = dialogView.findViewById(R.id.dialog_submit_button);
                EditText userNameEditText = dialogView.findViewById(R.id.username_edittext);
                TextView textView = dialogView.findViewById(R.id.sticker_id);
                textView.setText(imageIdArray[position]);

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Snackbar.make(parentLayout, "Sticker Sent", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
                    }
                });
                alertDialog.show();

            }
        });
    }
}