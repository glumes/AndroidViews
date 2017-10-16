package com.glumes.androidview.viewtype;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.glumes.androidview.BezierFlowerActivity;
import com.glumes.androidview.path.PathActivity;
import com.glumes.androidview.util.Constants;


/**
 * @author glumes
 */

public class EventHandler {

    public void onViewTypeClick(View view, ViewTypeModel model) {
        switch (model.type) {
            case Constants.BEZIER_BUBBLE:
                startActivity(view.getContext(), PathActivity.class);
                break;
            case Constants.BEZIER_FLOWER:
                startActivity(view.getContext(), BezierFlowerActivity.class);
                break;
            default:
                Toast.makeText(view.getContext(), "传入类型", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startActivity(Context context, Class<?> clazz) {
        context.startActivity(new Intent(context, clazz));
    }

}
