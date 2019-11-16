package com.example.appartners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String TAG="Login";

    private static final String Key_Username="username";
    private static final String Key_password="password";

    private EditText editUserName;
    private EditText editPassword;

    private FirebaseFirestore db =FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CardView card_view = (CardView) findViewById(R.id.LoginCard); // creating a CardView and assigning a value.

        editUserName= (EditText)findViewById(R.id.EmailText);
        editPassword= (EditText)findViewById(R.id.passText);

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Login.this, "HI your mail is: "+text_name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToReg(View view){

        String userName=editUserName.getText().toString();
        String pass=editPassword.getText().toString();

        Map<String,Object> note = new HashMap<>();
        note.put(Key_Username,userName);
        note.put(Key_password,pass);

         db.collection("notebook").document("my first note").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(Login.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });


        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();


    }


}


