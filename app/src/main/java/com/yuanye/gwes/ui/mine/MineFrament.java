package com.yuanye.gwes.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanye.gwes.LoginActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MineFrament extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
//        return inflater.inflate(R.layout.fragment_mine, container, false);
        return null;
    }
}
