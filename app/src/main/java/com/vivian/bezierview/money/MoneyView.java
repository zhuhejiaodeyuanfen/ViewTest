package com.vivian.bezierview.money;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MoneyView extends View {
    private Paint paint;
    private int totalLength = 2000;
    private int lastMoveX;
    private boolean isDrawText;
    private int motionLastX, motionX,moveX;


    public MoneyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(10);
        paint.setTextSize(30);
    }

    private int getMySize(int width) {
        int defaultSize = 250;
        int returnSize = defaultSize;
        int mode = MeasureSpec.getMode(width);
        int size = MeasureSpec.getSize(width);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                returnSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                returnSize = Math.min(size, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                returnSize = size;
                break;
        }
        return returnSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMySize(widthMeasureSpec), getMySize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        drawLine(canvas);
    }


    private void drawLine(Canvas canvas) {
        for (int start = 0; start < totalLength; start++) {
            int top = getHeight() - 30;

            if ((start + lastMoveX) % (13 * 10) == 0) {
                top = top - 50;
                isDrawText = true;
            } else {
                isDrawText = false;
            }

            if (start + lastMoveX >= 0 && start + lastMoveX <= 20000 * 13) {
                if (isDrawText) {
                    canvas.drawText((lastMoveX + start) / 13 + "", start, top - 8, paint);
                    canvas.drawLine(start, getHeight(), start, top, paint);
                } else {
                    if ((start + lastMoveX) % 13 == 0) {
                        canvas.drawLine(start, getHeight(), start, top, paint);
                    }

                }

            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                motionLastX = (int) event.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                moveX= (int) (event.getRawX()-motionLastX);

                break;

        }
        return true;
    }
}
