package com.example.petshelp.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.petshelp.AdapterNews;
import com.example.petshelp.AdapterPitomec;
import com.example.petshelp.News;
import com.example.petshelp.Pitomec;
import com.example.petshelp.R;
import com.example.petshelp.databinding.FragmentHomeBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View inflatedView = inflater.inflate(R.layout.fragment_home, container, false);










        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("News");


        // получаем экземпляр элемента ListView


        // Создаём пустой массив для хранения имен котов
        ArrayList<News> products = new ArrayList<News>();
        AdapterNews boxAdapter;

        // Создаём адаптер ArrayAdapter, чтобы привязать массив к ListView


        boxAdapter = new AdapterNews(this.getActivity(), products);

        // настраиваем список
        ListView listView = (ListView) inflatedView.findViewById(R.id.listView);
        listView.setAdapter(boxAdapter);

        // Прослушиваем нажатия клавиш




        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                News msg = snapshot.getValue(News.class);
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