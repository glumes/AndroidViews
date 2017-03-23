package com.glumes.androidview.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.glumes.androidview.R;

import timber.log.Timber;

/**
 * 通过给 LayoutInflater 设置 LayoutInflater.Factory 接口
 * 从而在系统加载布局 View 之前做一些操作，比如替换系统 View 或者实现换肤
 */
public class InflaterActivity extends AppCompatActivity {

    private Context mContext ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mContext = this ;

        LayoutInflater inflater = LayoutInflater.from(this) ;
        if (inflater.getFactory2() != null){
            inflater = inflater.cloneInContext(this);
            Timber.d("clone");
        }


        LayoutInflaterCompat.setFactory(inflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Timber.d("name is %s",name);
                if (name.equals("TextView")){
                    return new Button(mContext);
                }
                return  null ;
            }
        });

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inflater);


    }
}
