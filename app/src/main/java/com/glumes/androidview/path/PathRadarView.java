package com.glumes.androidview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/3/23.
 */

/**
 * 利用 Path 类实现雷达路径效果
 */
public class PathRadarView extends View{



    private int count = 6 ;
    private Paint mRadarPaint ;      // 绘制雷达图形的画笔
    private Paint mTextPaint ;       // 绘制雷达文本的画笔
    private Paint mDataPaint ;       // 绘制雷达数据的画笔

    private int mCenterX ;
    private int mCenterY ;

    private float mRadius ;    // 网格最大半径

    private Path mPolygonPath  ;     // 多边形的路径
    private Path mLinePath ;    // 直线的路径
    private float angle = (float) (Math.PI * 2 / count); // 数学里面的多少度，计算机里面用 Math.PI 来表示

    private String[] title = {"a","b","c","d","e","f"} ;

    public PathRadarView(Context context) {
        this(context,null);
    }

    public PathRadarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPolygonPath = new Path();
        mLinePath = new Path() ;

        mRadarPaint = new Paint();
        mTextPaint = new Paint();
        mDataPaint = new Paint();

        mRadarPaint.setColor(Color.BLACK);
        mRadarPaint.setStrokeWidth(5);
        mRadarPaint.setStyle(Paint.Style.STROKE);

        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(5);
        mTextPaint.setTextSize(40);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2 ;
        mCenterY = h / 2 ;
        mRadius = Math.min(w,h)/2 * 0.9f ; // 确定半径
        postInvalidate();
    }

    /**
     * 利用 Path 绘制正六边形
     * @param canvas
     */
    private void drawPolygon(Canvas canvas){
        float radius = mRadius / (count - 1) ; // 中心点不绘制，总半径分成五份，radius 就是最小圈的半径
        for (int i = 1 ; i < count ; i ++){
            float curRadius = radius * i ;  // 当前绘制正六边形的半径
            mPolygonPath.reset();   // 重置路径
            for (int j = 0 ; j < count ; j ++){ // count = 6 正好绘制六边形的所有边
                 if (j == 0){
                     mPolygonPath.moveTo(mCenterX + curRadius , mCenterY); // moveTo 移动到起点，也就是最右边的点
                 } else {
                     // 三角函数计算下一个点的位置
                     float x = (float) (mCenterX + curRadius * Math.cos(angle * j));
                     float y = (float) (mCenterY + curRadius * Math.sin(angle * j));
                     mPolygonPath.lineTo(x,y);
                 }
            }
            mPolygonPath.close(); // 闭合路径
            canvas.drawPath(mPolygonPath,mRadarPaint);
        }
    }

    /**
     * 绘制直线，旋转画布来绘制直线
     * 每次绘制后将画布进行旋转，直接绘制直线就好了，减少计算
     * @param canvas
     */
    private void drawLines(Canvas canvas){
        canvas.save();
        canvas.translate(mCenterX,mCenterY);
        for (int i = 0; i < count; i++) {
            canvas.drawLine(0,0,mRadius,0,mRadarPaint);
            canvas.rotate(60f);
        }
        canvas.restore();
    }

    /**
     * 字体属性和测量，http://mikewang.blog.51cto.com/3826268/871765/
     *
     * @param canvas
     */
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics() ;
        float fontHeight = fontMetrics.descent = fontMetrics.ascent ;
        canvas.translate(mCenterX,mCenterY);
        canvas.save() ;
        for (int i = 0; i < count - 1; i++) {
            Timber.d("fontHeight value is %f",fontHeight);
            canvas.drawText(title[i],mRadius + 10,0,mTextPaint);
            canvas.rotate(60);
        }
        canvas.restore();
    }

    private void drawRegion(Canvas canvas){

    }
}
