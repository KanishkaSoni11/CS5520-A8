package com.cs5520.cs5520_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_history);

        mDatabase = FirebaseDatabase.getInstance().getReference("stickerExchageDetails");
        receiveHistoryRecyclerView = findViewById(R.id.recyclerView_receive_history);
        receiveHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        receiveHistoryRecyclerView.setHasFixedSize(true);

        receivedHistoryCollectors = new ArrayList<>();
        receiveHistoryRecyclerView.setAdapter(receivedHIstoryAdapter);
        mDatabase.child("").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StickerExchangeDetails stickerExchangeDetails = snapshot.getValue(StickerExchangeDetails.class);
                ReceivedHistoryCollector receivedHistoryCollector = new ReceivedHistoryCollector(stickerExchangeDetails.getSenderId(), stickerExchangeDetails.getDateSent(),Integer.parseInt(stickerExchangeDetails.getStickerId()));
                receivedHistoryCollectors.add(receivedHistoryCollector);
                receivedHIstoryAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}