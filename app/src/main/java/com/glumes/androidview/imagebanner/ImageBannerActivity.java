package com.glumes.androidview.imagebanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.glumes.androidview.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageBannerActivity extends AppCompatActivity {


    @BindView(R.id.imageBanner)
    ImageBanner mImageBanner;

    private int[] images = {
            R.drawable.banner1 ,
            R.drawable.banner2 ,
            R.drawable.banner3 ,
            R.drawable.banner4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_banner);
        ButterKnife.bind(this);

        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getDrawable(images[i]));
            mImageBanner.addView(imageView);
        }
    }
}
