package com.yuanye.gwes.bean;

import android.graphics.Color;

public class Tips {

    String content = "";
    int color = Color.BLACK;
    int size = 12;

    public Tips() {
    }

    public Tips(String content, int color) {
        this.content = content;
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public Tips setContent(String content) {
        this.content = content;
        return this;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
