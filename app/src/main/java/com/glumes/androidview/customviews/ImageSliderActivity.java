package com.glumes.androidview.customviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.glumes.androidview.R;
import com.glumes.androidview.customviews.imageslider.ImageSlider;
import com.glumes.androidview.customviews.imageslider.slidertype.BaseSliderView;
import com.glumes.androidview.customviews.imageslider.slidertype.ImageInfoSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSliderActivity extends AppCompatActivity {

    @BindView(R.id.imageSlider)
    ImageSlider imageSlider;


    int[] imageList = new int[]{
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        ButterKnife.bind(this);



        for (int i = 0; i < 4; i++) {
            ImageInfoSliderView sliderView = new ImageInfoSliderView(this);

            sliderView.image(imageList[i]);

            imageSlider.addSlider(sliderView);
        }

    }
}
