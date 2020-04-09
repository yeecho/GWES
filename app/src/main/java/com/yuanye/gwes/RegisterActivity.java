package com.yuanye.gwes;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanye.gwes.Constant.YC;
import com.yuanye.gwes.bean.RIS;
import com.yuanye.gwes.bean.Tips;
import com.yuanye.gwes.model.RegisterModel;
import com.yuanye.gwes.utils.YY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText edtUsername,edtPswd,edtPswd2,edtPhone,edtCheckCode;
    private TextView txtUsername,txtPswd,txtPswd2,txtPhone,txtCheckCode;
    private ImageView imgPswdShow, imgPswdShow2;
    private Button btnSendCode, btnRegister;

    RegisterModel registerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_register);
        initView();
        initListener();
        initModel();
    }

    private void initModel() {
        registerModel = ViewModelProviders.of(this).get(RegisterModel.class);
        registerModel.getValidInfo().observe(this, new Observer<RIS>() {
            @Override
            public void onChanged(RIS ris) {
                Log.d("onChanged:","user:"+ris.isbUser()+" pswd:"+ris.isbPswd()+" pswd2:"+ris.isbPswd2()+" phone:"+ris.isbPhone());
                btnRegister.setClickable(ris.getState());
            }
        });
        registerModel.getUserTips().observe(this, new Observer<Tips>() {
            @Override
            public void onChanged(Tips tips) {
                txtUsername.setText(tips.getContent());
                txtUsername.setTextColor(tips.getColor());
            }
        });
    }

    private void initListener() {
        edtUsername.setOnFocusChangeListener(this);
        edtPswd.setOnFocusChangeListener(this);
        edtPswd2.setOnFocusChangeListener(this);
        edtPhone.setOnFocusChangeListener(this);

        imgPswdShow.setOnClickListener(this);
        imgPswdShow2.setOnClickListener(this);

        btnSendCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void initView() {
        edtUsername = findViewById(R.id.edt_user);
        edtPswd = findViewById(R.id.edt_pswd);
        edtPswd2 = findViewById(R.id.edt_pswd2);
        edtPhone = findViewById(R.id.edt_phone);
        edtCheckCode = findViewById(R.id.edt_check_code);
        txtUsername = findViewById(R.id.txt_user_tips);
        txtPswd = findViewById(R.id.txt_pswd_tips);
        txtPswd2 = findViewById(R.id.txt_pswd_tips2);
        txtPhone = findViewById(R.id.txt_phone_tips);
        txtCheckCode = findViewById(R.id.txt_code_tips);
        imgPswdShow = findViewById(R.id.img_pswd_show);
        imgPswdShow2 = findViewById(R.id.img_pswd_show2);
        btnSendCode = findViewById(R.id.btn_send_code);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_code:
//                registerModel.setBtnRegisterState();
                break;
            case R.id.btn_register:
                break;
            case R.id.img_pswd_show:
                YY.changeInputType(edtPswd);
                break;
            case R.id.img_pswd_show2:
                YY.changeInputType(edtPswd2);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()){
            case R.id.edt_user:
                if (!hasFocus){
                    boolean b = edtUsername.getText().toString().matches(YC.REGEX_USERNAME);
                    Tips tips = new Tips();
                    if (b){
                        tips.setContent("");
                    }else{
                        tips.setContent("格式或者长度有误").setColor(Color.RED);
                    }
                    registerModel.setValidInfo(0, b);
                    registerModel.getUserTips().postValue(tips);
                }
                break;
            case R.id.edt_pswd:
                if (!hasFocus){
                    boolean b = edtPswd.getText().toString().matches(YC.REGEX_PASSWORD_LOW);
                    if (b){
                        txtPswd.setText("");
                    }else{
                        txtPswd.setText("格式不对");
                        txtPswd.setTextColor(Color.RED);
                    }
                    registerModel.setValidInfo(1, b);
                }
                break;
            case R.id.edt_pswd2:
                if(!hasFocus){
                    boolean b = edtPswd.getText().toString().equals(edtPswd2.getText().toString());
                    if (b){
                        txtPswd2.setText("");
                        txtPswd2.setTextColor(Color.BLUE);
                    }else{
                        txtPswd2.setText("密码不一致");
                        txtPswd2.setTextColor(Color.RED);
                    }
                    registerModel.setValidInfo(2, b);
                }
                break;
            case R.id.edt_phone:
                if(!hasFocus){
                    boolean b = edtPhone.getText().toString().matches(YC.REGEX_PHONE);
                    if (b){
                        txtPhone.setText("");
                    }else{
                        txtPhone.setText("格式有误");
                        txtPhone.setTextColor(Color.RED);
                    }
                    registerModel.setValidInfo(3, b);
                }
                break;
        }

    }

    class TW1 implements TextWatcher {

        Tips tips = new Tips();

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("onTextChanged", "CharSequence:"+s+" start:"+start+" before:"+before);
//            if(!s.toString().matches("[^a-zA-Z0-9]")){
            if(!s.toString().matches("^[a-zA-Z0-9_-]{4,16}$")){
                Log.d("tag", "格式匹配"+s.toString().matches("[^a-zA-Z0-9]"));
                tips.setContent("格式不对，只允许字母和数字");
                tips.setColor(Color.RED);
            }else{
                if (s.length()<4){
                    tips.setContent("用户名太短了，至少4位");
                    tips.setColor(Color.DKGRAY);
                }else if(s.length()>24){
                    tips.setContent("你的太长了");
                    tips.setColor(Color.DKGRAY);
                }else{
                    tips.setContent("长度合格");
                    tips.setColor(Color.BLUE);
                }
            }
            registerModel.getUserTips().postValue(tips);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
