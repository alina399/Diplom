package com.example.petshelp.ui.petspristr;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petshelp.AdapterPitomec;
import com.example.petshelp.AdapterPitomecPristr;
import com.example.petshelp.Pitomec;
import com.example.petshelp.R;
import com.example.petshelp.databinding.PetsPristrFragmentBinding;
import com.example.petshelp.petsProfile;
import com.example.petshelp.petsprofile2;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PetsPristrFragment extends Fragment {

    private PetsPristrViewModel mViewModel;
    private PetsPristrFragmentBinding binding;

    public static PetsPristrFragment newInstance() {
        return new PetsPristrFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PetsPristrViewModel petsPristrViewModel = new ViewModelProvider(this).get(PetsPristrViewModel.class);

        binding = PetsPristrFragmentBinding.inflate(inflater, container, false);
        View inflatedView = inflater.inflate(R.layout.pets_pristr_fragment, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PitomecPristr");


        // получаем экземпляр элемента ListView

        final EditText name = (EditText) inflatedView.findViewById(R.id.editText);

        // Создаём пустой массив для хранения имен котов
        ArrayList<Pitomec> products = new ArrayList<Pitomec>();
        AdapterPitomecPristr boxAdapter;

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView


        boxAdapter = new AdapterPitomecPristr(this.getActivity(), products);

        // настраиваем список
        ListView listView = (ListView) inflatedView.findViewById(R.id.listView);
        listView.setAdapter(boxAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), petsprofile2.class);
                intent.putExtra("id",  ((TextView) itemClicked).getText());
                startActivity(intent);
                Toast.makeText(getContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
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
