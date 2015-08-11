package com.example.administrator.mouseapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/8/11.
 */
public class M5HeaderHide extends TextView implements M5IHeader{

    public M5HeaderHide(Context context) {
        super(context);
        initTextHeader();
    }

    public M5HeaderHide(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTextHeader();
    }

    public M5HeaderHide(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTextHeader();
    }

    private void initTextHeader(){
        setTextColor(Color.parseColor("#222222"));
        setGravity(Gravity.CENTER);
        setTextSize(50);
        setText("TXT");
    }

    @Override
    public boolean scrollBackRightNow() {
        return true;
    }

    @Override
    public boolean shouldScrollUpBack() {
        return false;
    }

    @Override
    public boolean shouldScrollDownBack() {
        return true;
    }

    @Override
    public HeaderType getType() {
        return HeaderType.HIDDEN;
    }

    @Override
    public int getBaseHeight() {
        return 0;
    }

    @Override
    public float getMaxHeightScale() {
        return 2.0f;
    }

    @Override
    public float getMinHeightScale() {
        return 0;
    }

    @Override
    public float getRefreshHeightScale() {
        return 1.1f;
    }

    @Override
    public void dynamicRedraw(View view,int height, float percentage) {
        Log.e("frankchan",String.format("percentage:%f",percentage));
    }

    @Override
    public void dynamicLayout(View view,int left, int top, int right, int bottom) {
        view.layout(left, top, right, bottom);
    }
}
