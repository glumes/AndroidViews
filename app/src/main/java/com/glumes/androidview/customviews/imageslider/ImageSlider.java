package com.glumes.androidview.customviews.imageslider;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.glumes.androidview.R;
import com.glumes.androidview.customviews.imageslider.slidertype.BaseSliderView;

/**
 * Created by zhaoying on 2017/8/17.
 */

public class ImageSlider extends RelativeLayout {

    private Context mContext;

    private SliderAdapter slideAdapter;

    private SliderViewPager sliderViewPager;

    public ImageSlider(Context context) {
        this(context, null);
    }

    public ImageSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.image_slider_layout, this, true);


        sliderViewPager = (SliderViewPager) findViewById(R.id.slider_view_pager);

        slideAdapter = new SliderAdapter(mContext);

        sliderViewPager.setAdapter(slideAdapter);

        sliderViewPager.setPageTransformer(true, new RotateDownTransformer());
    }

    public <T extends BaseSliderView> void addSlider(T slider) {
        slideAdapter.addSlider(slider);
    }

}
