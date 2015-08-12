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

    private boolean mVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m5_main);
        mContainer  = ((FrameLayout) findViewById(R.id.container));
        changeToVisible(true);
        findViewById(R.id.left).setOnClickListener(this);
        findViewById(R.id.right).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.left:
                changeToVisible(false);
                break;
            case R.id.right:
                changeToVisible(true);
                break;
        }
    }

    private void changeToVisible(boolean visible){
        if(mVisible==visible){
            return;
        }
        mVisible = visible;
        mContainer.removeAllViews();
        View view = new M5ViewGroupPullHeader(this,mVisible?R.layout.m5_view_group_visible :R.layout.m5_view_group_hidden);
        mContainer.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }

}
