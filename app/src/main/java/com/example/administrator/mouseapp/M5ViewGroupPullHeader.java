package com.example.administrator.mouseapp;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/4.
 */
public class M5ViewGroupPullHeader extends LinearLayout implements AdapterView.OnItemClickListener{

    private M5ViewDragHelper mDragHelper;

    private View mTarget, mSupport;

    private int mOriginLeft,mOriginTop;

    private M5ViewHeaderController mHeaderController;

    private boolean isRefreshing;

    private boolean reachHeight;

    public boolean enablePullRefresh = true;

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
        /**
         * changed-0
         */
        int headerHeight = mSupport.getMeasuredHeight();
        initHeaderController(0, headerHeight, headerHeight * 4 / 3, headerHeight + 5,mSupport.getWidth());
        setPadding(0, -mHeaderController.getExtraTopPadding(getHeaderType()), 0, 0);

        mOriginLeft = mTarget.getLeft();
        mOriginTop = mTarget.getTop();
    }

    private void initHeaderController(int min,int normal,int max ,int refresh,int width){
        if(mHeaderController ==null) {
            if(mSupport instanceof M5IHeader){
                M5IHeader header = (M5IHeader) mSupport;
                if(header.getBaseHeight() > 0){
                    normal = header.getBaseHeight();
                }
                min = (int)(header.getMinHeightScale()*normal);
                max = (int)(header.getMaxHeightScale()*normal);
                refresh = (int)(header.getRefreshHeightScale()*normal);
            }
            mHeaderController = new M5ViewHeaderController(min,normal,max,refresh);
            mHeaderController.setHeaderWidth(width);
        }
    }

    protected M5IHeader.HeaderType getHeaderType(){
        if(mSupport instanceof M5IHeader){
            return ((M5IHeader) mSupport).getType();
        }
        return M5IHeader.HeaderType.UNSPECIFIED;
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
                if (invokeMoveIntercept()) {
                    mDragHelper.processTouchEvent(event);
                    //发送cancel事件，防止listview响应之前的事件，出现点击操作。
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    super.dispatchTouchEvent(event);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                mDragHelper.processTouchEvent(event);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    private boolean invokeMoveIntercept(){
        return (mHeaderController.shouldIntercept(getHeaderType()))
                ||(isOnTheTop() && mPullState == PullState.DOWN);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.m5_view_group,this);
        mTarget = findViewById(R.id.target);
        mSupport = findViewById(R.id.support);
        /**
         * changed-1
         * */
        setOrientation(VERTICAL);
        mDragHelper = M5ViewDragHelper.create(this, 2.0f, new M5ViewDragHelper.Callback() {
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
                mHeaderController.setCurrentHeight(top);
                reachHeight = mHeaderController.reachTargetHeight();
                if(mSupport instanceof M5IHeader){
                    ((M5IHeader) mSupport).dynamicLayout(mSupport,left,
                            mHeaderController.getDynamicTop(getHeaderType()) ,left + mHeaderController.getHeaderWidth(),top);
                    ((M5IHeader) mSupport).dynamicRedraw(mSupport,mHeaderController.getCurrentHeight(),mHeaderController.getCurrentPercentage());
                }else {
                    mSupport.layout(left, top - mHeaderController.getNormalHeight(),
                            left + mHeaderController.getHeaderWidth(), top);
                }
//                mSupport.layout(left, top - mHeaderController.getNormalHeight(),
//                        left + mHeaderController.getHeaderWidth(), top);
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
                return Math.max(mOriginTop, Math.min(mHeaderController.getMaxRange(getHeaderType(),isRefreshing), top));
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (isRefreshing) {
                    return;
                }
                boolean scrollBack = mHeaderController.shouldScrollBackRelease(getHeaderType());
                if(scrollBack){
                    if (mDragHelper.smoothSlideViewTo(releasedChild, mOriginLeft,
                            mHeaderController.getScrollBackHeight(getHeaderType(),reachHeight,mOriginTop))) {
                        postInvalidate();
                    }
                }
                if (reachHeight) {
                    isRefreshing = true;
                    startRefresh(scrollBack);
                }

            }


        });
        mDragHelper.setResistance(1.2f,0.4f,1.0f,1.0f);
        setupAdapter();
    }

    private void startRefresh(boolean scrollBack){
        if(scrollBack) {
            removeCallbacks(mRefreshRunnable);
            postDelayed(mRefreshRunnable, 10000);
        }else{
            //TODO
        }
    }

    private boolean scrollBackRightNow(){
        if(mSupport instanceof M5IHeader){
            ((M5IHeader) mSupport).scrollBackRightNow();
        }
        return true;
    }

    private Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            if (mDragHelper.smoothSlideViewTo(mTarget, mOriginLeft, mOriginTop)) {
                ViewCompat.postInvalidateOnAnimation(M5ViewGroupPullHeader.this);
            }
            isRefreshing = false;
        }
    };




    private void setupAdapter(){
        if(mTarget instanceof AbsListView){
            ((AbsListView) mTarget).setAdapter(
                    new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,getListData()));
            ((AbsListView) mTarget).setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("frankchan","onItemClick");
    }
}
