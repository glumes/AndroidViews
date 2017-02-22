package com.glumes.androidview.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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

    private int mLeftPos = 0;
    private int mCenterPos = 1;
    private int mRightPos = 2;

    // 花的宽、高
    private int flowerWidth ;
    private int flowerHeight ;

    private int mWidth;
    private int mHeight;

    // 花 开始和结束的位置 ，左边 居中 右边
    private int mStartPos  = mCenterPos;
    private int mEndPos = mCenterPos;



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

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BezierFlower,defStyleAttr,0);

        int num = a.getIndexCount();
        for (int i = 0 ; i < num ;i ++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.BezierFlower_endPos:
                    mEndPos = a.getInt(attr,mCenterPos);
                    break;
                case R.styleable.BezierFlower_startPos:
                    mStartPos = a.getInt(attr,mCenterPos);
                    break;
            }
        }
        a.recycle();

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
        mLayoutParams.addRule(mStartPos == mLeftPos ? ALIGN_LEFT : mStartPos == mCenterPos ? CENTER_HORIZONTAL :
                ALIGN_RIGHT);
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
        mWidth = w ;
        mHeight = h ;

        Timber.d("startPos is %d,endPos is %d",mStartPos,mEndPos);
        if (mStartPos == mLeftPos){
            startPoint.x = flowerWidth / 2;
            startPoint.y = mHeight - flowerHeight /2 ;
            Timber.d("start left");
        }else if(mStartPos == mRightPos){
            startPoint.x = mWidth - flowerWidth / 2;
            startPoint.y = mHeight - flowerWidth / 2;
            Timber.d("start right");
        }else {
            startPoint.x = mWidth / 2 ;
            startPoint.y = mHeight - flowerHeight / 2;
            Timber.d("start center");
        }

        if (mEndPos == mLeftPos){
            startPoint.x = flowerWidth / 2;
            startPoint.y = flowerHeight / 2 ;
            Timber.d("start left");
        }else if(mEndPos == mRightPos){
            startPoint.x = mWidth - flowerWidth / 2 ;
            startPoint.y = flowerHeight / 2 ;
            Timber.d("start right");
        }else {
            startPoint.x = mWidth / 2 ;
            startPoint.y = flowerHeight / 2 ;
            Timber.d("start center");
        }

//        startPoint.x = w ;
//        startPoint.y = h ;
//        endPoint.x = 0;
//        endPoint.y = 0 ;

//        startPoint.x = w / 2;
//        startPoint.y = mHeight ;
//
//        startPoint.x = mWidth / 2 ;
//        startPoint.y = 0 ;
    }

    @Override
    public void onClick(View view) {
        final ImageView flower = new ImageView(getContext());
        flower.setImageDrawable(drawables[random.nextInt(drawables.length)]);
        addView(flower,mLayoutParams);

        ValueAnimator valueAnimator =  ValueAnimator.ofObject(
                new BezierEvaluator(getPointF(false),getPointF(true)),
                startPoint,endPoint);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(flower);
            }
        });
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                flower.setX(pointF.x);
                flower.setY(pointF.y);
            }
        });
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
