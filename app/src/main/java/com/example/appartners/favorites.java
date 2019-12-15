package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class favorites extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuto;

   private user currentUser;
   private user currentPartApart;
   private ArrayList<user> allFav;
   private String searchingFor;

    private ImageView imgView;
    private ImageButton mRightButton;
    private ImageButton mLeftButton;
    private ImageButton mDelButton;
    private TextView mZeroItemsText;

    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        imgView = findViewById(R.id.image_view);
        mRightButton = findViewById(R.id.rightBtn);
        mLeftButton = findViewById((R.id.leftBtn));
        mDelButton= findViewById(R.id.delButton2);
        mZeroItemsText=findViewById(R.id.zeroItemsText);
        mZeroItemsText.setVisibility(View.INVISIBLE);

        fAuto = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        index=0;


        Query query=mDatabaseRef.orderByChild("email").equalTo(fAuto.getCurrentUser().getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    currentUser=data.getValue(user.class);
                    allFav=currentUser.getMyFav();
                    searchingFor=currentUser.getAprPrt();

                }
                if(allFav.size()>0){

                    Picasso.with(favorites.this) // show first user img
                            .load(allFav.get(0).getImgUrl())
                            .into(imgView);
                    currentPartApart=allFav.get(0);
                }else{

                    mRightButton.setVisibility(View.INVISIBLE);
                    mLeftButton.setVisibility(View.INVISIBLE);
                   // imgView.setVisibility(View.INVISIBLE);
                    mDelButton.setVisibility(View.INVISIBLE);
                    mZeroItemsText.setVisibility(View.VISIBLE);
                    mZeroItemsText.setText("no favorites for you");
                    Picasso.with(favorites.this) // show first user img
                            .load("https://aworshipersjournal.files.wordpress.com/2018/11/no-favorites.jpg?w=300&h=300")
                            .into(imgView);
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
                String imgUrl= currentPartApart.getImgUrl();
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
                String imgUrl= currentPartApart.getImgUrl();
                Picasso.with(favorites.this)
                        .load(imgUrl)
                        .into(imgView);


            }
        }));

        imgView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                Toast.makeText(favorites.this, "img clicked", Toast.LENGTH_SHORT).show();

                if(searchingFor.equals("Searching partner")){

                    Intent intent = new Intent(favorites.this, picsUserInfo.class);
                    intent.putExtra("userEmail", currentPartApart.getEmail());
                    startActivity(intent);
                } else{

                    Intent intent = new Intent(favorites.this, picsApartInfo.class);
                    intent.putExtra("userEmail", currentPartApart.getEmail());
                    startActivity(intent);
                }

            }
        });

        mDelButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentUser.getMyFav().remove(currentPartApart);
                allFav.remove(currentPartApart);
                mDatabaseRef.child(currentUser.getUserId()).child("myFav").setValue(currentUser.getMyFav());

                if(allFav.size()==0){

                    if(searchingFor.equals("Searching partner")){

                        startActivity(new Intent(getApplicationContext(), partners_scan.class));

                    }else{

                        startActivity(new Intent(getApplicationContext(), apartments_scan.class));
                    }
                }else{

                    index=0;
                    currentPartApart =allFav.get(index);
                    String imgUrl= currentPartApart.getImgUrl();
                    Picasso.with(favorites.this)
                            .load(imgUrl)
                            .into(imgView);

                }
            }
        }));


    }


    @Override
    public void onBackPressed() {


        if(currentUser.getAprPrt().equals("Searching apartment")){
            startActivity(new Intent(this,apartments_scan.class));

        }else{
            startActivity(new Intent(this,partners_scan.class));
        }
        return;
    }

}
