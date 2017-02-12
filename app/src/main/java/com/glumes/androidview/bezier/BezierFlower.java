package com.glumes.androidview.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.glumes.androidview.R;
import com.glumes.androidview.evaluator.BezierEvaluator;

import java.util.Random;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/2/12.
 */

public class BezierFlower extends RelativeLayout implements View.OnClickListener{

    private static final String TAG = BezierFlower.class.getSimpleName();

    private Context mContext ;

    private Drawable[] drawables ;

    private Random random = new Random();

    private LayoutParams mLayoutParams ;

    // 花的宽、高
    private int flowerWidth ;
    private int flowerHeight ;

    private int mWidth;
    private int mHeight;

    // 动画开始点和结束点
    private PointF startPoint;
    private PointF endPoint ;

    public BezierFlower(Context context) {
        this(context,null);
    }

    public BezierFlower(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierFlower(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context ;
        setOnClickListener(this);
        initView();
    }

    private void initView() {
        drawables = new Drawable[6];
        drawables[0] = getResources().getDrawable(R.drawable.flower_1);
        drawables[1] = getResources().getDrawable(R.drawable.flower_2);
        drawables[2] = getResources().getDrawable(R.drawable.flower_3);
        drawables[3] = getResources().getDrawable(R.drawable.flower_4);
        drawables[4] = getResources().getDrawable(R.drawable.flower_5);
        drawables[5] = getResources().getDrawable(R.drawable.flower_6);
        flowerHeight = drawables[0].getIntrinsicHeight();
        flowerWidth = drawables[0].getIntrinsicWidth();
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.addRule(ALIGN_LEFT);
        mLayoutParams.addRule(ALIGN_PARENT_BOTTOM);

        startPoint = new PointF();
        endPoint = new PointF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPoint.x = w / 2 ;
        startPoint.y = h ;

        endPoint.x = w / 2;
        endPoint.y = 0 ;

        mWidth = w ;
        mHeight = h ;
    }

    @Override
    public void onClick(View view) {
        final ImageView flower = new ImageView(getContext());
        flower.setImageDrawable(drawables[random.nextInt(drawables.length)]);
        addView(flower,mLayoutParams);

        ValueAnimator valueAnimator =  ValueAnimator.ofObject(
                new BezierEvaluator(getPointF(false),getPointF(true)),
                startPoint,endPoint);


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                float fraction = valueAnimator.getAnimatedFraction();
                flower.setX(pointF.x);
                flower.setY(pointF.y);
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
    }


    /**
     * 该方法，实现随机点击触发从鲜花，而 onClick 实现固定点送鲜花
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 贝塞尔的控制点
     * @param up
     * @return
     */
    private PointF getPointF(boolean up){
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth);
        if (up){
            pointF.y = random.nextInt(mHeight/2);
        }else {
            pointF.y = random.nextInt(mHeight/2) + mHeight/2; //
        }
        return pointF ;
    }



}
