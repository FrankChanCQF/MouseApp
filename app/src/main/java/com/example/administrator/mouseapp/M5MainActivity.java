package com.example.administrator.mouseapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015/8/4.
 */
public class M5MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        M5ViewGroupOrigin view = new M5ViewGroupOrigin(this);
        M5ViewGroupPullHeader view = new M5ViewGroupPullHeader(this);
        addContentView(view,new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
