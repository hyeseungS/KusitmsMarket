package com.kusitms.kusitmsmarket.ui.price;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PriceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PriceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is price fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}