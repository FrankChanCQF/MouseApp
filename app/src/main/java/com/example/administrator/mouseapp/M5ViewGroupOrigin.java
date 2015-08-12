package com.example.administrator.mouseapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/8/4.
 */
public class M5ViewGroupOrigin extends LinearLayout {

    private ViewDragHelper mDragHelper;

    private View mTarget, mSupport;

    private int mDistanceX,mDistanceY,mOriginLeft,mOriginTop;

    public M5ViewGroupOrigin(Context context) {
        super(context);
        init();
    }

    public M5ViewGroupOrigin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public M5ViewGroupOrigin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mOriginLeft = mTarget.getLeft();
        mOriginTop = mTarget.getTop();
        mDistanceY = mTarget.getTop() - mSupport.getTop();
        mDistanceX = mTarget.getLeft() - mSupport.getLeft();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
//            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mDragHelper.cancel();
                    return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }



    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.m5_view_group_hidden,this);
        mTarget = findViewById(R.id.target);
        mSupport = findViewById(R.id.support);
        setPadding(50,50,50,50);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View view, int i) {
                return mTarget == view;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
//                mSupport.layout(left-mDistanceX,top-mDistanceY,
//                        left-mDistanceX+mSupport.getWidth(),top-mDistanceY+mSupport.getHeight());
                mSupport.offsetTopAndBottom(top-mSupport.getTop()-mDistanceY);
                mSupport.offsetLeftAndRight(left-mSupport.getLeft() -mDistanceX);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBound = getPaddingLeft();
                int rightBound = getMeasuredWidth()-getPaddingRight() - child.getWidth();
                return Math.max(leftBound,Math.min(rightBound,left));
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int topBound = getPaddingTop();
                int bottomBound = getMeasuredHeight() - getPaddingBottom() - child.getHeight();
                return Math.max(topBound,Math.min(bottomBound,top));
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if(mDragHelper.smoothSlideViewTo(releasedChild, mOriginLeft, mOriginTop)){
//                    ViewCompat.postInvalidateOnAnimation(M5ViewGroup.this);
                    postInvalidate();
                }
            }


        });

    }

}
