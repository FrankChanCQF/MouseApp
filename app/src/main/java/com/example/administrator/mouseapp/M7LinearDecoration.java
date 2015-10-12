package com.example.administrator.mouseapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/10/10.
 */
public class M7LinearDecoration extends RecyclerView.ItemDecoration {

    private int orientation = VERTICAL;

    private int dividerColor;

    private int strokeWidth = 5;

    private Paint paint;

    public final static int VERTICAL = 0;

    public final static int HORIZONTAL = 1;

    public M7LinearDecoration(Context context, int orientation, int divider) {
        this.orientation = orientation;
        this.dividerColor = context.getResources().getColor(divider);
        paint = new Paint();
        paint.setColor(dividerColor);
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public void setDividerStrokeWidth(int width) {
        strokeWidth = width;
    }

    @Override

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int top = view.getBottom();
            c.drawRect(view.getLeft(), top, view.getRight(), top + strokeWidth, paint);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int left = view.getRight();
            c.drawRect(left, view.getTop(), left + strokeWidth, view.getBottom(), paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == HORIZONTAL) {
            outRect.set(0, 0, strokeWidth, 0);
        } else {
            outRect.set(0, 0, 0, strokeWidth);
        }
    }
}
