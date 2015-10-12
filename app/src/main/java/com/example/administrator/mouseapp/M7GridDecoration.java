package com.example.administrator.mouseapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Administrator on 2015/10/10.
 */
public class M7GridDecoration extends RecyclerView.ItemDecoration {

    private int dividerColor;

    private int strokeWidth = 10;

    private Paint paint;

    public M7GridDecoration(Context context, int divider) {
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
        drawHorizontal(c, parent);
        drawVertical(c, parent);
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
            //draw more at right side so as to cover the corner
            c.drawRect(left, view.getTop(), left + strokeWidth, view.getBottom() + strokeWidth, paint);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        int right = isLastColumn(parent, itemPosition) ? 0 : strokeWidth;
        int bottom = isLastRaw(parent, itemPosition) ? 0 : strokeWidth;
        outRect.set(0, 0, right, bottom);
//        outRect.set(0,0,strokeWidth,strokeWidth);

    }

    private int getColumnCount(RecyclerView parent) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            return ((GridLayoutManager) manager).getSpanCount();
        } else if (manager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) manager).getSpanCount();
        } else {
            return 0;
        }
    }

    private boolean isLastColumn(RecyclerView parent, int position) {
        int sum = parent.getAdapter().getItemCount();
        int column = getColumnCount(parent);
        int pos = position + 1;
        if (column != 0) {
            int remain = sum % column;
            if (remain == 0) {
                return pos % column == 0;
            } else {
                return pos == sum;
            }
        }
        return true;
    }

    private boolean isLastRaw(RecyclerView parent, int position) {
        int sum = parent.getAdapter().getItemCount();
        int column = getColumnCount(parent);
        int pos = position + 1;
        return column == 0 || pos > sum - column;
    }

}
