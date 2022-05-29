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
import android.widget.Button;

import com.example.petshelp.R;
import com.example.petshelp.Test1;
import com.example.petshelp.databinding.PetsMinusFragmentBinding;
import com.example.petshelp.databinding.PetsPristrFragmentBinding;
import com.example.petshelp.ui.petsminus.PetsMinusViewModel;

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

        return inflatedView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
