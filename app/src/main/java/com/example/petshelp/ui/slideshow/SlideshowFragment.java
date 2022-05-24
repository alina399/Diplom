package com.example.petshelp.ui.slideshow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.petshelp.R;
import com.example.petshelp.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    ImageView lapushki,ZooSfera, Sber;
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_slideshow, container, false);
        lapushki = (ImageView)  inflatedView.findViewById(R.id.ToLapushki);
        ZooSfera = (ImageView)  inflatedView.findViewById(R.id.ToZooSfera);
        Sber = (ImageView) inflatedView.findViewById(R.id.ToSber);
        lapushki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://lapyshki.ru/"));
                startActivity(browserIntent);
            }
        });
        ZooSfera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://zoosfera-nn.ru/"));
                startActivity(browserIntent);
            }
        });
        Sber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://online.sberbank.ru/CSAFront/index.do"));
                startActivity(browserIntent);
            }
        });
        return inflatedView;


        /*SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;*/

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}