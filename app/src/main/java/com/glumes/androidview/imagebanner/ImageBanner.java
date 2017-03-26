package com.glumes.androidview.imagebanner;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;

/**
 * Created by zhaoying on 2017/3/25.
 */

/**
 * 实现图片轮播的容器
 */
public class ImageBanner extends ViewGroup {

    private int mChildrenCount;

    private int mBannerHeight; // 视图的高度
    private int mBannerWidth;     // 视图的宽度

    private int childrenWidth ; // 单个图片子视图的宽度
    private int childrenHeight ;    // 单个图片子视图的高度

    private int x ;
    private int index ;


    private Scroller mScroller ;

    private boolean isAuto = true ;
    private Timer timer = new Timer() ;
    private TimerTask task ;
    private Handler autoHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if (++index >= mChildrenCount){
                        index = 0 ; // 最后一张图片,从第一张开始重新滑动
                    }

                    scrollTo(index * childrenWidth,0);

                    // 自动轮播时底部原点切换
                    imageSelectListener.imageSelect(index);
                    break;

            }
        }
    } ;

    private void startAuto(){
        isAuto = true ;
    }

    private void stopAuto(){
        isAuto = false ;
    }



    // 点击事件的回调

    private ImageBannerListener listener ;

    private boolean isClick ; // true 为点击事件 false 为滑动事件，在 onTouchEvent 事件中判断


    public ImageSelectListener getImageSelectListener() {
        return imageSelectListener;
    }

    public void setImageSelectListener(ImageSelectListener imageSelectListener) {
        this.imageSelectListener = imageSelectListener;
    }

    private ImageSelectListener imageSelectListener ;


    public ImageBanner(Context context) {
        super(context);
        initView();
    }

    public ImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public ImageBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        mScroller = new Scroller(getContext()) ;
        startAuto();
        task= new TimerTask() {
            @Override
            public void run() {
                if (isAuto){
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };

        timer.schedule(task,100,3000); // 开启自动轮播的任务和间隔
    }

    /**
     * 利用 Scroller 滑动时，需要实现一个方法就是 computeScroll 方法
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){// 是否已经滑动完毕
            scrollTo(mScroller.getCurrX(),0);
            invalidate();
        }
    }

    /**
     * 先测量自视图的宽高，再测量 ViewGroup 的宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 求出自视图的个数
        mChildrenCount = getChildCount();

        if (mChildrenCount == 0){
            setMeasuredDimension(0,0);
        }else {
            Timber.d("add View");
            // 测量子视图的宽度和高度
            measureChildren(widthMeasureSpec,heightMeasureSpec);
            // ViewGroup 的高度就是我们第一个子视图的高度，
            // 宽度就是第一个子视图宽度 * 子视图的个数
            View view = getChildAt(0);
            childrenHeight = view.getMeasuredHeight() ;

            childrenWidth = view.getMeasuredWidth() ;

            mBannerWidth = childrenWidth * mChildrenCount;
            // 根据子视图的宽度和高度，来求出 ViewGroup 的宽度和高度
            setMeasuredDimension(mBannerWidth, childrenHeight);
        }
    }



    /**
     *
     * @param changed   布局位置发生改变时为 True,没有为 False
     * @param l         左
     * @param t         上
     * @param r         右
     * @param b         下
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            int leftMargin = 0 ;

            for (int i = 0; i < mChildrenCount; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin+childrenWidth, childrenHeight);
                leftMargin += childrenWidth ;
            }

        }
    }

    /**
     * 调用系统的绘制即可，针对容器的绘制其实就是容器内的子控件的绘制过程
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 调用容器的拦截方法 onInterceptTouchEvent
     * 针对该方法，返回值为 True 时，自定义的 ViewGroup 会处理此次拦截事件
     * 返回值为 False 那么自定义的 ViewGroup 将不会接受此次事件的处理过程，继续向下传递该事件
     * 返回 True ，真正处理的事件为 onTouchEvent 方法
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return true ;
    }

    /**
     * 用两种方式实现轮播图的手动轮播
     *
     * 1、使用 scrollTo 和 scrollBy 完成轮播图的手动轮播
     * 2、使用 Scroller 对象完成轮播图的手动轮播
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                stopAuto();
                if (!mScroller.isFinished()){ // 滑动没有结束，结束滑动
                    mScroller.abortAnimation();
                }

                isClick = true ; // down 事件为点击

                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int distance = moveX - x ; // 滑动的距离
                scrollBy(-distance,0);
                x = moveX ;

                isClick = false ; // 若出现了滑动为 false 代表滑动事件
                break;
            case MotionEvent.ACTION_UP:

                int scrollX = getScrollX() ; // View 左滑动的距离
                index = (scrollX + childrenWidth /2 ) / childrenWidth ;

                if (index < 0){ // 滑动到最左边第一张图片
                    index = 0 ;
                }else if (index > mChildrenCount -1){ // 说明此时已经滑动到了最右边一张
                    index = mChildrenCount - 1 ;
                }

                if (isClick){
                    if (listener!=null){
                        listener.clickImageIndex(index);
                    }
                }else {

//                scrollTo(index * childrenWidth , 0); // 松手后，滑动到指定位置

                    int dx = index * childrenWidth - scrollX ; // 滑动后的距离

                    mScroller.startScroll(scrollX,0,dx,0); // 开始滑动

                    postInvalidate(); // 重绘
                }

                // 小圆点的变化
                imageSelectListener.imageSelect(index);

                startAuto();
                break;
            default:
                break;
        }
        return true ; // 返回 true 表示已经处理好了该事件
    }

    public void setListener(ImageBannerListener listener){
        this.listener  = listener ;
    }

    public ImageBannerListener getListener(){
        return listener ;
    }

    public interface ImageBannerListener{
        void clickImageIndex(int position);
    }

    /**
     * 原点轮播的监听
     */
    public interface ImageSelectListener{
        void imageSelect(int position) ;
    }
}








