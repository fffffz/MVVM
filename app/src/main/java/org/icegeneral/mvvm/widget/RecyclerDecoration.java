package org.icegeneral.mvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.icegeneral.mvvm.utils.DensityUtils;

/**
 * Created by jianjun.lin on 2016/11/20.
 */

public class RecyclerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Context mContext;
    //    private Drawable mDivider;
    private Paint mPaint;
    private int mDividerHeight;

    private int mHeaderCount = 0;
    private int mFooterCount = 0;
    private int mDividerColor;
    private int mMarginLeft = 0;
    private int mMarginRight = 0;

    private RecyclerDecoration(Context context) {
        mContext = context;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
//        mDivider = a.getDrawable(0);
        a.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDividerColor = context.getResources().getColor(android.R.color.background_light);
        mPaint.setStyle(Paint.Style.FILL);
        mDividerHeight = DensityUtils.dp2px(context, 1);
    }

    public static class Builder {
        private RecyclerDecoration mRecyclerDecoration;

        public Builder(Context context) {
            mRecyclerDecoration = new RecyclerDecoration(context);
        }

        public Builder headerCount(int headerCount) {
            mRecyclerDecoration.mHeaderCount = headerCount;
            return this;
        }

        public Builder footerCount(int footerCount) {
            mRecyclerDecoration.mFooterCount = footerCount;
            return this;
        }

        public Builder dividerColor(int dividerColor) {
            mRecyclerDecoration.mDividerColor = dividerColor;
            return this;
        }

        public Builder marginLeft(int marginLeft) {
            mRecyclerDecoration.mMarginLeft = marginLeft;
            return this;
        }

        public Builder marginRight(int marginRight) {
            mRecyclerDecoration.mMarginRight = marginRight;
            return this;
        }

        public RecyclerDecoration build() {
            return mRecyclerDecoration;
        }
    }

    private int getOrientation(RecyclerView parent) {
        LinearLayoutManager layoutManager;
        try {
            layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        } catch (ClassCastException e) {
            throw new IllegalStateException("RecyclerDecoration can only be used with a LinearLayoutManager.", e);
        }
        return layoutManager.getOrientation();
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        mPaint.setColor(mDividerColor);
        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            drawHorizontal(canvas, parent);
        } else {
            drawVertical(canvas, parent);
        }
    }

    public void drawVertical(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = mHeaderCount; i < childSize - mFooterCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;
//            if (mDivider != null) {
//                mDivider.setBounds(left, top, right, bottom);
//                mDivider.draw(canvas);
//            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    public void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + mMarginLeft;
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight() - mMarginRight;
        final int childSize = parent.getChildCount();
        for (int i = mHeaderCount; i < childSize - mFooterCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
//            if (mDivider != null) {
//                mDivider.setBounds(left, top, right, bottom);
//                mDivider.draw(canvas);
//            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }
}