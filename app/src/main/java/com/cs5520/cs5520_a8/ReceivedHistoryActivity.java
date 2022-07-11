package com.cs5520.cs5520_a8;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReceivedHistoryActivity extends AppCompatActivity {

    String userID;
    private RecyclerView receiveHistoryRecyclerView;
    private List<ReceivedHistoryCollector> receivedHistoryCollectors;
    private ReceivedHistoryAdapter receivedHIstoryAdapter;
    private DatabaseReference mDatabase;

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

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(receiveHistoryRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        receiveHistoryRecyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userID = sharedPreferences.getString("username", "");


        mDatabase.child("allExchanges").orderByChild("dateSent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                receivedHistoryCollectors.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StickerExchangeDetails stickerExchangeDetails = dataSnapshot.getValue(StickerExchangeDetails.class);
                    if (stickerExchangeDetails.receiverId.equals(userID)) {
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("stickerExchangeDetails").child("allExchanges").child(dataSnapshot.getKey()).child("viewed");
                        db.setValue(true);

                        ReceivedHistoryCollector receivedHistoryCollector = new ReceivedHistoryCollector(stickerExchangeDetails.getSenderId(), stickerExchangeDetails.getDateSent(), stickerExchangeDetails.getStickerId());

                        stickerExchangeDetails.setViewed(true);
                        if (!receivedHistoryCollectors.contains(receivedHistoryCollector)) {
                            receivedHistoryCollectors.add(receivedHistoryCollector);

                        }

                    }

                }

                Collections.reverse(receivedHistoryCollectors);
                receivedHIstoryAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}