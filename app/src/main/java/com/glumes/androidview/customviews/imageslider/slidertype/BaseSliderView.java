package com.glumes.androidview.customviews.imageslider.slidertype;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by zhaoying on 2017/8/17.
 */

public abstract class BaseSliderView {

    private int mImageRes;
    private String mUrl;
    private File mFile;

    private Context mContext;

    private Bundle mBundle;

    public BaseSliderView(Context mContext) {
        this.mContext = mContext;
    }


    public abstract View getView();


    public BaseSliderView image(int resId) {
        mImageRes = resId;
        return this;
    }

    public BaseSliderView image(String url) {
        mUrl = url;
        return this;
    }

    public BaseSliderView image(File file) {
        mFile = file;
        return this;
    }

    public BaseSliderView bundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public int getImageResId() {
        return mImageRes;
    }

}
