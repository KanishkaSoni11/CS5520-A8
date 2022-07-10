package com.cs5520.cs5520_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReceivedHistoryActivity extends AppCompatActivity {

    private RecyclerView receiveHistoryRecyclerView;
    private List<ReceivedHistoryCollector> receivedHistoryCollectors;
    private ReceivedHistoryAdapter receivedHIstoryAdapter;
    private DatabaseReference mDatabase;
    private StickerExchangeDetails stickerExchangeDetails;
    String userID;
    private String sender;
    private int sticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_history);

        createNotificationChannel();

        Intent intent = new Intent(this, ReceivedHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        mDatabase = FirebaseDatabase.getInstance().getReference("stickerExchangeDetails");
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userID = sharedPreferences.getString("username", "");

//        mDatabase.child("allExchanges").orderByChild("dateSent").limitToLast(10).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    System.out.println("holaaaaaaaaaaaaaaaaaaaaaaa");
//                    stickerExchangeDetails = dataSnapshot.getValue(StickerExchangeDetails.class);
//                    if (stickerExchangeDetails.receiverId.equals(userID)) {
//                        sender = stickerExchangeDetails.getSenderId();
//                        System.out.println("----------"+sender);
//                        String uri = "@drawable/"+ stickerExchangeDetails.getStickerId();
//                        sticker = getApplicationContext().getResources().getIdentifier(uri, null, getApplicationContext().getPackageName());
//                        break;
//                    }
//                }
//                receivedHistoryCollectors.clear();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "receiver")
                .setSmallIcon(R.drawable.happy)
                .setContentTitle("Sender")
                .setContentText("Someone sent you a new sticker.")
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.angry_man))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.angry_man)
                                ).bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);



        receiveHistoryRecyclerView = findViewById(R.id.recyclerView_receive_history);
        receiveHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        receiveHistoryRecyclerView.setHasFixedSize(true);

        receivedHistoryCollectors = new ArrayList<>();
        receivedHIstoryAdapter = new ReceivedHistoryAdapter(receivedHistoryCollectors, this);
        receiveHistoryRecyclerView.setAdapter(receivedHIstoryAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(receiveHistoryRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        receiveHistoryRecyclerView.addItemDecoration(dividerItemDecoration);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        mDatabase.child("allExchanges").orderByChild("dateSent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                receivedHistoryCollectors.clear();
                notificationManager.notify((int) snapshot.getChildrenCount() + 1, builder.build());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    stickerExchangeDetails = dataSnapshot.getValue(StickerExchangeDetails.class);
                    if (stickerExchangeDetails.receiverId.equals(userID)) {
                        System.out.println("list" + stickerExchangeDetails.toString());
                        ReceivedHistoryCollector receivedHistoryCollector = new ReceivedHistoryCollector(stickerExchangeDetails.getSenderId(), stickerExchangeDetails.getDateSent(), stickerExchangeDetails.getStickerId());
                        if (!receivedHistoryCollectors.contains(receivedHistoryCollector)) {
                            receivedHistoryCollectors.add(receivedHistoryCollector);
                        }

                    }

                }


                receivedHIstoryAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void createNotificationChannel() {
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