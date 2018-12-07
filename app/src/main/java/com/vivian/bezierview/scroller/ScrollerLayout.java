package com.vivian.bezierview.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

public class ScrollerLayout extends ViewGroup {

    private int leftBorder, rightBorder;
    private int scrollUp;
    private Scroller scroller;
    private float actionDown, actionMove, actionLastMove;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollUp = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                childAt.layout(i * childAt.getMeasuredWidth(), 0, (i + 1) * childAt.getMeasuredWidth(), childAt.getMeasuredHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown = ev.getRawX();
                actionLastMove = actionDown;
                break;
            case MotionEvent.ACTION_MOVE:
                actionMove = ev.getRawX();
                actionLastMove = actionMove;
                if (Math.abs(actionLastMove - actionDown) > scrollUp) {
                    Toast.makeText(getContext(), "滑动了", Toast.LENGTH_LONG).show();
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                actionMove = event.getRawX();
                int vOffset = (int) (actionLastMove - actionMove);
                if (getScrollX() + vOffset < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                } else if (getScrollX() + vOffset + getWidth() > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                scrollBy(/*getScrollX() +*/ vOffset, 0);
                actionLastMove = actionMove;
                break;
            case MotionEvent.ACTION_UP:
                int dx = (getScrollX() + getWidth() / 2) / getWidth();
                int offset = dx * getWidth() - getScrollX();
                scroller.startScroll(getScrollX(), 0, offset, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }
}
