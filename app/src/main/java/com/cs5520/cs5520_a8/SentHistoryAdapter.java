package com.cs5520.cs5520_a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SentHistoryAdapter extends RecyclerView.Adapter<SentHistoryViewHolder> {

    private List<SentHistory> sentHistoryList;
    private final Context context;

    public SentHistoryAdapter(List<SentHistory> sentHistoryList, Context context) {
        this.sentHistoryList = sentHistoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public SentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SentHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_sent_history_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull SentHistoryViewHolder holder, int position) {
        holder.bindThisData(sentHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return sentHistoryList.size();    }
}
