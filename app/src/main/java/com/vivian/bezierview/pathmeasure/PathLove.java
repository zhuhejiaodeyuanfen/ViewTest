package com.vivian.bezierview.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathLove extends View {
    private int screenWidth, screenHeight;
    private Path lovePath;
    private Paint paint;
    private PathMeasure pathMeasure;
    private float current;
    private float[] position = new float[2];

    public PathLove(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);

        pathMeasure = new PathMeasure();


        lovePath = new Path();
        lovePath.moveTo(0, 0);
        lovePath.quadTo(-380, -100, 0, 400);
        lovePath.quadTo(380, -100, 0, 0);

        pathMeasure.setPath(lovePath, true);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                current = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(current * pathMeasure.getLength(), position, null);
                invalidate();
            }
        });
        valueAnimator.setDuration(4000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(screenWidth / 2, screenHeight / 2);
        canvas.drawPath(lovePath, paint);
        canvas.drawCircle(position[0],position[1],20,paint);
        super.onDraw(canvas);

    }
}
