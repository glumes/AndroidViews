package com.glumes.androidview.customviews.imageslider.slidertype;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zhaoying on 2017/8/17.
 */

public class ImageInfoSliderView extends BaseSliderView {

    public ImageInfoSliderView(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(getImageResId());
        return imageView;
    }
}
