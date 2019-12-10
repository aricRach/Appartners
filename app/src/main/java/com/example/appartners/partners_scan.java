package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class partners_scan extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuto;

    private String myPhoto;
    private ImageView imgView;
    private Button mRightButton;
    private Button mLeftButton;

    private user currentUser;
    private ArrayList<user> allPartners;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_scan);


        imgView = findViewById(R.id.image_view);
        mRightButton = findViewById(R.id.rightButton);
        mLeftButton = findViewById((R.id.leftButton));


        fAuto = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        allPartners = new ArrayList<user>();


        Query query=mDatabaseRef.orderByChild("aprPrt").equalTo("Searching apartment");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               user partnerUser=null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    partnerUser=data.getValue(user.class);
                    allPartners.add(partnerUser);

                }
                Picasso.with(partners_scan.this) // show first user img
                        .load(allPartners.get(0).getImgUri())
                        .into(imgView);
                currentUser=allPartners.get(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // listener for right and left button if clicked right index++ and show the image of the allPartners.get(index)


        mRightButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index++;
                if(index>= allPartners.size()){

                    index=0;

                }

                currentUser= allPartners.get(index);
                String imgUrl=currentUser.getImgUri();
                Picasso.with(partners_scan.this)
                        .load(imgUrl)
                        .into(imgView);


            }
        }));

        mLeftButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index--;
                if(index<0){

                    index= allPartners.size()-1;

                }

                currentUser= allPartners.get(index);
                String imgUrl=currentUser.getImgUri();
                Picasso.with(partners_scan.this)
                        .load(imgUrl)
                        .into(imgView);
            }
        }));

        imgView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Toast.makeText(partners_scan.this, "img clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(partners_scan.this, picsUserInfo.class);

                intent.putExtra("userEmail", currentUser.getEmail());
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
        inflater.inflate(R.menu.partners_scan_menu, menu);
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

            case R.id.apartment_details_Item:
                intent=new Intent(this, apartment_details.class);
                startActivity(intent);
                return true;

            case R.id.partners_scan_item:
                intent=new Intent(this, partners_scan.class);
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