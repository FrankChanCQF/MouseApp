package com.example.administrator.mouseapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2015/8/4.
 */
public class M5MainActivity extends Activity implements View.OnClickListener{

    private FrameLayout mContainer;

    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        setContentView(R.layout.m5_main);
        mContainer  = ((FrameLayout) findViewById(R.id.container));
        changeToIndex(2);
        findViewById(R.id.one).setOnClickListener(this);
        findViewById(R.id.two).setOnClickListener(this);
        findViewById(R.id.three).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.one:
                changeToIndex(1);
                break;
            case R.id.two:
                changeToIndex(2);
                break;
            case R.id.three:
                changeToIndex(3);
                break;
        }
    }

    private void changeToIndex(int index){
        if(mIndex == index){
            return;
        }
        mIndex = index;
        mContainer.removeAllViews();
        View view = new M5ViewGroupPullHeader(this,getLayoutByIndex(mIndex));
        mContainer.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private int getLayoutByIndex(int index){
        switch (index){
            case 1:
                return R.layout.m5_view_group_hidden;
            case 2:
                return R.layout.m5_view_group_visible;
            case 3:
                return R.layout.m5_view_group_extra;
            default:
                return R.layout.m5_view_group_visible;
        }
    }

}
