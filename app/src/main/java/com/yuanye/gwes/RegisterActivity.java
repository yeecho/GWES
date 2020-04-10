package com.yuanye.gwes;

import android.content.Intent;
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
import android.widget.Toast;

import com.yuanye.gwes.Constant.YC;
import com.yuanye.gwes.app.MyApp;
import com.yuanye.gwes.bean.RIS;
import com.yuanye.gwes.bean.Tips;
import com.yuanye.gwes.callback.RegisterCallback;
import com.yuanye.gwes.model.RegisterModel;
import com.yuanye.gwes.utils.YY;
import com.yuanye.gwes.utils.Yhttp;

import org.json.JSONException;
import org.json.JSONObject;

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
//                btnRegister.setClickable(ris.getState());
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
                if (checkInfo()){
                    Yhttp.register(edtUsername.getText().toString(), edtPswd.getText().toString(), edtPhone.getText().toString(), new RegisterCallback() {
                        @Override
                        public void onResponse(JSONObject jo) {
                            Log.d("register-ok", "注册成功："+jo.toString());
                            try {
                                String id = jo.getString("id");
                                Intent intent = new Intent();
                                intent.putExtra("id", id);
                                setResult(YC.REGISTER_OK, intent);
                                RegisterActivity.this.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFail(int code, String msg) {
                            Log.d("register-fail", "错误码："+code+" msg："+msg);
//                            Toast.makeText(RegisterActivity.this, "错误码："+code+" msg"+msg, Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(this, "注册信息有误", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_pswd_show:
                YY.changeInputType(edtPswd);
                break;
            case R.id.img_pswd_show2:
                YY.changeInputType(edtPswd2);
                break;
        }
    }

    private boolean checkInfo() {
        boolean b1 = edtUsername.getText().toString().matches(YC.REGEX_USERNAME);
        if (b1){
            txtUsername.setText("");
        }else{
            txtUsername.setText("格式不对");
            txtPswd.setTextColor(Color.RED);
        }
        boolean b2 = edtPswd.getText().toString().matches(YC.REGEX_PASSWORD_LOW);
        if (b2){
            txtPswd.setText("");
        }else{
            txtPswd.setText("格式不对");
            txtPswd.setTextColor(Color.RED);
        }
        boolean b3 = edtPswd.getText().toString().equals(edtPswd2.getText().toString());
        if (b3){
            txtPswd2.setText("");
            txtPswd2.setTextColor(Color.BLUE);
        }else{
            txtPswd2.setText("密码不一致");
            txtPswd2.setTextColor(Color.RED);
        }
        boolean b4 = edtPhone.getText().toString().matches(YC.REGEX_PHONE);
        if (b4){
            txtPhone.setText("");
        }else{
            txtPhone.setText("格式有误");
            txtPhone.setTextColor(Color.RED);
        }
        return b1 & b2 & b3 & b4;
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

}
