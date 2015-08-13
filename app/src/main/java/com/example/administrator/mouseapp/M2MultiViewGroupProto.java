package com.example.administrator.mouseapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class M2MultiViewGroupProto extends ViewGroup {

	private Context mContext;

	private static String TAG = "MultiViewGroup";

	public M2MultiViewGroupProto(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public M2MultiViewGroupProto(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	private void init() {
		LinearLayout oneLL = new LinearLayout(mContext);
		oneLL.setBackgroundColor(Color.RED);
        addView(oneLL);
		
		LinearLayout twoLL = new LinearLayout(mContext);
		twoLL.setBackgroundColor(Color.YELLOW);
		addView(twoLL);
		
		LinearLayout threeLL = new LinearLayout(mContext);
		threeLL.setBackgroundColor(Color.BLUE);
		addView(threeLL);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		Log.i(TAG, "--- start onMeasure --");

		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width, height);

		int childCount = getChildCount();
		Log.i(TAG, "--- onMeasure childCount is -->" + childCount);
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			child.measure(M2MultiScreenActivityProto.screenWidth, M2MultiScreenActivityProto.screenHeight);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		Log.i(TAG, "--- start onLayout --");
		int startLeft = 0;
		int startTop = 100;
		int childCount = getChildCount();
		Log.i(TAG, "--- onLayout childCount is -->" + childCount);
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			child.layout(startLeft, startTop, 
					startLeft + M2MultiScreenActivityProto.screenWidth,
					startTop + M2MultiScreenActivityProto.screenHeight);
			startLeft = startLeft + M2MultiScreenActivityProto.screenWidth ;
		}
	}

}
