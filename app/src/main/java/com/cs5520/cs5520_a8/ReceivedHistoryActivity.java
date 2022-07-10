package com.cs5520.cs5520_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ReceivedHistoryActivity extends AppCompatActivity {

    RecyclerView receiveHistoryRecyclerView;
    List<ReceivedHistoryCollector> receivedHistoryCollectors;
    ReceivedHIstoryAdapter receivedHIstoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_history);

        receiveHistoryRecyclerView = findViewById(R.id.recyclerView_receive_history);
        receivedHistoryCollectors = new ArrayList<>();



        receiveHistoryRecyclerView.setHasFixedSize(true);

        //This defines the way in which the RecyclerView is oriented
        receiveHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Associates the adapter with the RecyclerView
        receivedHIstoryAdapter = new ReceivedHIstoryAdapter(receivedHistoryCollectors, this);
        receiveHistoryRecyclerView.setAdapter(receivedHIstoryAdapter);

    }
}