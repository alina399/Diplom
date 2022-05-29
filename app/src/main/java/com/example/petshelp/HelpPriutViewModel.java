package com.example.petshelp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpPriutViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HelpPriutViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
