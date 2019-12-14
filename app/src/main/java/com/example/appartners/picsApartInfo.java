package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class picsApartInfo extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    Bundle bundle;
    String apartmentEmail;

    private TextView mPrice, mOccupants, mStreet, mRoomNums, mTypeOfRoom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics_apart_info);

        mPrice = findViewById( R.id.priceText );
        mOccupants = findViewById( R.id.occupantsText );
        mStreet = findViewById( R.id.streetText );
        mRoomNums = findViewById( R.id.roomsNumText );
        mTypeOfRoom = findViewById( R.id.typeOfRoomText );

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");




         bundle = getIntent().getExtras();
         apartmentEmail = bundle.getString("userEmail"); //  the email of the apartment's user

        Query query=mDatabaseRef.orderByChild("email").equalTo(apartmentEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    user currentUser = data.getValue(user.class);

                    apartment currentRoom=currentUser.getRoom();

                    mPrice.setText( "Price: " + currentRoom.getPrice() );
                    mOccupants.setText( "Occupants: " + currentRoom.getOccupants() );
                    mStreet.setText( "Street: " + currentRoom.getStreet() );
                    mRoomNums.setText( "Number Rooms: " + currentRoom.getNumOfRooms() );
                    mTypeOfRoom.setText( "Type Of Room: " + currentRoom.getRoomType() );

                    mDatabaseRef.child(currentUser.getUserId()).setValue(currentUser);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
