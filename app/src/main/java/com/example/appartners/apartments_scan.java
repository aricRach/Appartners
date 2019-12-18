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

public class apartments_scan extends AppCompatActivity {

    private DatabaseReference mDatabaseApartment;
    private DatabaseReference mDatabasePartner;

    private FirebaseAuth fAuto;

    private String myPhoto;
    private ImageView imgView;
    private ImageButton mLeft, mRight,mHeart;
    private TextView mCityText;
    private TextView mZeroItemsText;

    private String imgUrl;

    private Partner currentUser;
    private Apartment currentApartment;
    private ArrayList<Apartment> allRoomsHolders;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_scan);

        imgView = findViewById(R.id.image_view);
        mHeart = findViewById( R.id.heartBtn );
        mLeft = findViewById( R.id.leftBtn );
        mRight = findViewById( R.id.rightBtn );
        mCityText = findViewById( R.id.cityText );
        mZeroItemsText=findViewById(R.id.zeroItemsText);
        mZeroItemsText.setVisibility(View.INVISIBLE);

        fAuto = FirebaseAuth.getInstance();
        mDatabaseApartment = FirebaseDatabase.getInstance().getReference("Apartment");
        mDatabasePartner = FirebaseDatabase.getInstance().getReference("Partner");
        allRoomsHolders = new ArrayList<Apartment>();

        // make ArrayList of all searching partners users
        // because he searching for partners he has room
        //Query query= mDatabasePartner.orderByChild("aprPrt").equalTo("Searching Partner");

        mDatabaseApartment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Apartment roomHolder=null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    roomHolder=data.getValue(Apartment.class);

                    allRoomsHolders.add(roomHolder);
                }

                if(allRoomsHolders.size()>0){

                    imgUrl=allRoomsHolders.get(0).getImg(0);
                    if(imgUrl.equals(""))
                        imgUrl="https://firebasestorage.googleapis.com/v0/b/appartners-2735b.appspot.com/o/uploads%2FnoPhoto.png?alt=media&token=8fb5f8d9-a80f-4360-843d-7aae13546d13";

                    Picasso.with(apartments_scan.this)
                            .load(imgUrl)
                            .into(imgView);
                    currentApartment =allRoomsHolders.get(0);
                    mCityText.setText( "City: " + currentApartment.getCity() );
                }else{

                    mHeart.setVisibility(View.INVISIBLE);
                    mLeft.setVisibility(View.INVISIBLE);
                    mRight.setVisibility(View.INVISIBLE);
                    mCityText.setVisibility(View.INVISIBLE);
                    mZeroItemsText.setVisibility(View.VISIBLE);
                    mZeroItemsText.setText(" no apartments to show");

                    Picasso.with(apartments_scan.this)
                            .load("https://firebasestorage.googleapis.com/v0/b/appartners-2735b.appspot.com/o/uploads%2FnoMore.jpg?alt=media&token=1a4d7a69-d6c7-43a0-b888-1e810925ca0c")
                            .into(imgView);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query currentUserQuery= mDatabasePartner.orderByChild("email").equalTo(fAuto.getCurrentUser().getEmail());

        currentUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    currentUser = data.getValue(Partner.class);

                   // mDatabaseApartment.child(currentUser.getUserId()).setValue(currentUser);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mHeart.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!cheackContains(currentApartment.getEmail())) {

                    Toast.makeText(apartments_scan.this, currentApartment.getName() + " added to your favorites", Toast.LENGTH_SHORT).show();
                    UserImage ui = new UserImage(currentApartment, currentApartment.getImg(0));
                    currentUser.addFav(ui);
                    mDatabasePartner.child(currentUser.getId()).setValue(currentUser);

                }else{

                    Toast.makeText(apartments_scan.this, currentApartment.getName()+" already in your favorites", Toast.LENGTH_SHORT).show();
                }
            }
        }));

        mRight.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index++;
                if(index>=allRoomsHolders.size()){

                    index=0;

                }

                currentApartment =allRoomsHolders.get(index);
                 imgUrl= currentApartment.getImg(0);
                if(imgUrl.equals(""))
                    imgUrl="https://firebasestorage.googleapis.com/v0/b/appartners-2735b.appspot.com/o/uploads%2FnoPhoto.png?alt=media&token=8fb5f8d9-a80f-4360-843d-7aae13546d13";

                Picasso.with(apartments_scan.this)
                        .load(imgUrl)
                        .into(imgView);
                mCityText.setText( "City: " + currentApartment.getCity() );


            }
        }));


        mLeft.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                index--;
                if(index<0){

                    index=allRoomsHolders.size()-1;

                }

                currentApartment =allRoomsHolders.get(index);
                 imgUrl= currentApartment.getImg(0);
                if(imgUrl.equals(""))
                    imgUrl="https://firebasestorage.googleapis.com/v0/b/appartners-2735b.appspot.com/o/uploads%2FnoPhoto.png?alt=media&token=8fb5f8d9-a80f-4360-843d-7aae13546d13";

                Picasso.with(apartments_scan.this)
                        .load(imgUrl)
                        .into(imgView);
                mCityText.setText( "City: " + currentApartment.getCity() );



            }
        }));

        imgView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Toast.makeText(apartments_scan.this, "img clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(apartments_scan.this, picsApartInfo.class);

                 intent.putExtra("userEmail", currentApartment.getEmail());
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
                intent.putExtra("searchingFor", "Searching apartment");
                startActivity(intent);
                return true;

            case R.id.my_fav_item:
                intent=new Intent(this,favorites.class);
                intent.putExtra("searchingFor", "Searching apartment");
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

            case R.id.RemoveItem:

                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                FirebaseAuth.getInstance().getCurrentUser().delete(); // remove from Authentication
                                if(!currentUser.getImgUrl().equals("")){

                                    StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(currentUser.getImgUrl());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
                                            Log.i("delete files","file deleted");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
                                        }
                                    });
                                }

                                mDatabasePartner.child(currentUser.getId()).setValue(null); // remove from Database
                                Toast.makeText( apartments_scan.this, "User is Deleted", Toast.LENGTH_LONG ).show();
                                startActivity(new Intent(getApplicationContext(), login.class));
                                finish();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean cheackContains(String email){

        int size=currentUser.getMyFav().size();
        for(int i=0;i<size;i++){

            if(currentUser.getFav(i).getEmail().equals(email)){

                return true;
            }
        }
        return false;

    }



}

