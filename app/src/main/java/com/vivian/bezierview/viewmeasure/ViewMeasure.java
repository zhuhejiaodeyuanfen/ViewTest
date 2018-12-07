package com.vivian.bezierview.viewmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ViewMeasure extends View {
    public ViewMeasure(Context context) {
        super(context);
    }

    private int width, height;
    private int radius = 440;
    private Paint paint;
    private int startAngle, sweepAngle;

    public ViewMeasure(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        startAngle = 120;
        sweepAngle = 300;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }


    private int getMySize(int measureSpec) {
        int defaultSize = 60;
        int returnSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                //最大值  取最大值
                returnSize = size;
                break;
            case MeasureSpec.EXACTLY:
                //指定尺寸
                returnSize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                //未指定
                returnSize = defaultSize;
                break;
        }
        return returnSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(widthMeasureSpec);
        int height = getMySize(heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);


    }

    private void drawLine(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 36; i++) {
            canvas.drawLine(0, radius, 0, radius - 25, paint);
            canvas.rotate(10);
        }
        canvas.restore();
    }

    private void drawBigCircle(Canvas canvas) {
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        drawBigCircle(canvas);
        drawLine(canvas);
    }
}
