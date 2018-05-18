package com.vivian.bezierview.praise;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PointEvaluate implements TypeEvaluator<PointF> {

    private PointF pointF1, pointF2;

    public PointEvaluate(PointF pointF1, PointF pointF2) {
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = (1 - fraction) * (1 - fraction) * (1 - fraction) * startValue.x
                + 3 * (1 - fraction) * (1 - fraction) * fraction * pointF1.x
                + 3 * (1 - fraction) * fraction * fraction * pointF2.x
                + fraction * fraction * fraction * endValue.x;

        float y = (1 - fraction) * (1 - fraction) * (1 - fraction) * startValue.y
                + 3 * (1 - fraction) * (1 - fraction) * fraction * pointF1.y
                + 3 * (1 - fraction) * fraction * fraction * pointF2.y
                + fraction * fraction * fraction * endValue.y;
        return new PointF(x, y);
    }
}
