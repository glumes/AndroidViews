package com.glumes.androidview.imagebanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.glumes.androidview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ImageBannerActivity extends AppCompatActivity implements ImageBanner.ImageBannerListener {


//    @BindView(R.id.imageBanner)
//    ImageBanner mImageBanner;

    @BindView(R.id.imageBannerLayout)
    ImageBannerFrameLayout imageBannerLayout;

    private int[] images = {
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_banner);
        ButterKnife.bind(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

//        for (int i = 0; i < 4; i++) {
//            ImageView imageView = new ImageView(this);
//
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            imageView.setImageResource(images[i]);
//
//            mImageBanner.addView(imageView);
//        }
//
//        mImageBanner.setListener(this);

        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),images[i]);
            list.add(bitmap);
        }

        imageBannerLayout.addBitmaps(list);
    }

    @Override
    public void clickImageIndex(int position) {
        Toast.makeText(this, "pos" + position, Toast.LENGTH_SHORT).show();
    }
}
