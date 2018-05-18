package com.vivian.bezierview.shop;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PointEvaluate implements TypeEvaluator<PointF> {
    private PointF controllerValue;


    public PointEvaluate(PointF controllerValue) {
        this.controllerValue = controllerValue;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = (1 - fraction) * (1 - fraction) * startValue.x + 2 * (1 - fraction) * fraction * controllerValue.x + fraction * fraction * endValue.x;
        float y = (1 - fraction) * (1 - fraction) * startValue.y + 2 * (1 - fraction) * fraction * controllerValue.y + fraction * fraction * endValue.y;
        return new PointF(x, y);
    }
}
