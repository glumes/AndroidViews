package com.glumes.androidview.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.glumes.androidview.R;

/**
 * Created by zhaoying on 2017/2/12.
 */

public class BezierFlower extends RelativeLayout {


    private Drawable[] drawables ;

    public BezierFlower(Context context) {
        this(context,null);
    }

    public BezierFlower(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierFlower(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        drawables = new Drawable[6];
        drawables[0] = getResources().getDrawable(R.mipmap.flower_1);
        drawables[1] = getResources().getDrawable(R.mipmap.flower_2);
        drawables[2] = getResources().getDrawable(R.mipmap.flower_3);
        drawables[3] = getResources().getDrawable(R.mipmap.flower_4);
        drawables[4] = getResources().getDrawable(R.mipmap.flower_5);
        drawables[5] = getResources().getDrawable(R.mipmap.flower_6);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


}
