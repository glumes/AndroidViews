package com.glumes.androidview.viewtype;

import android.view.View;

import com.glumes.androidview.databinding.ViewTypeItemLayoutBinding;
import com.glumes.databindingadapter.BindingViewHolder;

/**
 * @author glumes
 */

public class ViewTypeHolder extends BindingViewHolder<ViewTypeModel, ViewTypeItemLayoutBinding> {


    public ViewTypeHolder(ViewTypeItemLayoutBinding binding) {
        super(binding);
    }

    @Override
    protected void onBind(ViewTypeModel viewTypeModel, int position) {
        mBinding.executePendingBindings();
        mBinding.setViewmodel(viewTypeModel);
        mBinding.setEventHandler(new EventHandler());
        mBinding.viewtype.setClickable(true);
    }

}
