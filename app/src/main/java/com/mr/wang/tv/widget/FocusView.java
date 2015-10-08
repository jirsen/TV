package com.mr.wang.tv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.mr.wang.tv.model.FocusItemModle;

import java.util.ArrayList;

/**
 * Android开发第一步 自定义FocusView
 * User: chengwangyong(chengwangyong@vcinema.com)
 * Date: 2015-08-18
 * Time: 15:09
 */
public class FocusView extends ViewGroup {
    /**
     * 横向滚动的Scroller
     */
    private Scroller scroller;
    /**
     * 最小滑动距离
     */
    private int mTouchSlop;
    /**
     * 竖直方向的行数
     */
    private int visibleRows;
    /**
     * 竖直方向的间隔
     */
    private int mGapHeight;
    /**
     * 行高
     */
    private int mRowHeight;
    /**
     * 列宽
     */
    private int mColWidth;
    /**
     * 水平方向的列数
     */
    private int visibleCols;
    /**
     * 列间隔宽度
     */
    private int mGapWidth;
    /**
     * 焦点的View集合
     */
    protected ArrayList<FocusItemModle> mFocusItems = new ArrayList<FocusItemModle>();

    public FocusView(Context context) {
        super(context);
        initViewGroup(context);
    }


    public FocusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewGroup(context);
    }

    public FocusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewGroup(context);
    }

    /**
     * 初始化focusView
     */
    private void initViewGroup(Context context) {
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //计算行高   整体高度-（行数-1）*行间隔=实际占的高度   （实际占的高度-上下间距）/行数=每行的高度
        mRowHeight = (height - (visibleRows - 1) * mGapHeight - getPaddingTop() - getPaddingBottom()) / visibleRows;
        //同理 计算列宽  整体宽度-（列数-1）*行间隔=实际列宽度   （实际列宽度-上下间隔）/列数=每列的宽度
        mColWidth = (width - (visibleCols - 1) * mGapWidth - getPaddingLeft() - getPaddingRight()) / visibleCols;

        //获取全部的item
        int itemCount = mFocusItems.size();
        for (int i = 0; i < itemCount; i++) {
            FocusItemModle item = mFocusItems.get(i);
            View childView  = item.getFocusView();

            final int childWidth = MeasureSpec.makeMeasureSpec((mColWidth + mGapWidth) * item.getColSpan() - mGapWidth, MeasureSpec.EXACTLY);
            final int childHeight = MeasureSpec.makeMeasureSpec(
                    (mRowHeight + mGapHeight) * item.getRowSpan() - mGapHeight, MeasureSpec.EXACTLY);

            item.setmWigth(childWidth);
            item.setmHight(childHeight);
            childView.measure(childWidth, childHeight);
        }

        //scrollTo((mColWidth + mGapWidth) * mCurCol, (mRowHeight + mGapHeight) * mCurRow);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
