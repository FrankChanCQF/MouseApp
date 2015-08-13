package com.example.administrator.mouseapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author http://http://blog.csdn.net/qinjuning
 */
public class M2MultiScreenActivityUltra extends Activity implements OnClickListener {

	private Button bt_scrollLeft;
	private Button bt_scrollRight;
	private M2MultiViewGroupUltra mulTiViewGroup  ;
	
	public static int screenWidth  ;
	public static int screenHeight;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels ;
		screenHeight = metric.heightPixels;
		
		setContentView(R.layout.m2_multiview_ultra);

		mulTiViewGroup = (M2MultiViewGroupUltra)findViewById(R.id.mymultiViewGroup);
		
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
			mulTiViewGroup.startMove() ;
			break;
		case R.id.bt_scrollRight:
			mulTiViewGroup.stopMove() ;
			break;
		}
	}

}
