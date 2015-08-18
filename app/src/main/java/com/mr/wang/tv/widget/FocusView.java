package com.mr.wang.tv.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Android开发第一步 自定义FocusView
 * User: chengwangyong(chengwangyong@vcinema.com)
 * Date: 2015-08-18
 * Time: 15:09
 */
public class FocusView extends ViewGroup {

    private Scroller scroller;
    private int mTouchSlop;

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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
