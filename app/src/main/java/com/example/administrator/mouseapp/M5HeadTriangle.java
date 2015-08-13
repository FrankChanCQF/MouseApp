package com.example.administrator.mouseapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/8/11.
 */
public class M5HeadTriangle extends ImageView implements M5IHeader{

    private Paint mPaint;

    private Path mPath;

    public M5HeadTriangle(Context context) {
        super(context);
        initBezierWave();
    }

    public M5HeadTriangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBezierWave();
    }

    public M5HeadTriangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initBezierWave();
    }

    private void initBezierWave(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#9370DB"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }

    private void refreshPath(int height,int width){
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.quadTo(width / 2, height*2, width, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mPath.isEmpty()){
            refreshPath(h,w);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPath.isEmpty()){
            return;
        }
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public HeaderType getType() {
        return HeaderType.VISIBLE;
    }

    @Override
    public int getBaseHeight() {
        return 0;
    }

    @Override
    public float getMaxHeightScale() {
        return 2.0f;
    }

    @Override
    public float getMinHeightScale() {
        return 0.5f;
    }

    @Override
    public float getRefreshHeightScale() {
        return 1.1f;
    }

    @Override
    public void dynamicRedraw(View view,int height,int width, float percentage,boolean refresh) {
        refreshPath(height,width);
        invalidate();
    }

    @Override
    public void dynamicLayout(View view,int left, int top, int right, int bottom) {
        this.layout(left, top, right, bottom);
    }
}
