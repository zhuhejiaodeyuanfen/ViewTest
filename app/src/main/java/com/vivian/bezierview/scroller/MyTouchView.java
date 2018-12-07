package com.vivian.bezierview.scroller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyTouchView extends View {
    public MyTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return true;
//    }
}
