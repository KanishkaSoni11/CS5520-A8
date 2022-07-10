package com.cs5520.cs5520_a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceivedHistoryAdapter extends RecyclerView.Adapter<ReceivedHistoryViewHolder> {

    private List<ReceivedHistoryCollector> receivedHistoryCollectors;
    private final Context context;

    public ReceivedHistoryAdapter(List<ReceivedHistoryCollector> receivedHistoryCollectors, Context context) {
        this.receivedHistoryCollectors = receivedHistoryCollectors;
        this.context = context;
    }


    @NonNull
    @Override
    public ReceivedHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReceivedHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recieve_history, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedHistoryViewHolder holder, int position) {
        holder.bindThisData(receivedHistoryCollectors.get(position));
    }

    @Override
    public int getItemCount() {
        return receivedHistoryCollectors.size();
    }
}
