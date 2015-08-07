package com.example.administrator.mouseapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class M2MultiScreenActivityProto extends Activity implements OnClickListener {

	private Button bt_scrollLeft;
	private Button bt_scrollRight;
	private M2MultiViewGroupProto mulTiViewGroup  ;
	
	public static int screenWidth  ;
	public static int scrrenHeight ;
	
	private int curscreen = 0;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels ;
		scrrenHeight = metric.heightPixels;		
		System.out.println("screenWidth * scrrenHeight --->" + screenWidth + " * " +scrrenHeight);
		
		setContentView(R.layout.m2_multiview_proto);

		mulTiViewGroup = (M2MultiViewGroupProto)findViewById(R.id.mymultiViewGroup);
		
		bt_scrollLeft = (Button) findViewById(R.id.bt_scrollLeft);
		bt_scrollRight = (Button) findViewById(R.id.bt_scrollRight);

		bt_scrollLeft.setOnClickListener(this);
		bt_scrollRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.bt_scrollLeft:
			if(curscreen > 0) {
			    curscreen -- ;
			    Toast.makeText(M2MultiScreenActivityProto.this,  +(curscreen+1) + "Page", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(M2MultiScreenActivityProto.this, "已经在最左边", Toast.LENGTH_SHORT).show();
			}
			mulTiViewGroup.scrollTo(curscreen * screenWidth , 0);
			break;
		case R.id.bt_scrollRight:
			if (curscreen < 2 ){
				curscreen ++ ;
				Toast.makeText(M2MultiScreenActivityProto.this, (curscreen+1) + "Page", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(M2MultiScreenActivityProto.this, "已经在最右边", Toast.LENGTH_SHORT).show();
			}
			mulTiViewGroup.scrollTo(curscreen * screenWidth, 0);
			break;
		}
	}

}
