package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class picsApartInfo extends AppCompatActivity {

    private DatabaseReference mDatabaseApartment;
    private Bundle bundle;
    private String apartmentEmail;
    private String imgUrl;
    private Apartment currentApartment;

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

        mDatabaseApartment = FirebaseDatabase.getInstance().getReference("Apartment");

         bundle = getIntent().getExtras();
         apartmentEmail = bundle.getString("userEmail"); //  the email of the Apartment's User

        Query query= mDatabaseApartment.orderByChild("email").equalTo(apartmentEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                     currentApartment = data.getValue(Apartment.class);

                    mPrice.setText( "Price: " + currentApartment.getPrice() );
                    mOccupants.setText( "Occupants: " + currentApartment.getOccupants() );
                    mStreet.setText( "Street: " + currentApartment.getStreet() );
                    mRoomNums.setText( "Number Rooms: " + currentApartment.getNumOfRooms() );
                    mTypeOfRoom.setText( "Type Of Room: " + currentApartment.getRoomType() );

                    mDatabaseApartment.child(currentApartment.getId()).setValue(currentApartment);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
