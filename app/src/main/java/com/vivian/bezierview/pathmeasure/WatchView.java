package com.vivian.bezierview.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class WatchView extends View {
    private Paint paint, hourPaint, minutePaint, secondPaint;
    private int watchRadius = 400, pointLength;

    private String[] mTimes = {"XII", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ", "Ⅶ", "Ⅷ", "Ⅸ", "Ⅹ", "XI"};


    public WatchView(Context context) {
        this(context, null);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(40);

        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setColor(Color.RED);
        hourPaint.setStrokeWidth(10);
        hourPaint.setStyle(Paint.Style.FILL);

        minutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minutePaint.setColor(Color.GREEN);
        minutePaint.setStrokeWidth(5);
        minutePaint.setStyle(Paint.Style.FILL);

        secondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondPaint.setColor(Color.BLACK);
        secondPaint.setStrokeWidth(3);
        secondPaint.setStyle(Paint.Style.STROKE);

        pointLength = watchRadius / 6;


    }

    private void calculate() {
        Calendar instance = Calendar.getInstance();
        hour = instance.get(Calendar.HOUR_OF_DAY);
        minute = instance.get(Calendar.MINUTE);
        second = instance.get(Calendar.SECOND);
    }


    private int hour, minute, second;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wrapContentSize = 1000;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.UNSPECIFIED && heightMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(wrapContentSize, wrapContentSize);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(0, 0, watchRadius, paint);
        paintScale(canvas);

        postInvalidateDelayed(1000);
    }

    private void paintScale(Canvas canvas) {
        int lineLength;
        canvas.save();
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                lineLength = 10;
                paint.setStrokeWidth(1.5f);
                paint.setColor(Color.BLACK);
                canvas.drawLine(0, -watchRadius + 50, 0, -watchRadius + 10 + lineLength, paint);

                int mTimeTextSize=90;
                canvas.drawText(mTimes[i/5], 0,-watchRadius+10 + lineLength+mTimeTextSize,paint);//整点的位置标上整点时间数字
            } else {
                lineLength = 30;
                paint.setStrokeWidth(1.5f);
                paint.setColor(Color.BLACK);
                canvas.drawLine(0, -watchRadius + 50, 0, -watchRadius + 10 + lineLength, paint);

            }
            canvas.rotate(6);
        }
        canvas.restore();

        calculate();
        canvas.save();
        canvas.rotate(hour * 30);
        canvas.drawLine(0, 0, 0, -150, hourPaint);
        canvas.drawLine(0, 0, 0, pointLength, hourPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(minute * 6);
        canvas.drawLine(0, 0, 0, -250, minutePaint);
        canvas.drawLine(0, 0, 0, pointLength, minutePaint);
        canvas.restore();

        //绘制秒针
        canvas.save();
        canvas.rotate(second * 6);
        canvas.drawLine(0, 0, 0, -290, secondPaint);
        canvas.drawLine(0, 0, 0, pointLength, secondPaint);
        canvas.restore();


    }
}
