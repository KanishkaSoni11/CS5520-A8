package com.cs5520.cs5520_a8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RealTimeDbActivity extends AppCompatActivity {

    private TextView user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_db);

        user = findViewById(R.id.textView);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("stickerExchangeDetails").addChildEventListener(
                new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String name = user.getText().toString();
                        System.out.println("Child added");
                        StickerExchangeDetails user = new StickerExchangeDetails(name, "", "", "");
                        Task t1 = mDatabase.child("stickerExchangeDetails").child(user.senderId).setValue(user);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        System.out.println("Child added");

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        System.out.println("Child added");
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        System.out.println("Child added");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Child added");
                        Toast.makeText(getApplicationContext()
                                , "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void onChildAdded1(View view) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("stickerExchangeDetails");
        StickerExchangeDetails user1 = new StickerExchangeDetails("A", "2", "4", "");
        Task t1 = myRef.child("stickerExchangeDetails").setValue(user1);

//        Task t = myRef.setValue("K");


    }
}
