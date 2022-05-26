package com.example.petshelp.ui.petsminus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetsMinusViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PetsMinusViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}