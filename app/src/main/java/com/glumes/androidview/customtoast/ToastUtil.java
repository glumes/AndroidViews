package com.glumes.androidview.customtoast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.glumes.androidview.R;
import com.glumes.androidview.util.DisplayUtil;


/**
 * Created by zhaoying on 2017/6/2.
 */

public class ToastUtil {


    private String tvContent;

    private Context mContext;

    private static final float PADDING_PX = 48f;
    private static final float Y_OFFSET = 150f;

    private ToastUtil(Builder builder) {
        this.tvContent = builder.tvContent;
        this.mContext = builder.mContext;
    }


    public void show() {
        View root = LayoutInflater.from(mContext).inflate(R.layout.toast_layout, null);
        TextView tv = (TextView) root.findViewById(R.id.tvContent);
        tv.setText(tvContent);
        int padding = DisplayUtil.px2dip(mContext, PADDING_PX);
        tv.setPadding(padding, 0, padding, 0);
        int offset = DisplayUtil.px2dip(mContext, Y_OFFSET);
        Toast toast = new Toast(mContext.getApplicationContext());
        toast.setView(root);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, offset);
        toast.show();
    }


    public static class Builder {
        String tvContent;
        Context mContext;


        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setContent(@StringRes int titleId) {
            tvContent = mContext.getString(titleId);
            return this;
        }

        public Builder setContent(String title) {
            tvContent = title;
            return this;
        }

        public ToastUtil create() {
            return new ToastUtil(this);
        }

        public void show() {
            ToastUtil toastUtil = create();
            toastUtil.show();
        }

    }

}
