package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

// Code referred from https://www.viralandroid.com/2016/03/display-images-in-android-gridview.html
public class StickerGridActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    StickerExchangeDetails stickerExchangeDetails;
    String userID;
    long totalStickerExchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_grid);

        mDatabase = FirebaseDatabase.getInstance().getReference("stickerExchangeDetails");

        stickerExchangeDetails = new StickerExchangeDetails();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userID = sharedPreferences.getString("username","");


        String[] imageIdArray = {
                "angel", "angry", "angry_man",
               "bored", "happy"
        };

        GridView stickerGridView = (GridView) findViewById(R.id.sticker_grid_view);
        StickerGridViewAdapter adapter = new StickerGridViewAdapter(this, imageIdArray);
        stickerGridView.setAdapter(adapter);

//        createNotificationChannel();
//
//        Intent intent = new Intent(this, ReceivedHistoryActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);



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

                        String receiverID = userNameEditText.getText().toString();

                        //Insert to Firebase.
                        addDataToFirebase(imageIdArray[position], receiverID);

                        Snackbar.make(parentLayout, "Sticker Sent", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();
//                        String uri = "@drawable/"+ imageIdArray[position];
//                        int sticker = getApplicationContext().getResources().getIdentifier(uri, null, getApplicationContext().getPackageName());
//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(StickerGridActivity.this, "receiver")
//                                .setSmallIcon(R.drawable.happy)
//                                .setContentTitle(receiverID)
//                                .setContentText("Someone sent you a new sticker.")
//                                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),sticker))
//                                .setStyle(new NotificationCompat.BigPictureStyle()
//                                .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),sticker)
//                                ).bigLargeIcon(null))
//                                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                // Set the intent that will fire when the user taps the notification
//                                .setContentIntent(pendingIntent)
//                                .setAutoCancel(true);
//
//                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(StickerGridActivity.this);
//
//                            notificationManager.notify(1, builder.build());

                    }
                });

                alertDialog.show();

            }
        });


    }

    private void addDataToFirebase(String stickerID, String receiver) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        stickerExchangeDetails.setSenderId(userID);
        stickerExchangeDetails.setReceiverId(receiver);
        stickerExchangeDetails.setStickerId(stickerID);
        stickerExchangeDetails.setDateSent(formatter.format(date));

        mDatabase.child("allExchanges").push().setValue(stickerExchangeDetails);
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalStickerExchange = snapshot.getChildrenCount() + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StickerGridActivity.this, "Failed to send Sticker " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Receive Notification";
            String description = "Receive Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("receiver", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}