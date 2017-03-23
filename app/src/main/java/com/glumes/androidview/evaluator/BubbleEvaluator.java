package com.glumes.androidview.evaluator;

import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.graphics.PointF;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/3/23.
 */

/**
 * 气泡效果插值器
 */
public class BubbleEvaluator implements TypeEvaluator<PointF> {


    public BubbleEvaluator() {
    }

    @Override
    public PointF evaluate(float fraction, PointF start, PointF end) {

        PointF result = new PointF();

        result.x = start.x + fraction * (end.x - start.x) ;
        result.y = start.y + fraction * (end.y - start.y) ;

        return  result ;
    }
}
