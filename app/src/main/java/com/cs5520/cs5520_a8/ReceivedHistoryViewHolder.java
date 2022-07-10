package com.cs5520.cs5520_a8;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceivedHistoryViewHolder extends RecyclerView.ViewHolder{
    public TextView senderId;
    public TextView dateSent;
    public ImageView sticker;

    public ReceivedHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.senderId = itemView.findViewById(R.id.textView_senderId);
    }
}
