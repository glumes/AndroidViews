package com.glumes.androidview.customtoast;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.glumes.androidview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomToastActivity extends AppCompatActivity {

    @BindView(R.id.btnToast)
    Button btnToast;

    Context mContext;
    @BindView(R.id.btnNormalToast)
    Button btnNormalToast;
    @BindView(R.id.btnDialog)
    Button btnDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toast);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.btnToast, R.id.btnNormalToast, R.id.btnDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnToast:
                ToastUtil toastUtil = new ToastUtil.Builder(mContext).setContent(R.string.text_content)
                        .create();
                toastUtil.show();
                break;
            case R.id.btnNormalToast:
                Toast.makeText(mContext, "normal content", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnDialog:
                SWAlertDialog dialog = new SWAlertDialog.Builder(CustomToastActivity.this)
                        .setTitle(R.string.clear_record_title)
                        .setMessage(R.string.clear_record_message)
                        .setNegativeBtn(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveBtn(R.string.confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .create();

                dialog.show();

        }
    }


}
