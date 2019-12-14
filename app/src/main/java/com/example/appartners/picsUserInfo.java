package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class picsUserInfo extends AppCompatActivity {


    private DatabaseReference mDatabaseRef;
    Bundle bundle;
    String userEmail;
    user currentUser;

    private TextView mNameText, mGenderText, mAgeText, mCityText, mEmailText, mTellAboutText;
    private ImageView mImage_View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pics_user_info);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mNameText = findViewById( R.id.nameText );
        mGenderText = findViewById( R.id.genderText );
        mAgeText = findViewById( R.id.ageText );
        mCityText = findViewById( R.id.cityText );
        mEmailText = findViewById( R.id.emailText );
        mTellAboutText = findViewById( R.id.tellAboutText );
        mImage_View = findViewById( R.id.image_view );



        bundle = getIntent().getExtras();
        userEmail = bundle.getString("userEmail"); //  the email of the user

        Query query=mDatabaseRef.orderByChild("email").equalTo(userEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    currentUser = data.getValue(user.class);

                    mNameText.setText( "Name: " + currentUser.getUserName() );
                    mGenderText.setText( "Gender: " + currentUser.getUserGender() );
                    mAgeText.setText( "Age: " +currentUser.getUserBirthday() );
                    mCityText.setText( "City: " +currentUser.getUserCity() );
                    mEmailText.setText( "Email: " +currentUser.getEmail() );
                    mTellAboutText.setText( "Tell about your self:\n" +currentUser.getTellAbout() );

                    mDatabaseRef.child(currentUser.getUserId()).setValue(currentUser);

                }


                    Picasso.with(picsUserInfo.this)
                            .load(currentUser.getImgUrl())
                            .into(mImage_View);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
