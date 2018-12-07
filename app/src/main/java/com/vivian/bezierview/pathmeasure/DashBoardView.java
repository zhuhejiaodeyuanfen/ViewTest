package com.vivian.bezierview.pathmeasure;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.vivian.bezierview.R;


public class DashBoardView extends AppCompatTextView {

    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    /**
     * TypedArray 是一个数组容器，在这个容器中装由 obtainStyledAttributes(AttributeSet, int[], int, int) 或者 obtainAttributes(AttributeSet, int[]) 函数获取到的属性值。
     * 用完之后记得调用 recycle() 函数回收资源。
     * 索引值用来获取 Attributes 对应的属性值（这个 Attributes 将会被传入 obtainStyledAttributes() 函数）。
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.myCustomerView, defStyleAttr, 0);
        // TypedArray typedArray = context.getTheme().obtainStyledAttributes (R.styleable.myCustomerView);

//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(R.style.MyAppTheme_2, R.styleable.myCustomerView);

        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.myCustomerView);

        String string = typedArray.getString(R.styleable.myCustomerView_text);
        int color = typedArray.getColor(R.styleable.myCustomerView_textColor, getResources().getColor(R.color.colorAccent));
        float dimension = typedArray.getDimension(R.styleable.myCustomerView_textSize, 0);
        typedArray.recycle();
        Log.i("获得的xml数值", "myCustomerView_text==" + string + "===myCustomerView_textColor==" + color + "==dimension==" + dimension);

        if (string != null)

            setText(string);
        setTextColor(color);
        setTextSize(dimension);

    }
}
