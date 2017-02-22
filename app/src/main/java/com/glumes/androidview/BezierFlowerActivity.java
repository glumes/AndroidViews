package com.glumes.androidview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.glumes.androidview.evaluator.BezierEvaluator;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BezierFlowerActivity extends AppCompatActivity {

    private static final String TAG = "testtest";
    @BindView(R.id.addView)
    Button mAddView;
    @BindView(R.id.activity_main)
    CoordinatorLayout mCoordinatorLayout;

    private Context mContext;

    private static final int Duration = 2000 ;

    private static final int UP_POINT = 1 ;
    private static final int DOWN_POINT = 2 ;

    private int mPhoneWidht;
    private int mPhoneHeight;

    private int mParentLayoutWidth ;
    private int mParentLayoutHeight ;

    private int mImageWidth ;
    private int mImageHeight ;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext = this;

        Resources resources = getResources();

        DisplayMetrics displayMetrics = resources.getDisplayMetrics() ;
        mPhoneWidht = displayMetrics.widthPixels ;
        mPhoneHeight = displayMetrics.heightPixels ;

        Timber.d("displayMetrics : width is %d, height is %d", mPhoneWidht, mPhoneHeight);


        mAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mParentLayoutHeight = mCoordinatorLayout.getHeight() ;
                mParentLayoutWidth = mCoordinatorLayout.getWidth() ;


                final ImageView imageView = new ImageView(mContext);

                Drawable background = getResources().getDrawable(R.drawable.flower_1) ;
                imageView.setImageDrawable(background);

                mImageHeight = background.getIntrinsicHeight() ;
                mImageWidth = background.getIntrinsicWidth() ;

                CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM ;
                mCoordinatorLayout.addView(imageView,layoutParams);


                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView,"alpha",4.0f,1.0f);
                alphaAnimator.setDuration(Duration);

                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView,"scaleX",0.5f,1.0f) ;
                scaleXAnimator.setDuration(Duration);

                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView,"scaleY",0.5f,1.0f) ;
                scaleYAnimator.setDuration(Duration);


                Timber.d("coordinatorlayout height is %d , width is %d",mCoordinatorLayout.getHeight(),mCoordinatorLayout.getWidth());
                Timber.d("image height is %d , width is %d",mImageHeight,mImageWidth);

//                ObjectAnimator vertialAnimator = ObjectAnimator.ofFloat(imageView,"translationY",0,-mCoordinatorLayout.getHeight() + imageHeight);
//
//                vertialAnimator.setDuration(Duration * 2);
//
//                vertialAnimator.start();

                ValueAnimator bezierAniamator = getBezierAnimator();

                bezierAniamator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mCoordinatorLayout.removeView(imageView);
                    }
                });

                bezierAniamator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        PointF point = (PointF) valueAnimator.getAnimatedValue();
                        Timber.d("point x is %f",point.x);
                        imageView.setX(point.x);
                        imageView.setY(point.y);
                    }
                });
                AnimatorSet animatorSet = new AnimatorSet();

                animatorSet.play(bezierAniamator).with(alphaAnimator).with(scaleXAnimator).with(scaleYAnimator);
                animatorSet.start();

            }

        });
    }


    ValueAnimator getBezierAnimator(){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(getPointF(DOWN_POINT),
                getPointF(UP_POINT)),
                new PointF((mParentLayoutWidth - mImageWidth)/2f ,(mParentLayoutHeight - mImageHeight)) ,
                new PointF(random.nextInt(mParentLayoutWidth),0)
                );

        Timber.d("start point width is %d,height is %d",(mParentLayoutWidth - mImageWidth)/2 ,(mParentLayoutHeight - mImageHeight));
        Timber.d("end point width is %d,height is %d",random.nextInt(mParentLayoutWidth),0);
        valueAnimator.setDuration(Duration) ;
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator ;
    }

    /**
     * 随机得到点
     * @param position
     * @return
     */
    private PointF getPointF(int position ){
        PointF point = new PointF();
        point.x = random.nextInt(mParentLayoutWidth);
        if (position == UP_POINT){
            point.y = random.nextInt(mParentLayoutHeight/2);
        }else {
            point.y = random.nextInt(mParentLayoutHeight/2) + mParentLayoutHeight / 2;
        }
        return point ;
    }


}
