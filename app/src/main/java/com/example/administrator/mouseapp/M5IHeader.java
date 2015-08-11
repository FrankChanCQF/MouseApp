package com.example.administrator.mouseapp;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface M5IHeader {

    enum HeaderType {
        HIDDEN,VISIBLE, UNSPECIFIED
    }

    HeaderType getType();

    int getBaseHeight();

    float getMinHeightScale();

    float getMaxHeightScale();

    float getTargetHeightScale();

    void dynamicRedraw(int height, float percentage);

    void dynamicLayout(int left,int top,int right,int bottom);
}
