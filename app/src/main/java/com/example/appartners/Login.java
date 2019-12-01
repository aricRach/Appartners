package com.example.appartners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    private EditText editUserName;
    private EditText editPassword;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        CardView card_view = (CardView) findViewById(R.id.LoginCard); // creating a CardView and assigning a value.

        editUserName= (EditText)findViewById(R.id.EmailText);
        editPassword= (EditText)findViewById(R.id.passText);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                if (firebaseAuth.getCurrentUser()!=null){

                    //goToReg(); //if i allow this it will take me to registration page but cant go back to login because
                    // i already logged in ! need to fix it!! because it on "onCreate" function
                }
            }
        };

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tryToLogin();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void tryToLogin(){

        String email=editUserName.getText().toString();
        String password=editPassword.getText().toString();

        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){

            Toast.makeText(Login.this, "empty fields", Toast.LENGTH_SHORT).show();

        }else{

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()){

                        Toast.makeText(Login.this, "Login not successed", Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(Login.this, "You Logged in", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

    }

    public void goToReg(View view){

     //   Intent intent=new Intent(this,register.class);
       // startActivity(intent);


      Intent intent=new Intent(this,personal_details.class);
        startActivity(intent);
    }


}


