package com.cs5520.cs5520_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ReceivedHistoryActivity extends AppCompatActivity {

    private RecyclerView receiveHistoryRecyclerView;
    private List<ReceivedHistoryCollector> receivedHistoryCollectors;
    private ReceivedHistoryAdapter receivedHIstoryAdapter;
    private DatabaseReference mDatabase;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_history);

        mDatabase = FirebaseDatabase.getInstance().getReference("stickerExchangeDetails");
        receiveHistoryRecyclerView = findViewById(R.id.recyclerView_receive_history);
        receiveHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        receiveHistoryRecyclerView.setHasFixedSize(true);

        receivedHistoryCollectors = new ArrayList<>();
        receivedHIstoryAdapter = new ReceivedHistoryAdapter(receivedHistoryCollectors, this);
        receiveHistoryRecyclerView.setAdapter(receivedHIstoryAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userID = sharedPreferences.getString("username", "");

        System.out.println("username " + userID);
        mDatabase.child("allExchanges").orderByChild("dateSent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Hereeee");
                System.out.println("Snap " + snapshot.toString());
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    StickerExchangeDetails stickerExchangeDetails = dataSnapshot.getValue(StickerExchangeDetails.class);

                    if (stickerExchangeDetails.receiverId.equals(userID)) {
                        System.out.println("list" + stickerExchangeDetails.toString());
                        ReceivedHistoryCollector receivedHistoryCollector = new ReceivedHistoryCollector(stickerExchangeDetails.getSenderId(), stickerExchangeDetails.getDateSent(), stickerExchangeDetails.getStickerId());
                        receivedHistoryCollectors.add(receivedHistoryCollector);
                    }

                }

                receivedHIstoryAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}