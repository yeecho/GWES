package com.yuanye.gwes;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuanye.gwes.model.RegisterModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername,edtPswd,edtPswd2,edtPhone,edtCheckCode;
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
        registerModel = ViewModelProviders.of(this).get(RegisterModel.class);
        registerModel.getBtnRegisterState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                btnRegister.setClickable(aBoolean);
            }
        });
    }

    private void initView() {
        edtUsername = findViewById(R.id.edt_user);
        edtPswd = findViewById(R.id.edt_pswd);
        edtPswd2 = findViewById(R.id.edt_pswd2);
        edtPhone = findViewById(R.id.edt_phone);
        edtCheckCode = findViewById(R.id.edt_check_code);
        btnSendCode = findViewById(R.id.btn_send_code);
        btnSendCode.setOnClickListener(this);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
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
                registerModel.setBtnRegisterState();
                break;
            case R.id.btn_register:
                break;
        }
    }
}
