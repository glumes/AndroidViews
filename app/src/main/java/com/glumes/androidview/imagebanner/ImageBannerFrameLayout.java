package com.glumes.androidview.imagebanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.glumes.androidview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoying on 2017/3/26.
 */

public class ImageBannerFrameLayout extends FrameLayout implements ImageBanner.ImageSelectListener,ImageBanner.ImageBannerListener{
    

    private ImageBanner imageBanner ;
    private LinearLayout linearLayout ;

    public ImageBannerFrameLayout(@NonNull Context context) {
        super(context);
        initImageBanner();
        initDotLayout();
    }

    private void initImageBanner() {
        imageBanner = new ImageBanner(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        imageBanner.setLayoutParams(lp);
        imageBanner.setImageSelectListener(this);// 小圆点滑动的监听事件
        imageBanner.setListener(this);
        addView(imageBanner);
    }

    private void initDotLayout() {
        linearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,40);
        linearLayout.setLayoutParams(lp);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        linearLayout.setGravity(Gravity.CENTER);

        linearLayout.setBackgroundColor(Color.RED);

        addView(linearLayout);

        FrameLayout.LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM ;
        linearLayout.setLayoutParams(layoutParams);

        // 3.0 版本以后是用 setAlpha 方法，而 3.0 之前使用也是 setAlpha，但是调用有所不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            linearLayout.setAlpha(0.5f);
        } else {    //
            linearLayout.getBackground().setAlpha(100);
        }

    }

    public ImageBannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBanner();
        initDotLayout();
    }

    public ImageBannerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBanner();
        initDotLayout();
    }


    public void addBitmaps(List<Bitmap> bitmaps){
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bitmap = bitmaps.get(i);
            addBitmapToImageBanner(bitmap);
            addDotToLinearLayout();
        }
    }

    private void addDotToLinearLayout() {
        ImageView iv = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT) ;
        lp.setMargins(5,5,5,5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(iv);

    }

    private void addBitmapToImageBanner(Bitmap bitmap) {

            ImageView imageView = new ImageView(getContext());

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            imageView.setImageBitmap(bitmap);

            imageBanner.addView(imageView);
    }

    @Override
    public void imageSelect(int position) {
        int count = linearLayout.getChildCount() ;
        for (int i = 0; i < count; i++) {
            ImageView iv = (ImageView) linearLayout.getChildAt(i);
            if (i == position){
                iv.setImageResource(R.drawable.dot_select);
            }else {
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    @Override
    public void clickImageIndex(int position) {
        Toast.makeText(getContext(),"pos is " + position,Toast.LENGTH_SHORT).show();
    }
}
