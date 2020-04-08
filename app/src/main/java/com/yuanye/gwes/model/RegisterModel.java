package com.yuanye.gwes.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterModel extends ViewModel {

    private MutableLiveData<Boolean> btnRegisterState;

    public RegisterModel(){
        btnRegisterState = new MutableLiveData<>();
        btnRegisterState.setValue(false);
    }

    public LiveData<Boolean> getBtnRegisterState(){
        return btnRegisterState;
    }

    public void setBtnRegisterState(){
        btnRegisterState.postValue(true);
    }

}
