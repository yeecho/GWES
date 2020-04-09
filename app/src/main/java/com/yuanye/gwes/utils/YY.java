package com.yuanye.gwes.utils;

import android.text.InputType;
import android.widget.EditText;

public class YY {

    public static void changeInputType(EditText password){
        if (password.getInputType() == InputType.TYPE_CLASS_TEXT) {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            password.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }
}
