package com.glumes.androidview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhaoying on 2017/3/23.
 */

/**
 * Path 封装了曲线和直线的路径
 */
public class PathView extends View {

    private Paint mPaint ;

    private int mWidth ;
    private int mHeight ;
    private Path mPath;

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2 , mHeight / 2); // lineTo 默认起点是上一次操作后的点，若没有则是坐标原点
//        mPath.lineTo(200,200);
//        mPath.setLastPoint(200,100);    // 设置上一次操作的终点，也就是重置了终点
//        mPath.moveTo(200,400);          // 设置下一次操作的起点，也就是不使用默认上一次操作后的终点
//        mPath.lineTo(200,0);
//        mPath.close();                  // 连接当前最后一个点和当初第一个点，形成封闭图形

//        mPath.addRect(-200,-200,200,200,Path.Direction.CW);     // 最后一个参数代表顺时针还是逆时针，影响绘制的最后一个点的确认

        canvas.scale(1,-1);     // 翻转 Y 轴
        Path path = new Path();
        Path src = new Path();

        path.addRect(-200,-200,200,200, Path.Direction.CW);
        src.addCircle(0,0,100, Path.Direction.CW);
        path.addPath(src,0,200);

        mPaint.setColor(Color.BLACK);

        canvas.drawPath(path,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w ;
        mHeight = h ;
    }
}
