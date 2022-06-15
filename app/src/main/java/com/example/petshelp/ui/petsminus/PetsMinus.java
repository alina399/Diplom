package com.example.petshelp.ui.petsminus;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.EditText;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.petshelp.AdapterPitomec;
import com.example.petshelp.Pitomec;
import com.example.petshelp.R;

import com.example.petshelp.databinding.PetsMinusFragmentBinding;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;


import java.util.Random;
import java.util.regex.Pattern;


public class PetsMinus extends Fragment {

    private PetsMinusFragmentBinding binding;



    private FirebaseStorage storage;
    private StorageReference storageReference;
    EditText editTextSearch;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PetsMinusViewModel petsMinusViewModel = new ViewModelProvider(this).get(PetsMinusViewModel.class);

        binding = PetsMinusFragmentBinding.inflate(inflater, container, false);
        View inflatedView = inflater.inflate(R.layout.pets_minus_fragment, container, false);










        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pitomec");
        editTextSearch = (EditText) inflatedView.findViewById(R.id.txtsearch);


        /////////////////////////////////////////////
editTextSearch.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
System.out.println(s);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Pitomec> searchPit = new ArrayList<Pitomec>();
                for (DataSnapshot searchcSnapshot: snapshot.getChildren()){
                    Pitomec pitomec = searchcSnapshot.getValue(Pitomec.class);
                    searchPit.add(pitomec);

                }
                System.out.println(searchPit);

                ArrayList<Pitomec> al2 = new ArrayList<Pitomec>();
                for(Pitomec pitomec: searchPit){
                    String namePit = pitomec.name;
                    if (namePit.matches("(?is)" + ".*" + Pattern.quote(s.toString()) + ".*")) {
                        al2.add(pitomec);
                    }
                }
                System.out.println(al2);
                AdapterPitomec boxAdapter;
                boxAdapter = new AdapterPitomec(PetsMinus.this.getActivity(), al2);
                // настраиваем список
                ListView listView = (ListView) inflatedView.findViewById(R.id.listView);
                listView.setAdapter(boxAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });
    }
});

        /////////////////////////////////////////////
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
        //int size = products.size();
       // Log.e("1",String.valueOf(size));

        // Прослушиваем нажатия клавиш
        name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {


                        int min = 10000;
                        int max = 99999;
                        int diff = max - min;
                        Random random = new Random();
                        int i = random.nextInt(diff + 1);
                        i += min;
                        int size = products.size();

                        String idPitomic = String.valueOf(i);

                        storage = FirebaseStorage.getInstance();
                        storageReference = storage.getReference();
                        String ref = "images/"+ "pitomec"+".png";
                        Pitomec pitomec = new Pitomec(name.getText().toString(),"","",ref,idPitomic,"");

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
                products.add(msg);
                boxAdapter.notifyDataSetChanged();
               // int size = products.size();
               // Log.e("1",String.valueOf(size));
               // Toast.makeText(PetsMinus.this.getActivity(), String.valueOf(size), Toast.LENGTH_SHORT).show();
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