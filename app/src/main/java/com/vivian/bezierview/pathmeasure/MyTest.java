package com.vivian.bezierview.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyTest extends View {

    private int width, height;
    private int smallRadius, bigRadius;
    private Paint smallPaint, greenPaint, yellowPaint, redPaint,textPaint;

    public MyTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        smallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallPaint.setStyle(Paint.Style.FILL);
        smallPaint.setStrokeWidth(10);
        smallPaint.setColor(Color.WHITE);

        smallRadius = 100;
        bigRadius = 400;

        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(10);
        greenPaint.setColor(Color.GREEN);

        yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setStrokeWidth(10);
        yellowPaint.setColor(Color.YELLOW);

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(10);
        redPaint.setColor(Color.RED);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(5);
        textPaint.setTextSize(10);
        textPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);

        canvas.drawCircle(0, 0, smallRadius, smallPaint);

        canvas.drawArc(new RectF(-bigRadius, -bigRadius, bigRadius, bigRadius), 145, 100, false, greenPaint);

        canvas.drawArc(new RectF(-bigRadius, -bigRadius, bigRadius, bigRadius), 245, 50, false, yellowPaint);

        canvas.drawArc(new RectF(-bigRadius, -bigRadius, bigRadius, bigRadius), 295, 100, false, redPaint);

        drawLine(canvas);

    }

    private int lineHeight;

    private void drawLine(Canvas canvas) {
        canvas.save();

        /**
         * 参数为正则顺时针，否则逆时针
         *
         * 旋转的是画布的坐标系原点
         */
        canvas.rotate(55);

        for (int i = 0; i <= 50; i++) {

            if (i % 5 == 0) {
                lineHeight = bigRadius - 70;
                canvas.drawText("0.00",0,lineHeight-10,textPaint);
            } else {
                lineHeight = bigRadius - 40;
            }
            canvas.drawLine(0, lineHeight, 0, bigRadius, greenPaint);

            canvas.rotate(25 / 5);
        }


        canvas.restore();

    }
}
