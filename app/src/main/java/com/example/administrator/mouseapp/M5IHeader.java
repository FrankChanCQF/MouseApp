package com.example.administrator.mouseapp;

import android.view.View;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface M5IHeader {

    enum HeaderType {
        HIDDEN,VISIBLE,UNSPECIFIED
    }

    HeaderType getType();

    int getBaseHeight();

    float getMinHeightScale();

    float getMaxHeightScale();

    float getRefreshHeightScale();

    void dynamicRedraw(View view,int height,int width, float percentage);

    void dynamicLayout(View view,int left,int top,int right,int bottom);
}
