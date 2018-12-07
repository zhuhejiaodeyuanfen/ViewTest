package com.vivian.bezierview.matrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.vivian.bezierview.R;

public class LevelBar extends ViewGroup {

    private int viewWidth, viewHeigth;
    private int leftRightMargin;
    private Paint paint;

    private int barHeight;

    public LevelBar(Context context) {
        this(context, null);
    }

    public LevelBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10f);

        barHeight = 30;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeigth = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeigth);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * x0表示渲染起始位置的x坐标
         y0表示渲染起始位置的y坐标
         x1表示渲染结束位置的x坐标
         y1表示渲染结束位置的y坐标
         color1表示渲染的第一个颜色
         color1表示渲染的第二个颜色
         tile表示平铺方式，有三种，我们分别来看
         */
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
                new int[]{getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimaryDark)},
                null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRoundRect(new RectF(0 + leftRightMargin, viewHeigth / 2 - barHeight / 2,
                viewWidth - leftRightMargin, viewHeigth / 2 + barHeight / 2), 20, 20, paint);
    }
}
