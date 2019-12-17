package com.example.appartners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class partners_scan extends AppCompatActivity {


    String currImg;

    private DatabaseReference mDatabaseApartment;
    private DatabaseReference mDatabasePartner;

    private FirebaseAuth fAuto;

    private Apartment currentUser;

    private String myPhoto;
    private ImageView imgView;
    private ImageButton mHeart, mLeft, mRight;
    private TextView mNameText, mAgeText;
    private TextView mZeroItemsText;


    private Partner currentPartner;
    private ArrayList<Partner> allPartners;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners_scan);



        imgView = findViewById(R.id.image_view);
        mHeart = findViewById(R.id.heartBtn);
        mLeft = findViewById( R.id.leftBtn );
        mRight = findViewById( R.id.rightBtn );
        mZeroItemsText=findViewById(R.id.zeroItemsText);
        mZeroItemsText.setVisibility(View.INVISIBLE);

        mNameText = findViewById( R.id.nameText );
        mAgeText = findViewById( R.id.ageText );



        fAuto = FirebaseAuth.getInstance();
        mDatabaseApartment = FirebaseDatabase.getInstance().getReference("Apartment");
        mDatabasePartner = FirebaseDatabase.getInstance().getReference("Partner");
        allPartners = new ArrayList<Partner>();

//
//        Query query=mDatabaseRef.orderByChild("aprPrt").equalTo("Searching Apartment");
//
        mDatabasePartner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               Partner partnerUser=null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    partnerUser=data.getValue(Partner.class);
                    allPartners.add(partnerUser);

                }
                if(allPartners.size()>0){

                    Picasso.with(partners_scan.this) // show first User img
                            .load(allPartners.get(0).getImgUrl())
                            .into(imgView);
                    currentPartner =allPartners.get(0);
                    mNameText.setText( currentPartner.getName() );
                    mAgeText.setText( "Age: " + currentPartner.getAge() );

                }else{

                    mHeart.setVisibility(View.INVISIBLE);
                    mLeft.setVisibility(View.INVISIBLE);
                    mRight.setVisibility(View.INVISIBLE);
                    mAgeText.setVisibility(View.INVISIBLE);
                    mNameText.setVisibility(View.INVISIBLE);
                    mZeroItemsText.setVisibility(View.VISIBLE);
                    mZeroItemsText.setText(" no apartments to show");

                    Picasso.with(partners_scan.this)
                            .load("https://firebasestorage.googleapis.com/v0/b/appartners-2735b.appspot.com/o/uploads%2FnoMore.jpg?alt=media&token=1a4d7a69-d6c7-43a0-b888-1e810925ca0c")
                            .into(imgView);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Query currentUserQuery=mDatabaseApartment.orderByChild("email").equalTo(fAuto.getCurrentUser().getEmail());

        currentUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                   currentUser = data.getValue(Apartment.class);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        mHeart.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            //    if(!currentUser.getMyFav().contains(currentPartner)){
//
//                    Toast.makeText(partners_scan.this, currentPartner.getName()+" added to your favorites", Toast.LENGTH_SHORT).show();
//                    currentUser.addFav(currentPartner);
//                    ArrayList<User> updatedFav=currentUser.getMyFav();
//                    mDatabaseRef.child(currentUser.getUserId()).child("myFav").setValue(updatedFav);
//
//
//
//          //      }
//
//
//            }
//        }));
//
//
//        // listener for right and left button if clicked right index++ and show the image of the allPartners.get(index)
//
        mRight.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index++;
                if(index>= allPartners.size()){

                    index=0;

                }

                currentPartner = allPartners.get(index);
                String imgUrl= currentPartner.getImgUrl();
                Picasso.with(partners_scan.this)
                        .load(imgUrl)
                        .into(imgView);
                mNameText.setText( currentPartner.getName() );
                mAgeText.setText( "Age: " + currentPartner.getAge() );
            }
        }));


        mLeft.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index--;
                if(index<0){

                    index=allPartners.size()-1;

                }

                currentPartner = allPartners.get(index);
                String imgUrl= currentPartner.getImgUrl();
                Picasso.with(partners_scan.this)
                        .load(imgUrl)
                        .into(imgView);
                mNameText.setText( currentPartner.getName() );
                mAgeText.setText( "Age: " + currentPartner.getAge() );
            }
        }));

        imgView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Toast.makeText(partners_scan.this, "img clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(partners_scan.this, picsUserInfo.class);

                intent.putExtra("userEmail", currentPartner.getEmail());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,login.class));
        return;
    }

//    // menu code
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.partners_scan_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        Intent intent;
//
//        switch (item.getItemId()) {
//
//            case R.id.personal_details_item:
//                intent=new Intent(this,personal_details.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.my_fav_item:
//                intent=new Intent(this,favorites.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.apartment_details_Item:
//                intent=new Intent(this, apartment_details.class);
//                startActivity(intent);
//                return true;
//
//            case R.id.partners_scan_item:
//                intent=new Intent(this, partners_scan.class);
//                startActivity(intent);
//                return true;
//
//
//            case R.id.LogOutItem:
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), login.class));
//                finish();
//                return true;
//
//            case R.id.RemoveItem:
//
//                new AlertDialog.Builder(this)
//                        .setTitle("Delete User")
//                        .setMessage("Are you sure you want to delete this User?")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Log.i("want to delete","want to delete");
//                                // Continue with delete operation
//                                FirebaseAuth.getInstance().getCurrentUser().delete(); // remove from Authentication
//                                int size=currentUser.getRoom().getImagesUri().size();
//                                for(int i=0; i<size; i++){ // remove all images from storage
//                                    currImg = currentUser.getRoom().getImg(i);
//                                    StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(currImg);
//                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Log.i("delete files",""+currImg);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception exception) {
//                                            Log.i(" cant delete ",""+currImg);
//                                        }
//                                    });
//                                }
//
//                                Toast.makeText( partners_scan.this, "User is Deleted", Toast.LENGTH_LONG ).show();
//                                mDatabaseRef.child(currentUser.getUserId()).setValue(null); // remove from Database
//                                startActivity(new Intent(getApplicationContext(), login.class));
//                                finish();
//
//                            }
//                        })
//
//                        // A null listener allows the button to dismiss the dialog and take no further action.
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}