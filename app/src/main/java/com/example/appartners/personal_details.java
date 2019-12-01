package com.example.appartners;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class personal_details extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TakedPicture=2;
    private int uploadFrom=0; // image not choosed yet / 1 is gallery / 2 is camera

    private Button mButtonChooseImage;
    private Button mButtonTakePic;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private CardView updateCardView;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    StorageReference mountainsRef; // for camera capture
    StorageReference mountainImagesRef; // // for camera capture

    Bitmap bitmap; // the image will save as bitmap in order to show it

    private StorageTask mUploadTask;

// menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.loggedmenue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.personalItem:
                Intent intent=new Intent(this,personal_details.class);
                startActivity(intent);
                return true;
            case R.id.LogOutItem:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonTakePic = findViewById(R.id.takePicButton);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        updateCardView=findViewById(R.id.updateCard);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads"); // save in storage
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mountainsRef = mStorageRef.child(""+System.currentTimeMillis()+".jpg"); // dell
        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/mountains.jpg");

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        updateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress()) { // if not null and not already uploaded
                    Toast.makeText(personal_details.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {

                    if(uploadFrom==1){

                        uploadFileFromGallery();

                    }else if (uploadFrom==2){

                        uploadFromCapturedImage();
                    } else{

                        Toast.makeText(personal_details.this, "please upload image", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


        mButtonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,TakedPicture);

            }
        });


        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // called when we picked our file/take picture
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK // successfully picked image
                && data != null && data.getData() != null) {
            mImageUri = data.getData(); // the ui of the image we picked

            Picasso.with(this).load(mImageUri).into(mImageView); // load the image to the image view
            uploadFrom=1;
        }else{

            if (requestCode == TakedPicture && resultCode==RESULT_OK) {

                bitmap = (Bitmap) data.getExtras().get("data");

                mImageView.setImageBitmap(bitmap);

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { // not sure

                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        showPermissionExplanation();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, TakedPicture);
                    }
                }

                uploadFrom=2;

            }else{

                Toast.makeText(this, "Result canceled", Toast.LENGTH_LONG).show();
            }

        }

    }
    public void showPermissionExplanation() // not sure we need it anymore because external flash mybe not needed
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This app need to access to your External Storage because bla bla bla");
        builder.setTitle("External Storage needed");
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, TakedPicture);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String getFileExtension(Uri uri) { // responsible for the file extension
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFileFromGallery() {

        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() // to get unique id we put current time
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler(); // to delay the progress bar for 0.5 sec
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(personal_details.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), // (name,url)
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString()); // https://stackoverflow.com/questions/50660975/firebase-storage-getdownloadurl-method-cant-be-resolved
                            String uploadId = mDatabaseRef.push().getKey(); // create new entry in our database
                            mDatabaseRef.child(uploadId).setValue(upload); // then take the unique id and set the data to the upload


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(personal_details.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {

            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFromCapturedImage() {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataArr = baos.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(dataArr);

        mStorageRef.child( ""+System.currentTimeMillis()+".jpg");// gives unique name
        mountainImagesRef = mStorageRef.child(""+System.currentTimeMillis()+".jpg");

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(personal_details.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Handler handler = new Handler(); // to delay the progress bar for 0.5 sec
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setProgress(0);
                    }
                }, 500);

                Toast.makeText(personal_details.this, "Upload successful", Toast.LENGTH_LONG).show();

                Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), // (name,url)
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                String uploadId = mDatabaseRef.push().getKey(); // create new entry in our database
                mDatabaseRef.child(uploadId).setValue(upload); // then take the unique id and set the data to the upload

            }

        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });
    }
}
