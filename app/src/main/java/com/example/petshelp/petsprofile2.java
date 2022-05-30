package com.example.petshelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class petsprofile2 extends AppCompatActivity {
    TextView id;
    TextView qname, age, history, email;
    ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petsprofile2);

        imageView = (ImageView) findViewById(R.id.choosepets);
        qname = (TextView) findViewById(R.id.textFieldNamePets);
        age = (TextView) findViewById(R.id.textFieldAge);
        history = (TextView) findViewById(R.id.textFieldHaract);
        email = (TextView) findViewById(R.id.emailpristr);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        id = (TextView) findViewById(R.id.idpets);
        String idPit = getIntent().getStringExtra("id");

        reference.child("PitomecPristr").child(idPit).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {



                Pitomec pitomec = task.getResult().getValue(Pitomec.class);
                qname.setText(pitomec.name);
                age.setText(pitomec.age);
                history.setText(pitomec.history);
                email.setText("Пристроил: " + pitomec.email);
                RebutImage(idPit);



            }
        });
        id.setText(idPit);
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