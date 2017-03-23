package com.glumes.androidview.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.glumes.androidview.evaluator.BubbleEvaluator;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/3/23.
 */

/**
 * 仿 QQ 的可伸缩的气泡效果，用到贝塞尔曲线
 */
public class BubbleView extends View{

    private int mCenterX ;
    private int mCenterY ;

    private Paint mCirclePaint;
    private int mCircleRadius = 50 ;
    private int mBubbleRadius = 90 ;

    private float moveX;
    private float moveY ;

    private int touchDistance = 400;

    private boolean inTouchArea ;

    private Path mBubblePath ;

    private boolean isBroke ; // 气泡断开连接

    public BubbleView(Context context) {
        super(context);
        initView();
    }

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclePaint.setAntiAlias(true);

        mBubblePath = new Path() ;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        if (!isBroke && inTouchArea){
            drawBubble(canvas);
            drawBubblePath(canvas);
        }
    }

    /**
     * 绘制原始圆圈
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
         canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mCirclePaint);
    }

    /**
     * 绘制气泡
     * @param canvas
     */
    private void drawBubble(Canvas canvas){
        canvas.drawCircle(moveX,moveY,mBubbleRadius,mCirclePaint);
    }

    /**
     * 绘制气泡之间的连接部分
     * @param canvas
     */
    private void drawBubblePath(Canvas canvas){
        canvas.drawPath(mBubblePath,mCirclePaint);
        Timber.d("draw path");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2 ;
        mCenterY = h / 2 ;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isBroke = false ;
                moveX = event.getX() ;      // getX 和 getY 得到的距离是相对于当前 View 左上角距离
                moveY = event.getY() ;      // 也就是相对于 BubbleView 的距离
                // 判断 ACTION_DOWN 动作时，触碰的区域是否在限定的区域内
                Timber.d("moveX is %f and moveY is %f",moveX,moveY);
                inTouchArea = isInTouchArea(moveX,moveY,touchDistance) ;
                break;
            case MotionEvent.ACTION_MOVE:
                if (inTouchArea){
                    moveX = event.getX() ;
                    moveY = event.getY() ;
                    if (!isInTouchArea(moveX, moveY, touchDistance)){ // 移动时超出了限定区域
                        inTouchArea = false ;
                        isBroke = true ;
                    } else {
                        setPath(moveX,moveY);
                    }
                    invalidate();       // 重绘
                }
                break;
            case MotionEvent.ACTION_UP:
                if (inTouchArea){
                    resetCircle(event.getX(),event.getY()); // 松手之后，若还在限定区域内，则弹回原位置
                }
                break;
        }
        return true ;
    }

    /**
     *
     * @param x
     * @param y
     */
    private void resetCircle(float x, float y) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BubbleEvaluator(),
                new PointF(x,y),
                new PointF(mCenterX,mCenterY)) ;
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                moveX = pointF.x ;
                moveY = pointF.y ;
                setPath(pointF.x,pointF.y);
                invalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 计算得到气泡效果两个圆之间连接的绘制图案
     * 一个类似于一半椭圆的效果
     * 利用贝塞尔曲线效果绘制的
     * @param moveX
     * @param moveY
     */
    private void setPath(float moveX, float moveY) {

        // 由于此处 View 的宽和高都设为了 match_parent，为屏幕的宽高
        double angle = Math.atan((moveX - mCenterX) / (moveY - mCenterY)) ;

        // 构建连接图案的路径

        // 起点
        float x1 = (float) (mCenterX + mCircleRadius * Math.cos(angle));
        float y1 = (float) (mCenterY - mCircleRadius * Math.sin(angle));

        float x2 = (float) (moveX + mBubbleRadius * Math.cos(angle));
        float y2 = (float) (moveY - mBubbleRadius * Math.sin(angle));

        float x3 = (float) (moveX - mBubbleRadius * Math.cos(angle));
        float y3 = (float) (moveY + mBubbleRadius * Math.sin(angle));

        // 终点
        float x4 = (float) (mCenterX - mCircleRadius * Math.cos(angle));
        float y4 = (float) (mCenterY + mCircleRadius * Math.sin(angle));

        // 贝塞尔曲线参考锚点的坐标

        float centerX = mCenterX + (moveX - mCenterX) / 2 ;
        float centerY = mCenterY + (moveY - mCenterY) / 2 ;

        mBubblePath.reset();
        mBubblePath.moveTo(mCenterX,mCenterY);
        mBubblePath.lineTo(x1,y1);
        mBubblePath.quadTo(centerX,centerY,x2,y2);
        mBubblePath.lineTo(x3,y3);
        mBubblePath.quadTo(centerX,centerY,x4,y4);
        mBubblePath.lineTo(mCenterX,mCenterY);
        mBubblePath.close();
    }



    /**
     * 气泡效果的限定范围
     * 若在点击时超出该范围，则点击无效果
     * 若在移动时超出该范围，则气泡会破裂
     * 处于该范围内移动时，会有气泡拉伸的效果
     * @param offsetX
     * @param offsetY
     * @param touchDistance
     * @return
     */
    private boolean isInTouchArea(float offsetX, float offsetY, float touchDistance){
        return (offsetX - mCenterX) * (offsetX - mCenterX) +
                (offsetY - mCenterY) * (offsetY - mCenterY) <
                touchDistance * touchDistance ;
    }
}









