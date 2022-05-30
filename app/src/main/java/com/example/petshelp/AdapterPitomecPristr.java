package com.example.petshelp;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petshelp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdapterPitomecPristr extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Pitomec> objects;
    ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public AdapterPitomecPristr(Context context, ArrayList<Pitomec> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.activity_card, parent, false);
        }

        Pitomec p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.adapterName)).setText(p.name);
        ((TextView) view.findViewById(R.id.adapterAge)).setText(p.age);
        ((TextView) view.findViewById(R.id.adapterId)).setText(p.id);
        imageView = (ImageView) view.findViewById(R.id.adapterImage);
        if(p.photo.equals("Yes")){
            RebutImage(p.id);}
        ImageButton perehod = (ImageButton) view.findViewById(R.id.perehod);
        perehod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, p.name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx, petsprofile2.class);
                intent.putExtra("id",  p.id);
                ctx.startActivity(intent);
            }
        });

        return view;
    }

    // товар по позиции
    Pitomec getProduct(int position) {
        return ((Pitomec) getItem(position));
    }
    private void RebutImage(String uid) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        StorageReference ref = storageReference.child("images/"+ uid+".jpg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imageView);
            }
        });
    }

}