package com.example.petshelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

class User {
    public String fio;
    public String email;
    public String birth;
    public String city;
    public String telephone;

    public User() {

    }
    public User(String fio, String email, String birth, String city, String telephone) {

        this.fio = fio;
        this.email = email;
        this.birth = birth;
        this.city = city;
        this.telephone = telephone;





    }

}

public class ProfileActivity extends AppCompatActivity {

    EditText qfio;
    EditText qemail;
    EditText qbirth;
    EditText qcity;
    EditText qtelephone;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        qfio = (EditText) findViewById(R.id.textFieldFio);
        qemail = (EditText) findViewById(R.id.textFieldEmail);
        qbirth = (EditText) findViewById(R.id.textFieldData);
        qcity = (EditText) findViewById(R.id.textFieldCity);
        qtelephone = (EditText) findViewById(R.id.textFieldTelephone);
        imageView = (ImageView) findViewById(R.id.choose);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String userid = user.getUid();
            RebutImage(userid);
            ReadNewUser(userid);
        }


    }


    public void writeNewUser(String userId, String fio, String email, String birth, String city, String telephone) {
        User user = new User(fio, email, telephone, city, birth);

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").child(userId).setValue(user);
    }

    public void ReadNewUser(String userId) {

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    if (task.getResult().getValue(User.class) != null) {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));

                        User user = task.getResult().getValue(User.class);
                        qfio.setText(user.fio);
                        qemail.setText(user.email);
                        qbirth.setText(user.birth);
                        qcity.setText(user.city);
                        qtelephone.setText(user.telephone);


                    }
                }
            }
        });

    }

    public void ButtonSaveProfile(View view) {
        String fio = qfio.getText().toString();
        String city = qcity.getText().toString();
        String birth = qbirth.getText().toString();
        String telephone = qtelephone.getText().toString();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String userid = user.getUid();
            String email = user.getEmail();

            writeNewUser(userid, fio, email, telephone, city, birth);
        }
        uploadImage();
        Toast.makeText(this, "Профиль сохранён!", Toast.LENGTH_SHORT).show();


    }


    public void ImageProfile(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    public void ToPersonActivity(View view) {
        Intent intent = new Intent(this, PersonActivity.class);
        startActivity(intent);
        finish();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();

            StorageReference ref = storageReference.child("images/"+ uid +".jpg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void RebutImage(String uid) {

        StorageReference ref = storageReference.child("images/"+ uid+".jpg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imageView);
            }
        });
    }
}