package com.glumes.androidview.customtoast;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.View;

import com.glumes.androidview.R;


/**
 * Created by zhaoying on 2017/5/25.
 */

public class SWAlertDialog extends Dialog {

    private Context mContext;

    private SWAlertController mController;

    protected SWAlertDialog(@NonNull Context context) {
        super(context, 0);
        initDialog(context);
    }

    protected SWAlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context,themeResId);
        initDialog(context);
    }

    protected SWAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context,cancelable,cancelListener);
        initDialog(context);
    }

    public void initDialog(Context context) {
        mContext = context;
        mController = new SWAlertController(mContext, this, getWindow());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController.installContent();
    }

    public static class Builder {

        private final SWAlertController.AlertParams Params;

        public Builder(Context context) {
            Params = new SWAlertController.AlertParams(context);
        }

        public Builder setTitle(@StringRes int titleId) {
            Params.mTitle = Params.mContext.getString(titleId);
            return this;
        }

        public Builder setTitle(String title) {
            Params.mTitle = title;
            return this;
        }

        public Builder setMessage(@StringRes int messageId) {
            Params.mMessage = Params.mContext.getString(messageId);
            return this;
        }

        public Builder setMessage(String message) {
            Params.mMessage = message;
            return this;
        }

        public Builder setNegativeBtn(@StringRes int msgId, final View.OnClickListener listener) {
            Params.mNegativeBtnText = Params.mContext.getString(msgId);
            Params.mNegativeBtnListener = listener;
            return this;
        }

        public Builder setNegativeBtn(String msg, final View.OnClickListener listener) {
            Params.mNegativeBtnText = msg;
            Params.mNegativeBtnListener = listener;
            return this;
        }

        public Builder setPositiveBtn(@StringRes int msgId, final View.OnClickListener listener) {
            Params.mPositiveBtnText = Params.mContext.getString(msgId);
            Params.mPositiveBtnListener = listener;
            return this;
        }

        public Builder setPositiveBtn(String msg, final View.OnClickListener listener) {
            Params.mPositiveBtnText = msg;
            Params.mPositiveBtnListener = listener;
            return this;
        }

        public Builder isBackgroundDark(boolean isDark) {
            Params.mIsBackgroundDark = isDark;
            return this;
        }

        public SWAlertDialog create() {
            final SWAlertDialog dialog = new SWAlertDialog(Params.mContext, R.style.dialog);
//            final SWAlertDialog dialog = new SWAlertDialog(Params.mContext);
            Params.apply(dialog.mController);
            return dialog;
        }

        public void show() {
            final SWAlertDialog dialog = create();
            dialog.show();
        }

    }

}
