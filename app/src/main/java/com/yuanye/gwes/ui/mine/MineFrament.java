package com.yuanye.gwes.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanye.gwes.Constant.YC;
import com.yuanye.gwes.LoginActivity;
import com.yuanye.gwes.R;
import com.yuanye.gwes.app.MyApp;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MineFrament extends Fragment {

    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (MyApp.id.equals("")){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivityForResult(intent, 100);
        }
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        textView = view.findViewById(R.id.text_home);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "requestCode:"+requestCode + " resultCode:"+resultCode);
        if (requestCode==100 && resultCode == YC.LOGIN_OK){
            textView.setText("登录成功"+" id:"+MyApp.id);
        }else{
            textView.setText("未登录");
        }
    }
}
