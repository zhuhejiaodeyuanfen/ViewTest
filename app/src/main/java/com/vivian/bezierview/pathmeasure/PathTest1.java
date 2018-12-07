package com.vivian.bezierview.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PathTest1 extends View {

    private Paint paint;

    public static final String TAG = PathTest1.class.getName();

    private Path path, path2, path3;

    private int width, height;

    public PathTest1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.lineTo(0, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 0);

        PathMeasure pathMeasure1 = new PathMeasure(path, false);
        PathMeasure pathMeasure2 = new PathMeasure(path, true);

        Log.e(TAG, "forceClosed=false---->" + pathMeasure1.getLength());
        Log.e(TAG, "forceClosed=true---->" + pathMeasure2.getLength());


//        path2 = new Path();
//        path2.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        path3 = new Path();
//        PathMeasure pathMeasure = new PathMeasure(path2, false);
//        pathMeasure.getSegment(200, 600, path3, true);

        path2 = new Path();
        path2.addRect(-200, -200, 200, 200, Path.Direction.CW);
        path3 = new Path();
        path3.lineTo(-300, -300);
        PathMeasure pathMeasure = new PathMeasure(path2, false);
        //true和false不一样
        pathMeasure.getSegment(200, 600, path3, false);
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

//        canvas.drawPath(path, paint);
        canvas.drawPath(path3, paint);

    }
}
