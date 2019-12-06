package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;

public class apartments_scan extends AppCompatActivity {


    // menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logged_menu, menu);
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

            case R.id.LogOutItem:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private DatabaseReference mDatabaseRef;
    private FirebaseAuth fAuto;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_scan);

        fAuto = FirebaseAuth.getInstance();

        text=findViewById(R.id.textView);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        Query query=mDatabaseRef.orderByChild("/u").orderByChild("email").equalTo("dira12@gmail.com");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

//                    user currentUser = data.getValue(user.class);
//                    String city = currentUser.getUserCity();
//                    String name = currentUser.getUserName();
//                    int age = currentUser.getUserBirthday();
//                    if (currentUser != null) {
//
//                        text.setText(name + " is living in " + city + " his age is: " + age );
//                    }
                    text.setText("good");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
