package com.example.administrator.mouseapp;

/**
 * Created by Administrator on 2015/8/10.
 */
public class M5ViewHeaderManager {

    private int mCurrentHeight,mMinHeight,mNormalHeight,mMaxHeight,mTargetHeight;

    public M5ViewHeaderManager(int mMinHeight, int mNormalHeight, int mMaxHeight, int mTargetHeight) {
        this.mMinHeight = mMinHeight;
        this.mNormalHeight = mNormalHeight;
        this.mMaxHeight = mMaxHeight;
        this.mTargetHeight = mTargetHeight;
        mCurrentHeight = mMinHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        mCurrentHeight = currentHeight;
    }

    public int getCurrentHeight() {
        return mCurrentHeight;
    }

    public int getNormalHeight() {
        return mNormalHeight;
    }

    public int getMinHeight() {
        return mMinHeight;
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public boolean isHeaderVisible(){
        return mCurrentHeight > 0;
    }

    public boolean reachTargetHeight(){
        return mCurrentHeight>=mTargetHeight;
    }
}
