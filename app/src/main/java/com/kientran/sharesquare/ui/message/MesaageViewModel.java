package com.kientran.sharesquare.ui.message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MesaageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MesaageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Message fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}