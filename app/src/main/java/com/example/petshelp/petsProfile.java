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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.petshelp.Pitomec;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class petsProfile extends AppCompatActivity {
TextView id;
EditText qname, age, history;
Button savebutton, pristroit;
ImageView imageView;
private String photo;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_profile);
        pristroit = (Button) findViewById(R.id.pristroit);
        savebutton = (Button) findViewById(R.id.save_profile_pets);
        imageView = (ImageView) findViewById(R.id.choosepets);
        qname = (EditText) findViewById(R.id.textFieldNamePets);
        age = (EditText) findViewById(R.id.textFieldAge);
        history = (EditText) findViewById(R.id.textFieldHaract);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        id = (TextView) findViewById(R.id.idpets);
        String idPit = getIntent().getStringExtra("id");


        reference.child("Pitomec").child(idPit).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {



                        Pitomec pitomec = task.getResult().getValue(Pitomec.class);
                        qname.setText(pitomec.name);
                        age.setText(pitomec.age);
                        history.setText(pitomec.history);
                        RebutImage(idPit);



            }
        });




        id.setText(idPit);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference;
                reference = FirebaseDatabase.getInstance().getReference();

                uploadImage();

                Pitomec pitomec = new Pitomec(qname.getText().toString(),age.getText().toString(),history.getText().toString(),photo,idPit,"");
                reference.child("Pitomec").child(idPit).setValue(pitomec);

                Toast.makeText(petsProfile.this, "Сохранено!", Toast.LENGTH_SHORT).show();
            }
        });

        pristroit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference;
                reference = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String emailGo = user.getEmail().toString();
                Pitomec pitomec = new Pitomec(qname.getText().toString(),age.getText().toString(),history.getText().toString(),"Yes",idPit,emailGo);
                reference.child("PitomecPristr").child(idPit).setValue(pitomec);
                reference.child("Pitomec").child(idPit).removeValue();
                Toast.makeText(petsProfile.this, "Вы пристроили питомца", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(petsProfile.this,PersonActivity.class);
                startActivity(intent);
            }
        });




    }



    public void choosepit(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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



            StorageReference ref = storageReference.child("images/"+ id.getText().toString() +".jpg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(petsProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            DatabaseReference reference;
                            reference = FirebaseDatabase.getInstance().getReference();
                            reference.child("Pitomec").child(id.getText().toString()).child("photo").setValue("Yes");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(petsProfile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            photo = "Yes";
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