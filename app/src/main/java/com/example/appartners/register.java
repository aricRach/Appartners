package com.example.appartners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class register extends AppCompatActivity {

    //private static final String TAG="Login";

    // private static final String Key_Username="username";
    //  private static final String Key_password="password";


    // private FirebaseFirestore db =FirebaseFirestore.getInstance(); important for registration page

   // private EditText editUserName;
    //private EditText editPassword;


    // the following code need to be in some button listener after fill the registration fields

    // IMPORTANT FOR REGISTER PAGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //    Toast.makeText(Login.this, "HI your mail is: "+text_name.getText().toString(), Toast.LENGTH_SHORT).show();
//        String userName=editUserName.getText().toString();
//        String pass=editPassword.getText().toString();
//
//        Map<String,Object> note = new HashMap<>();
//        note.put(Key_Username,userName);
//        note.put(Key_password,pass);
//
//        db.collection("notebook").document("my first note").set(note)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(Login.this, "Fail", Toast.LENGTH_SHORT).show();
//                    }
//                });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void backToLogin(View view){


        Intent intent=new Intent(this,Login.class);

        startActivity(intent);
        Toast.makeText(this, "back to login", Toast.LENGTH_LONG).show();

    }
}
