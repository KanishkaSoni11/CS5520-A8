package com.cs5520.cs5520_a8;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SentHistoryViewHolder  extends RecyclerView.ViewHolder {

    public ImageView stickerIV;
    public TextView countTV;

    public SentHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.stickerIV = itemView.findViewById(R.id.sentSticker);
        this.countTV = itemView.findViewById(R.id.sentCount);
    }

    public void bindThisData(SentHistory sentHistoryToBind) {

        String uri = "@drawable/"+ sentHistoryToBind.getSticker();
        int imageResource = itemView.getContext().getResources().getIdentifier(uri, null, itemView.getContext().getPackageName());
        Drawable res = itemView.getContext().getResources().getDrawable(imageResource);
        stickerIV.setImageDrawable(res);
        String s = "Sent Count: ".concat(sentHistoryToBind.getCount());
        countTV.setText(s);
    }
}
