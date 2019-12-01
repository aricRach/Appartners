package com.example.appartners;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class register extends AppCompatActivity {


    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuto;
    ProgressBar progressBar;
    Spinner spnCity;
    boolean chosenCity;
    boolean chosenGender;
    RadioGroup genderGroup;
    RadioButton genderBtn;
    DatePicker mBirthday;

    DatabaseReference databaseRegister;

    //private FirebaseFirestore db =FirebaseFirestore.getInstance(); // aric


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseRegister = FirebaseDatabase.getInstance().getReference("Users");

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        //  mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);
        spnCity=findViewById(R.id.citySpinner);
        genderGroup=findViewById(R.id.radioGroup);
        mBirthday = findViewById(R.id.Birthday);


        fAuto = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if(fAuto.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String yourCity = spnCity.getSelectedItem().toString();

                int selectedRadioButtonID = genderGroup.getCheckedRadioButtonId();

                // If nothing is selected from Radio Group, then it return -1
                if (selectedRadioButtonID != -1) {
                    genderBtn = findViewById(selectedRadioButtonID);
                    chosenGender=true;
                }
                else{
                    chosenGender=false;
                }

                if (yourCity.equals("city")) {
                    chosenCity=false;               }
                else{
                    chosenCity=true;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                addUser();
                //register the user in firebase
                if(chosenCity && chosenGender) {
                    fAuto.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register.this, "User Created.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(register.this, "please fill up all details", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    private void addUser(){

        String id = databaseRegister.push().getKey();
        Users user = new Users(id,mFullName,genderGroup,spnCity,mBirthday);
        databaseRegister.child(id).setValue(user);

        /*Map<String,Object> note = new HashMap<>();
       note.put("username",mFullName);
        note.put("gender",genderGroup);

                db.collection("notebook").document("my first note").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(register.this, "success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(register.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });*/

    }
}
