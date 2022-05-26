package com.example.petshelp.ui.petsminus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.petshelp.MainActivity;
import com.example.petshelp.PersonActivity;
import com.example.petshelp.ProfileActivity;
import com.example.petshelp.R;
import com.example.petshelp.SignIn;
import com.example.petshelp.SignUp;
import com.example.petshelp.Test1;
import com.example.petshelp.databinding.PetsMinusFragmentBinding;



public class PetsMinus extends Fragment {

    private PetsMinusFragmentBinding binding;
    Button button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PetsMinusViewModel petsMinusViewModel = new ViewModelProvider(this).get(PetsMinusViewModel.class);

        binding = PetsMinusFragmentBinding.inflate(inflater, container, false);
        View inflatedView = inflater.inflate(R.layout.pets_minus_fragment, container, false);
        button = (Button) inflatedView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetsMinus.this.getActivity(), Test1.class);
                startActivity(intent);

            }
        });

       /* View root = binding.getRoot();

        final TextView textView = binding.textHome;
        petsMinusViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;*/
        return inflatedView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}