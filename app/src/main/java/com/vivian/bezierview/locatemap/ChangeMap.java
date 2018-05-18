package com.vivian.bezierview.locatemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ChangeMap extends View {


    private Paint paint;
    private int width, height;
    private Path path;
    private Region circleRegion;
    private Matrix matrix;

    private float[] touchPoints = new float[2];

    public ChangeMap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);


        circleRegion = new Region();
        path = new Path();
        //Path.Direction.CW---顺时针  Path.Direction.CCW逆时针
        path.addCircle(0, 0, 250, Path.Direction.CW);

        Region region = new Region(-width / 2, -height / 2, width / 2, height / 2);
        circleRegion.setPath(path, region);

        matrix = new Matrix();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        matrix.reset();
        if (matrix.isIdentity()) {
            canvas.getMatrix().invert(matrix);
        }
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                touchPoints[0] = event.getRawX();
                touchPoints[1] = event.getRawY();

                matrix.mapPoints(touchPoints);
                if (circleRegion.contains((int) touchPoints[0], (int) touchPoints[1])) {
                    Toast.makeText(getContext(), "触摸区域内", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;

    }
}
