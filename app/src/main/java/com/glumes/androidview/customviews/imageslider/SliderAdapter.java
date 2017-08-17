package com.glumes.androidview.customviews.imageslider;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.glumes.androidview.customviews.imageslider.slidertype.BaseSliderView;

import java.util.ArrayList;

/**
 * Created by zhaoying on 2017/8/17.
 */

public class SliderAdapter extends PagerAdapter {


    private Context mContext;

    private ArrayList<BaseSliderView> sliderViewList;

    public SliderAdapter(Context context) {
        mContext = context;
        sliderViewList = new ArrayList<>();

        mockData();
    }

    private void mockData() {
//        sliderViewList.add()
    }


    @Override
    public int getCount() {
        return sliderViewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseSliderView b = sliderViewList.get(position);
        View slider = b.getView();
        container.addView(slider);
        return slider;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public <T extends BaseSliderView> void addSlider(T slider) {
        sliderViewList.add(slider);
        notifyDataSetChanged();
    }

    public BaseSliderView getSliderView(int position) {
        if (position < 0 || position >= sliderViewList.size()) {
            return null;
        } else {
            return sliderViewList.get(position);
        }
    }
}
