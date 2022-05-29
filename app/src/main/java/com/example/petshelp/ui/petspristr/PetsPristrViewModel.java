package com.example.petshelp.ui.petspristr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetsPristrViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PetsPristrViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}