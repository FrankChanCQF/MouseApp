package com.example.administrator.mouseapp;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/4.
 */
public class M5ViewGroupPullHeader extends LinearLayout {

    private M5ViewDragHelper mDragHelper;

    private View mTarget, mSupport;

    private int mDistanceX,mDistanceY,mOriginLeft,mOriginTop,mHeaderHeight,mMaxHeaderHeight,mCurrentTop;

    private boolean isRefreshing;

    private boolean reachHeight;

    public M5ViewGroupPullHeader(Context context) {
        super(context);
        init();
    }

    public M5ViewGroupPullHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public M5ViewGroupPullHeader(Context context, AttributeSet attrs, int defStyle) {
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
        /**
         * changed-0
         */
        setPadding(0, -mSupport.getMeasuredHeight(), 0, 0);
        mHeaderHeight = mSupport.getMeasuredHeight();
        mMaxHeaderHeight = mHeaderHeight*4/3;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
//            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
        }
    }


    /**
     * change-5
     */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        switch (action){
//            case MotionEvent.ACTION_CANCEL:
//                case MotionEvent.ACTION_UP:
//                    mDragHelper.cancel();
//                    return false;
//        }
//        return mDragHelper.shouldInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        mDragHelper.processTouchEvent(event);
//        return true;
//    }

    public enum PullState {
        UNSPECIFIED,UP,DOWN;

        static PullState getPullState(float factor){
            if (factor>0){
                return DOWN;
            }else if(factor<0){
                return UP;
            }
            return UNSPECIFIED;
        }
    }

    private PullState mPullState = PullState.UNSPECIFIED;

    private float mLastY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastY = event.getRawY();
                mDragHelper.processTouchEvent(event);
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                mDragHelper.processTouchEvent(event);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float currentY = event.getRawY();
                mPullState = PullState.getPullState(currentY - mLastY);
                //到顶部，并且是向下拉
                if (invokeIntercept()) {
                    mDragHelper.processTouchEvent(event);
                    //发送cancel事件，防止listview响应之前的事件，出现点击操作。
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    super.dispatchTouchEvent(event);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDragHelper.processTouchEvent(event);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean invokeIntercept(){
        return (isOnTheTop() && mPullState == PullState.DOWN)
                ||(mCurrentTop<=mMaxHeaderHeight&&mCurrentTop>0);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.m5_view_group,this);
        mTarget = findViewById(R.id.target);
        mSupport = findViewById(R.id.support);
        /**
         * changed-1
         * */
        setOrientation(VERTICAL);
        mDragHelper = M5ViewDragHelper.create(this, 1.0f, new M5ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View view, int i) {
                return mTarget == view;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                /**
                 * changed-2
                 */
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                mSupport.layout(left - mDistanceX, top - mDistanceY,
                        left - mDistanceX + mSupport.getWidth(), top - mDistanceY + mSupport.getHeight());
                reachHeight = top >= mHeaderHeight;
                mCurrentTop = top;
//                mSupport.offsetTopAndBottom(top-mSupport.getTop()-mDistanceY);
//                mSupport.offsetLeftAndRight(left-mSupport.getLeft() -mDistanceX);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                /**
                 * change-3
                 */
//                int leftBound = getPaddingLeft();
//                int rightBound = getMeasuredWidth()-getPaddingRight() - child.getWidth();
//                return Math.max(leftBound,Math.min(rightBound,left));
                return mTarget.getLeft();
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                /*int topBound = getPaddingTop();
                int bottomBound = getMeasuredHeight() - getPaddingBottom() - child.getHeight();
                return Math.max(topBound,Math.min(bottomBound,top));*/
                /**
                 * change-4
                 */
                return Math.max(mOriginTop, Math.min(isRefreshing ? mHeaderHeight : mMaxHeaderHeight, top));
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (isRefreshing) {
                    return;
                }
                if (mDragHelper.smoothSlideViewTo(releasedChild, mOriginLeft, reachHeight ? mHeaderHeight : mOriginTop)) {
//                    ViewCompat.postInvalidateOnAnimation(M5ViewGroup.this);
                    postInvalidate();
                }
                if (reachHeight) {
                    isRefreshing = true;
                    startRefresh();
                }

            }


        });
        mDragHelper.setResistance(0.6f);
        setupAdapter();
    }

    private void startRefresh(){
        removeCallbacks(mRefreshRunnable);
        postDelayed(mRefreshRunnable,10000);
    }

    private Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            if(mDragHelper.smoothSlideViewTo(mTarget,mOriginLeft,mOriginTop)){
                ViewCompat.postInvalidateOnAnimation(M5ViewGroupPullHeader.this);
            }
            isRefreshing = false;
        }
    };

    private void setupAdapter(){
        if(mTarget instanceof AbsListView){
            ((AbsListView) mTarget).setAdapter(
                    new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,getListData()));
        }
    }

    private List<String> getListData(){
        List<String>list = new ArrayList<String>();
        for (int i = 1; i < 15; i++) {
            list.add(String.valueOf(i));

        }
        return list;
    }

    private boolean isOnTheTop(){
        if(mTarget instanceof AbsListView){
            AbsListView absListView = ((AbsListView) mTarget);
            Adapter adapter = absListView.getAdapter();
            if(adapter!=null&&!adapter.isEmpty()){
                if(absListView.getFirstVisiblePosition()==0){
                    if(absListView.getChildAt(0)!=null&&absListView.getChildAt(0).getTop()>=mOriginTop){
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

}
