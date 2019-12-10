package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class apartments_scan extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuto;

    private String myPhoto;

    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_scan);

        imgView = findViewById(R.id.image_view);
        fAuto = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        Query query=mDatabaseRef.orderByChild("email").equalTo(fAuto.getCurrentUser().getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    user currentUser = data.getValue(user.class);

                    myPhoto = currentUser.getImgUri();

                    Toast.makeText(apartments_scan.this, "" + myPhoto, Toast.LENGTH_SHORT).show();
                    Log.i("the photo", myPhoto);

                    Picasso.with(apartments_scan.this)
                            .load(myPhoto)
                            .into(imgView);

                    imgView.setOnClickListener(new View.OnClickListener() {
                        //@Override
                        public void onClick(View v) {
                            Toast.makeText(apartments_scan.this, "img clicked", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(apartments_scan.this, picsApartInfo.class);

                           // intent.putExtra("userImg", currentUser.getEmail());
                            //startActivity(intent);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
}

