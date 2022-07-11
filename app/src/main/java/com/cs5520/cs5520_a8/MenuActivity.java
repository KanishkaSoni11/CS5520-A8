package com.cs5520.cs5520_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private List<ReceivedHistoryCollector> receivedHistoryCollectors;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        createNotificationChannel();

        Intent intent = new Intent(this, ReceivedHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "receiver")
                .setSmallIcon(R.drawable.happy)
                .setContentText("Someone sent you a new sticker.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Button stickersButton = (Button) findViewById(R.id.stickers_button);
        Button sentHistoryButton = (Button) findViewById(R.id.sent_history_button);
        Button receiveHistoryButton = findViewById(R.id.receive_history_button);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String value = sharedPreferences.getString("username","");

        TextView welcome = findViewById(R.id.welcome);

        welcome.setText("Welcome " + value + "!");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        mDatabase.child("allExchanges").orderByChild("dateSent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                receivedHistoryCollectors.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StickerExchangeDetails stickerExchangeDetails = dataSnapshot.getValue(StickerExchangeDetails.class);
                    if (stickerExchangeDetails.receiverId.equals(value)) {
                        ReceivedHistoryCollector receivedHistoryCollector = new ReceivedHistoryCollector(stickerExchangeDetails.getSenderId(), stickerExchangeDetails.getDateSent(), stickerExchangeDetails.getStickerId());
                        if (!receivedHistoryCollectors.contains(receivedHistoryCollector)) {
                            receivedHistoryCollectors.add(receivedHistoryCollector);
                        }

                    }

                }

                Collections.reverse(receivedHistoryCollectors);
                builder.setContentTitle(receivedHistoryCollectors.get(0).getSenderId());
                String uri = "@drawable/"+ receivedHistoryCollectors.get(0).getSticker();
                int sticker = getApplicationContext().getResources().getIdentifier(uri, null, getApplicationContext().getPackageName());
                builder.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),sticker));
                builder.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),sticker)
                        ).bigLargeIcon(null));
                notificationManager.notify((int) snapshot.getChildrenCount() + 1, builder.build());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        sentHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SentHistoryActivity.class);
                startActivity(intent);
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