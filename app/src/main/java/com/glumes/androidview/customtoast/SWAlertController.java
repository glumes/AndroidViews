package com.glumes.androidview.customtoast;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.glumes.androidview.R;


/**
 * Created by zhaoying on 2017/5/26.
 */

public class SWAlertController {

    private Context mContext;
    private Dialog mDialog;
    private Window mWindow;

    public String mTitle;
    public String mMessage;
    public String mNegativeBtnText;
    public String mPositiveBtnText;
    public View.OnClickListener mPositiveBtnListener;
    public View.OnClickListener mNegativeBtnListener;
    public Button mNegativeBtn;
    public Button mPositiveBtn;
    public TextView mTvTitle;
    public TextView mTvMessage;

    public boolean isBackgroundDark = false;

    private final View.OnClickListener mButtonHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancel:
                    if (mNegativeBtnListener != null) {
                        mNegativeBtnListener.onClick(v);
                    }
                    break;
                case R.id.btn_confirm:
                    if (mPositiveBtnListener != null) {
                        mPositiveBtnListener.onClick(v);
                    }
                    break;
                default:
                    break;
            }
            mDialog.dismiss();
        }
    };

    public SWAlertController(Context mContext, Dialog mDialog, Window mWindow) {
        this.mContext = mContext;
        this.mDialog = mDialog;
        this.mWindow = mWindow;
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void installContent() {
        mDialog.setContentView(R.layout.alert_dialog_layout);
        setupView();
    }

    private void setupView() {

        ViewGroup layout = (ViewGroup) mWindow.findViewById(R.id.cv_layout);
        setupContent(layout);
        setupButtons(layout);
    }


    private void setupContent(ViewGroup layout) {
        mTvTitle = (TextView) layout.findViewById(R.id.tv_alert_title);
        if (mTitle != null) {
            mTvTitle.setText(mTitle);
        }

        mTvMessage = (TextView) layout.findViewById(R.id.tv_alert_msg);
        if (mMessage != null) {
            mTvMessage.setText(mMessage);
        }
//        setBackground(isBackgroundDark);

    }

    private void setupButtons(ViewGroup layout) {
        mPositiveBtn = (Button) layout.findViewById(R.id.btn_confirm);
        mNegativeBtn = (Button) layout.findViewById(R.id.btn_cancel);
        if (mPositiveBtnText != null) {
            mPositiveBtn.setText(mPositiveBtnText);
        }

        if (mNegativeBtnText != null) {
            mNegativeBtn.setText(mNegativeBtnText);
        }
        mPositiveBtn.setOnClickListener(mButtonHandler);
        mNegativeBtn.setOnClickListener(mButtonHandler);
    }

//    public void setBackground(boolean isBackgroundDark) {
//        if (!isBackgroundDark) {
//            WindowManager.LayoutParams lp = mWindow.getAttributes();
//            mWindow.setGravity(Gravity.CENTER);
//            lp.dimAmount = 0f;
//            mWindow.setAttributes(lp);
//        }
//    }

    public static class AlertParams {

        public Context mContext;
        public String mTitle;
        public String mMessage;
        public String mNegativeBtnText;
        public String mPositiveBtnText;
        public View.OnClickListener mPositiveBtnListener;
        public View.OnClickListener mNegativeBtnListener;
        public boolean mIsBackgroundDark;

        public AlertParams(Context context) {
            mContext = context;
        }

        public void apply(SWAlertController dialog) {
            if (mTitle != null) {
                dialog.mTitle = mTitle;
            }

            if (mMessage != null) {
                dialog.mMessage = mMessage;
            }

            if (mNegativeBtnText != null) {
                dialog.mNegativeBtnText = mNegativeBtnText;
            }

            if (mPositiveBtnText != null) {
                dialog.mPositiveBtnText = mPositiveBtnText;
            }

            dialog.isBackgroundDark = mIsBackgroundDark;
            dialog.mPositiveBtnListener = mPositiveBtnListener;
            dialog.mNegativeBtnListener = mNegativeBtnListener;
        }
    }
}


