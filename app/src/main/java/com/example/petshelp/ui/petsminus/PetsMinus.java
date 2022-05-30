package com.example.petshelp.ui.petsminus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.petshelp.AdapterPitomec;
import com.example.petshelp.Pitomec;
import com.example.petshelp.R;

import com.example.petshelp.databinding.PetsMinusFragmentBinding;
import com.example.petshelp.petsProfile;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;




public class PetsMinus extends Fragment {

    private PetsMinusFragmentBinding binding;
    Button button;
    Random random;
    final String LOG_TAG = "myLogs";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PetsMinusViewModel petsMinusViewModel = new ViewModelProvider(this).get(PetsMinusViewModel.class);

        binding = PetsMinusFragmentBinding.inflate(inflater, container, false);
        View inflatedView = inflater.inflate(R.layout.pets_minus_fragment, container, false);










        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pitomec");


        // получаем экземпляр элемента ListView

        final EditText name = (EditText) inflatedView.findViewById(R.id.editText);

        // Создаём пустой массив для хранения имен котов
        ArrayList<Pitomec> products = new ArrayList<Pitomec>();
        AdapterPitomec boxAdapter;

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView


        boxAdapter = new AdapterPitomec(this.getActivity(), products);

        // настраиваем список
        ListView listView = (ListView) inflatedView.findViewById(R.id.listView);
        listView.setAdapter(boxAdapter);

        // Прослушиваем нажатия клавиш
        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {

                        int min = 100000;
                        int max = 999999;
                        int diff = max - min;
                        Random random = new Random();
                        int i = random.nextInt(diff + 1);
                        i += min;
                        String idPitomic = String.valueOf(i);


                        Pitomec pitomec = new Pitomec(name.getText().toString(),"","","",idPitomic,"");

                        DatabaseReference reference;
                        reference = FirebaseDatabase.getInstance().getReference();
                        reference.child("Pitomec").child(idPitomic).setValue(pitomec);



                        name.setText("");
                        return true;
                    }
                return false;
            }
        });



        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                Pitomec msg = snapshot.getValue(Pitomec.class);
                products.add(0,msg);
                boxAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













        return inflatedView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}