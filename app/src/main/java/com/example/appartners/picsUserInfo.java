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

public class picsUserInfo extends AppCompatActivity {


    private DatabaseReference mDatabaseRef;
    Bundle bundle;
    String userEmail;

    TextView mNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics_user_info);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mNameText = findViewById(R.id.nameTextView);

        bundle = getIntent().getExtras();
        userEmail = bundle.getString("userEmail"); //  the email of the user

        Query query=mDatabaseRef.orderByChild("email").equalTo(userEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    user currentUser = data.getValue(user.class);

                    String name = currentUser.getUserName();
                    String city = currentUser.getUserCity();
                    mNameText.setText(name+" live in "+city+" he has ");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
