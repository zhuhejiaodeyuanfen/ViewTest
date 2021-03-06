package com.vivian.bezierview.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathPainter extends View {

    private PathMeasure pathMeasure;
    private Paint paint;
    private float length;
    private Path path;
    private float currentValue, stop;

    public PathPainter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        path = new Path();
        path.addCircle(500, 500, 100, Path.Direction.CW);

        pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, true);
        length = pathMeasure.getLength();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                currentValue = (float) animation.getAnimatedValue();
                stop = length * currentValue;
                invalidate();
            }
        });

        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        path.lineTo(0, 0);

        pathMeasure.getSegment(0, stop, path, true);
        canvas.drawPath(path, paint);
    }
}
