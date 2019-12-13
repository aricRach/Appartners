package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class favorites extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuto;

   private user currentUser;
   private user currentPartApart;
   private ArrayList<user> allFav;

    private ImageView imgView;
    private Button mRightButton;
    private Button mLeftButton;
    private ImageButton mDelButton;

    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        imgView = findViewById(R.id.image_view);
        mRightButton = findViewById(R.id.rightButton);
        mLeftButton = findViewById((R.id.leftButton));
        mDelButton= findViewById(R.id.delButton2);

        fAuto = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        index=0;


        Query query=mDatabaseRef.orderByChild("email").equalTo(fAuto.getCurrentUser().getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    currentUser=data.getValue(user.class);
                    allFav=currentUser.getMyFav();

                }
                if(allFav.size()>0){

                  //  Toast.makeText(favorites.this, ""+allFav.get(0).getEmail(), Toast.LENGTH_SHORT).show();
                   //String a= allFav.get(0).getImgUri();
                  //  Toast.makeText(favorites.this, ""+allFav.get(0).getEmail(), Toast.LENGTH_SHORT).show();

                    Picasso.with(favorites.this) // show first user img
                            .load(allFav.get(0).getImgUri())
                            .into(imgView);
                    currentPartApart=allFav.get(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRightButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index++;
                if(index>=allFav.size()){

                    index=0;

                }

                currentPartApart =allFav.get(index);
                String imgUrl= currentPartApart.getImgUri();
                Picasso.with(favorites.this)
                        .load(imgUrl)
                        .into(imgView);


            }
        }));

        mLeftButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index--;
                if(index<0){

                    index=allFav.size()-1;

                }

                currentPartApart =allFav.get(index);
                String imgUrl= currentPartApart.getImgUri();
                Picasso.with(favorites.this)
                        .load(imgUrl)
                        .into(imgView);


            }
        }));

        imgView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Toast.makeText(favorites.this, "img clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(favorites.this, picsApartInfo.class);

                intent.putExtra("userEmail", currentPartApart.getEmail());
                startActivity(intent);

            }
        });

        mDelButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = allFav.indexOf(currentPartApart);
                Toast.makeText(favorites.this, ""+i+" deleted from favorites", Toast.LENGTH_SHORT).show();
                currentUser.getMyFav().remove(currentPartApart);
                //mDatabaseRef.child(currentUser.getUserId()).setValue(currentUser);
                ArrayList<user> updatedFav=currentUser.getMyFav();
                mDatabaseRef.child(currentUser.getUserId()).child("myFav").setValue(updatedFav);

                index=0;
                currentPartApart =allFav.get(index);
                String imgUrl= currentPartApart.getImgUri();
                Picasso.with(favorites.this)
                        .load(imgUrl)
                        .into(imgView);

                //currentUser.getMyFav().remove(i);
            }
        }));


    }
}
