package com.bca.cancercure.ui.detection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetectionModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DetectionModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Choose an image for detection");
    }

    public LiveData<String> getText() {
        return mText;
    }
}