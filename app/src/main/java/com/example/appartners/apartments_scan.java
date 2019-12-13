package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class apartments_scan extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuto;

    private String myPhoto;
    private ImageView imgView;
    private Button mRightButton;
    private Button mLeftButton;
    private ImageButton mStar;

    private user currentUser;
    private user currentHolder;
    private ArrayList<user> allRoomsHolders;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_scan);

        imgView = findViewById(R.id.image_view);
        mRightButton=findViewById(R.id.rightButton);
        mLeftButton=findViewById((R.id.leftButton));
        mStar = findViewById(R.id.imageButton);


        fAuto = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        allRoomsHolders = new ArrayList<user>();

        // make ArrayList of all searching partners users
        // because he searching for partners he has room
        Query query=mDatabaseRef.orderByChild("aprPrt").equalTo("Searching partner");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user roomHolder=null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    roomHolder=data.getValue(user.class);
                    allRoomsHolders.add(roomHolder);
                }
                Picasso.with(apartments_scan.this)
                        .load(allRoomsHolders.get(0).getRoom().getImg(0))
                        .into(imgView);
                currentHolder =allRoomsHolders.get(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query currentUserQuery=mDatabaseRef.orderByChild("email").equalTo(fAuto.getCurrentUser().getEmail());

        currentUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    currentUser = data.getValue(user.class);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mStar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!currentUser.getMyFav().contains(currentHolder)){

                    Toast.makeText(apartments_scan.this, currentHolder.getUserName()+" added to your favorites", Toast.LENGTH_SHORT).show();
                    currentUser.addFav(currentHolder);
                    ArrayList<user> updatedFav=currentUser.getMyFav();
                    mDatabaseRef.child(currentUser.getUserId()).child("myFav").setValue(updatedFav);
                }else{}

            }
        }));


        mRightButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index++;
                if(index>=allRoomsHolders.size()){

                    index=0;

                }

                currentHolder =allRoomsHolders.get(index);
                String imgUrl= currentHolder.getRoom().getImg(0);
                Picasso.with(apartments_scan.this)
                        .load(imgUrl)
                        .into(imgView);


            }
        }));

        mLeftButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index--;
                if(index<0){

                    index=allRoomsHolders.size()-1;

                }

                currentHolder =allRoomsHolders.get(index);
                String imgUrl= currentHolder.getRoom().getImg(0);
                Picasso.with(apartments_scan.this)
                        .load(imgUrl)
                        .into(imgView);


            }
        }));

        imgView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Toast.makeText(apartments_scan.this, "img clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(apartments_scan.this, picsApartInfo.class);

                 intent.putExtra("userEmail", currentHolder.getEmail());
                 startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,login.class));
        return;
    }

    // menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.apartment_scan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {

            case R.id.personal_details_item:
                intent=new Intent(this,personal_details.class);
                startActivity(intent);
                return true;

            case R.id.my_fav_item:
                intent=new Intent(this,favorites.class);
                startActivity(intent);
                return true;

            case R.id.apartment_scan_item:
                intent=new Intent(this, apartments_scan.class);
                startActivity(intent);
                return true;


            case R.id.LogOutItem:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

