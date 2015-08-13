package com.example.administrator.mouseapp;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/8/11.
 */
public class M5HeaderHide extends TextView implements M5IHeader{

    private String strCurrent ,strNormal = "局长别开枪是我",strPulling = "哎呀中枪了",strRefreshing = "要死了要死了";

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
        setTextColor(Color.parseColor("#3C929C"));
        setGravity(Gravity.CENTER);
        setTextSize(30);
        setCurrentText(strNormal);
    }

    private void setCurrentText(String text){
        if(!TextUtils.equals(strCurrent,text)){
            strCurrent = text;
            setText(strCurrent);
        }
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
    public void dynamicRedraw(View view,int height,int width, float percentage,boolean refresh) {
        if(percentage == 0){
            if(!refresh){
                setCurrentText(strNormal);
            }
        }else if(percentage ==1){
            if(refresh) {
                setCurrentText(strRefreshing);
            }
        }else if(percentage >= getRefreshHeightScale()){
            if(!refresh) {
                setCurrentText(strPulling);
            }
        }
    }
    @Override
    public void dynamicLayout(View view,int left, int top, int right, int bottom) {
        this.layout(left, bottom - getMeasuredHeight(), right, bottom);
    }
}
