package com.example.petshelp;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petshelp.databinding.FragmentHelpPriutBinding;


public class helpPriutFragment extends Fragment {

    private HelpPriutViewModel mViewModel;
    private FragmentHelpPriutBinding binding;
    Button buttonhelp;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        HelpPriutViewModel helpPriutViewModel = new ViewModelProvider(this).get(HelpPriutViewModel.class);

        binding = FragmentHelpPriutBinding.inflate(inflater, container, false);
        View inflatedView = inflater.inflate(R.layout.fragment_help_priut, container, false);

        buttonhelp = (Button)  inflatedView.findViewById(R.id.toHelpPriut);
        buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tinkoff.ru/cf/9mzXXdTbdW6"));
                startActivity(browserIntent);
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