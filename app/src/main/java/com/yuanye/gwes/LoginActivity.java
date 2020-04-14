package com.yuanye.gwes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuanye.gwes.Constant.YC;
import com.yuanye.gwes.app.MyApp;
import com.yuanye.gwes.callback.LoginCallback;
import com.yuanye.gwes.utils.Yhttp;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "LoginActivity";

    private EditText edtUsername,edtPassword;
    private Button btnLogin, btnRegister;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("登录");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_login);
        context = this;
        initView();
    }

    private void initView() {
        edtUsername = findViewById(R.id.edt_user);
        edtPassword = findViewById(R.id.edt_pswd);
        btnLogin = findViewById(R.id.btn_login);
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
            case R.id.btn_login:
                if (checkInfo()){
                    Yhttp.login(edtUsername.getText().toString(), edtPassword.getText().toString(),
                            new LoginCallback() {
                                @Override
                                public void onSuccess(JSONObject jo) {

                                }

                                @Override
                                public void onFail(int i, String msg) {

                                }
                            });
                }

                break;
            case R.id.btn_register:
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    private boolean checkInfo() {
        boolean b = edtUsername.getText().toString().matches(YC.REGEX_USERNAME);
        boolean b1 = edtPassword.getText().toString().matches(YC.REGEX_PASSWORD_LOW);
        return b && b1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode:"+requestCode + " resultCode:"+resultCode);
        if (requestCode == YC.REGISTER_OK){
            MyApp.id = data.getStringExtra("data");
            setResult(YC.LOGIN_OK);
            finish();
        }
    }
}
