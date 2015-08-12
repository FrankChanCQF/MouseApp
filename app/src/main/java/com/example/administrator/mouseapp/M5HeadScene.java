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
public class M5HeadScene extends ImageView implements M5IHeader{

    private Paint mPaint1,mPaint2,mPaint3 , mPaint;

    private Path mPath1, mPath2 , mPath3;

    private int mPivotX, mPivotY,mRadius;

    private int mSunSize = 50;

    public M5HeadScene(Context context) {
        super(context);
        initSineWave();
    }

    public M5HeadScene(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSineWave();
    }

    public M5HeadScene(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSineWave();
    }

    private void initSineWave(){
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setColor(Color.parseColor("#86DAD7"));
        mPaint1.setStyle(Paint.Style.FILL);
        mPath1 = new Path();

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.parseColor("#3C929C"));
        mPaint2.setStyle(Paint.Style.FILL);
        mPath2 = new Path();

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setColor(Color.parseColor("#3E5F73"));
        mPaint3.setStyle(Paint.Style.FILL);
        mPath3 = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#FFBEB916"));
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void refreshAllPath(int height, int width ,float percentage){
        refreshPath1(height, width);
        refreshPath2(height, width);
        refreshPath3(height, width);
        mPivotX = (int)(width *(0.28f-0.08f * percentage/getMaxHeightScale()));
        mPivotY = (int)(height *(0.75f- 0.5f * percentage/getMaxHeightScale()));
        mRadius = (int)(mSunSize * (0.8f + 1.0f * percentage/getMaxHeightScale()));
    }

    private void refreshPath1(int height,int width){
        mPath1.reset();
        mPath1.moveTo(0, height * 4 / 5 + 10);
        mPath1.lineTo(width * 3 / 7 , height*2/5);
        mPath1.lineTo(width * 3 / 5, height*3/4 - 10);
        mPath1.lineTo(width * 4 / 5, height *2/5 - 50);
        mPath1.lineTo(width, height * 3 / 4 - 50);
        mPath1.lineTo(width, height);
        mPath1.lineTo(0, height);
        mPath1.close();
    }

    private void refreshPath2(int height,int width){
        mPath2.reset();
        mPath2.moveTo(0, height * 4 / 5);
        mPath2.lineTo(width * 3 / 7, height / 2);
        mPath2.lineTo(width * 3 / 5, height * 4/5);
        mPath2.lineTo(width * 4 / 5, height / 2 - 20);
        mPath2.lineTo(width, height * 3 / 4 - 20);
        mPath2.lineTo(width, height);
        mPath2.lineTo(0, height);
        mPath2.close();
    }

    private void refreshPath3(int height,int width){
        mPath3.reset();
        mPath3.moveTo(0,height*9/10);
        mPath3.quadTo(width*3/5+20,height *3/4- 40,width,height*4/5);
        mPath3.lineTo(width, height);
        mPath3.lineTo(0, height);
        mPath3.close();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mPath1.isEmpty()||mPath2.isEmpty()||mPath3.isEmpty()){
            refreshAllPath(h, w ,1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPath2.isEmpty()){
            return;
        }
        canvas.drawCircle(mPivotX,mPivotY,mRadius, mPaint);
        canvas.drawPath(mPath1, mPaint1);
        canvas.drawPath(mPath2, mPaint2);
        canvas.drawPath(mPath3,mPaint3);
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
        return 1.6f;
    }

    @Override
    public float getMinHeightScale() {
        return 0.6f;
    }

    @Override
    public float getRefreshHeightScale() {
        return 1.1f;
    }

    @Override
    public void dynamicRedraw(View view,int height,int width, float percentage) {
        refreshAllPath(height, width, percentage);
        invalidate();
    }

    @Override
    public void dynamicLayout(View view,int left, int top, int right, int bottom) {
        this.layout(left, top, right, bottom);
    }
}
