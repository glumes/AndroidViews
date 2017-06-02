package com.glumes.androidview.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zhaoying on 2017/6/2.
 */

public class DisplayUtil {
    /**
     * 将 px 值转换为 dip 或 dp 值,并保证尺寸大小不变
     *
     * @param context
     * @param pxValue DisplayMetrics 类中属性 density
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将 dip 或 dp 值转换为 px 值,保证尺寸大小不变
     *
     * @param context
     * @param dipValue DisplayMetrics 类中属性 density
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将 px 值转换为 sp 值,保证文字大小不变
     *
     * @param context
     * @param pxValue DisplayMetrics 类中属性 scaledDensity
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将 sp 值转换为 px 值,保证文字大小不变
     *
     * @param context
     * @param spValue DisplayMetrics 类中属性 scaledDensity
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 使用系统自带的 TypedValue 类将 dp 转换成 px
     *
     * @param context
     * @param dp
     * @return
     */
    protected int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * 使用系统自带的 TypedValue 类将 sp 转换成 px
     *
     * @param context
     * @param sp
     * @return
     */
    protected int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources()
                .getDisplayMetrics());
    }
}
