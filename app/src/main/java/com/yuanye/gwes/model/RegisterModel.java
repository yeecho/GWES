package com.yuanye.gwes.model;

import android.graphics.Color;

import com.yuanye.gwes.bean.RIS;
import com.yuanye.gwes.bean.Tips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterModel extends ViewModel {

    private MutableLiveData<RIS> validInfo;


    private MutableLiveData<Tips> userTips;
    private MutableLiveData<String> registerTips;

    private RIS ris;

    public RegisterModel(){
        validInfo = new MutableLiveData<>();
        ris = new RIS();
        validInfo.postValue(ris);
        userTips = new MutableLiveData<>();
        userTips.postValue(new Tips("", Color.BLACK));
        registerTips = new MutableLiveData<>();
    }

    public MutableLiveData<Tips> getUserTips() {
        return userTips;
    }

    public LiveData<RIS> getValidInfo(){
        return validInfo;
    }

    public MutableLiveData<String> getRegisterTips() {
        return registerTips;
    }

    public void setValidInfo(int index, boolean b){
        switch (index){
            case 0:
                ris.setbUser(b);
                break;
            case 1:
                ris.setbPswd(b);
                break;
            case 2:
                ris.setbPswd2(b);
                break;
            case 3:
                ris.setbPhone(b);
                break;
        }
        validInfo.postValue(ris);
    }

}
