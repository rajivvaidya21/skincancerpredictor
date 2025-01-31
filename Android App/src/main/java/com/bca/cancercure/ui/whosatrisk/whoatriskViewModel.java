package com.bca.cancercure.ui.whosatrisk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class whoatriskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public whoatriskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}