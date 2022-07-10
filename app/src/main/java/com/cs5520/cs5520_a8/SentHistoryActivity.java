package com.cs5520.cs5520_a8;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentHistoryActivity extends AppCompatActivity {

    List<SentHistory> sentHistoryList;
    RecyclerView sentHistoryRecyclerView;
    private SentHistoryAdapter sentHistoryAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_history);
        System.out.println("Holaaaaa");
        databaseReference = FirebaseDatabase.getInstance().getReference("stickerExchangeDetails");
        sentHistoryRecyclerView = findViewById(R.id.sentRecyclerView);
        sentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sentHistoryRecyclerView.setHasFixedSize(true);

        sentHistoryList = new ArrayList<>();
        sentHistoryAdapter = new SentHistoryAdapter(sentHistoryList, this);
        sentHistoryRecyclerView.setAdapter(sentHistoryAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String value = sharedPreferences.getString("username","");
        databaseReference.child("allExchanges").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Integer> sentMap = new HashMap<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StickerExchangeDetails stickerExchangeDetails = dataSnapshot.getValue(StickerExchangeDetails.class);
                    if(stickerExchangeDetails.getSenderId().equals(value)) {
                        if(sentMap.containsKey(stickerExchangeDetails.getStickerId())) {
                            int oldCount = sentMap.get(stickerExchangeDetails.getStickerId());
                            sentMap.put(stickerExchangeDetails.getStickerId(),oldCount+1);
                        }
                        sentMap.put(stickerExchangeDetails.getStickerId(),1);
                    }
                }

                System.out.println(sentMap);
                for(Map.Entry<String, Integer> m: sentMap.entrySet()) {
                    SentHistory sentHistory = new SentHistory(m.getKey(), String.valueOf(m.getValue()));
                    sentHistoryList.add(sentHistory);
                }
                sentHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
