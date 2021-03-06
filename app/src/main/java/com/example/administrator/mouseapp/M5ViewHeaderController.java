package com.example.administrator.mouseapp;

/**
 * Created by Administrator on 2015/8/10.
 */
public class M5ViewHeaderController {

    private M5IHeader.HeaderType mType;

    private int mCurrentHeight,mMinHeight,mNormalHeight,mMaxHeight,mTargetHeight, mHeaderWidth;

    public M5ViewHeaderController(M5IHeader.HeaderType type, int mMinHeight, int mNormalHeight, int mMaxHeight, int mTargetHeight) {
        this.mType = type;
        this.mMinHeight = mMinHeight;
        this.mNormalHeight = mNormalHeight;
        this.mMaxHeight = mMaxHeight;
        this.mTargetHeight = mTargetHeight;
        mCurrentHeight = getInitHeight(mType);
    }

    public M5IHeader.HeaderType getHeaderType() {
        return mType;
    }
    public int getHeaderWidth() {
        return mHeaderWidth;
    }

    public void setHeaderWidth(int headerWidth) {
        mHeaderWidth = headerWidth;
    }

    public void setCurrentHeight(int currentHeight) {
        mCurrentHeight = currentHeight;
    }

    public float getCurrentPercentage(){
        return mCurrentHeight*1.0f/mNormalHeight;
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


    private int getInitHeight(M5IHeader.HeaderType type){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return getMinHeight();
                case VISIBLE:
                    return getNormalHeight();
            }
        }
        return getMinHeight();
    }

    public int getExtraTopPadding(M5IHeader.HeaderType type){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return getNormalHeight();
                case VISIBLE:
                    return 0;
            }
        }
        return getNormalHeight();
    }

    public int getDynamicTop(M5IHeader.HeaderType type){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return mCurrentHeight - mNormalHeight;
                case VISIBLE:
                    return 0;
            }
        }
        return mCurrentHeight - mNormalHeight;
    }

    public boolean shouldIntercept(M5IHeader.HeaderType type){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return isHeaderVisible();
                case VISIBLE:
                    return mCurrentHeight > mMinHeight;
            }
        }
        return isHeaderVisible();
    }


    public int getMinRange(M5IHeader.HeaderType type , boolean isRefreshing,int originY){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return originY;
                case VISIBLE:
                    return mMinHeight;
            }
        }
        return isRefreshing?mNormalHeight:originY;
    }

    public int getMaxRange(M5IHeader.HeaderType type , boolean isRefreshing){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return isRefreshing?mNormalHeight:mMaxHeight;
                case VISIBLE:
                    return mMaxHeight;
            }
        }
        return isRefreshing?mNormalHeight:mMaxHeight;
    }

    public boolean shouldScrollBackRelease(M5IHeader.HeaderType type){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return mCurrentHeight <= mMaxHeight;
                case VISIBLE:
                    return mCurrentHeight <= mMaxHeight && mCurrentHeight >= mTargetHeight;
            }
        }
        return true;
    }

    public int getScrollBackHeight(M5IHeader.HeaderType type,boolean reachRefresh,int originY){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return reachRefresh?mNormalHeight:originY;
                case VISIBLE:
                    return mNormalHeight;
            }
        }
        return reachRefresh?mNormalHeight:originY;
    }

    public boolean interceptRelease(M5IHeader.HeaderType type,boolean isRefreshing){
        if(type != null){
            switch (type){
                case HIDDEN:
                case UNSPECIFIED:
                    return isRefreshing;
                case VISIBLE:
                    return false;
            }
        }
        return isRefreshing;
    }

    public boolean scrollBackRefresh(M5IHeader.HeaderType type){
        if(type == M5IHeader.HeaderType.VISIBLE){
            return false;
        }
        return true;
    }
}
