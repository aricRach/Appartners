package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class picsApartInfo extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    Bundle bundle;
    String apartmentEmail;

    TextView mNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics_apart_info);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mNameText = findViewById(R.id.nameTextView);

         bundle = getIntent().getExtras();
         apartmentEmail = bundle.getString("userEmail"); //  the email of the apartment's user

        Query query=mDatabaseRef.orderByChild("email").equalTo(apartmentEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    user currentUser = data.getValue(user.class);

                    apartment currentRoom=currentUser.getRoom();

                    int numOfRooms=currentRoom.getNumOfRooms();

                    String name = currentUser.getUserName();
                    String city = currentUser.getUserCity();
                    mNameText.setText(name+" live in "+city+" he has "+numOfRooms+" rooms");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
