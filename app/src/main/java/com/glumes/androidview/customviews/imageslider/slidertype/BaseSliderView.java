package com.glumes.androidview.customviews.imageslider.slidertype;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zhaoying on 2017/8/17.
 */

public abstract class BaseSliderView {


    private Context mContext;

    public BaseSliderView(Context mContext) {
        this.mContext = mContext;
    }

    public View getView() {
        return null;
    }
}
