package androidtvwidget.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.mr.wang.tv.R;

/**
 * 平滑移动的水平ScrollView
 */
public class SmoothHorizontalScrollView extends HorizontalScrollView {
    final String TAG = "SmoothHorizontalScrollView";

    public SmoothHorizontalScrollView(Context context) {
        this(context, null, 0);
    }

    public SmoothHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothHorizontalScrollView(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 计算X方向滚动的总合，以便在屏幕上显示子视图的完整矩形（或者，若矩形宽度超过屏幕宽度，至少要填满第一个屏幕大小）。
     * @param rect 矩形
     * @return 返回值
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0)
            return 0;

        int width = getWidth();
        int screenLeft = getScrollX();
        int screenRight = screenLeft + width;

        int fadingEdge = this.getResources().getDimensionPixelSize(
                R.dimen.fading_edge);

        // leave room for left fading edge as long as rect isn't at very left
        if (rect.left > 0) {
            screenLeft += fadingEdge;
        }

        // leave room for right fading edge as long as rect isn't at very right
        if (rect.right < getChildAt(0).getWidth()) {
            screenRight -= fadingEdge;
        }

        int scrollXDelta = 0;

        if (rect.right > screenRight && rect.left > screenLeft) {
            // need to move right to get it in view: move right just enough so
            // that the entire rectangle is in view (or at least the first
            // screen size chunk).

            if (rect.width() > width) {
                // just enough to get screen size chunk on
                scrollXDelta += (rect.left - screenLeft);
            } else {
                // get entire rect at right of screen
                scrollXDelta += (rect.right - screenRight);
            }

            // make sure we aren't scrolling beyond the end of our content
            int right = getChildAt(0).getRight();
            int distanceToRight = right - screenRight;
            scrollXDelta = Math.min(scrollXDelta, distanceToRight);

        } else if (rect.left < screenLeft && rect.right < screenRight) {
            // need to move right to get it in view: move right just enough so
            // that
            // entire rectangle is in view (or at least the first screen
            // size chunk of it).

            if (rect.width() > width) {
                // screen size chunk
                scrollXDelta -= (screenRight - rect.right);
            } else {
                // entire rect at left
                scrollXDelta -= (screenLeft - rect.left);
            }

            // make sure we aren't scrolling any further than the left our
            // content
            scrollXDelta = Math.max(scrollXDelta, -getScrollX());
        }
        return scrollXDelta;
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction,
                                                  Rect previouslyFocusedRect) {

        // convert from forward / backward notation to up / down / left / right
        // (ugh).

        if (previouslyFocusedRect != null) {
            if (direction == View.FOCUS_FORWARD) {
                direction = View.FOCUS_RIGHT;
            } else if (direction == View.FOCUS_BACKWARD) {
                direction = View.FOCUS_LEFT;
            }
            View nextFocus = FocusFinder.getInstance().findNextFocusFromRect(
                    this, previouslyFocusedRect, direction);
            if (nextFocus == null) {
                return false;
            }
            return nextFocus.requestFocus(direction, previouslyFocusedRect);
        } else {
            int index;
            int increment;
            int end;
            int count = this.getChildCount();
            if ((direction & FOCUS_FORWARD) != 0) {
                index = 0;
                increment = 1;
                end = count;
            } else {
                index = count - 1;
                increment = -1;
                end = -1;
            }
            for (int i = index; i != end; i += increment) {
                View child = this.getChildAt(i);
                if (child.getVisibility() == View.VISIBLE) {
                    if (child.requestFocus(direction, previouslyFocusedRect)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
