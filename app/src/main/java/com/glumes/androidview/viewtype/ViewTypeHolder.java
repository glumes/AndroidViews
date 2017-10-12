package com.glumes.androidview.viewtype;

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
    protected void onBind(ViewTypeModel viewTypeModel) {
        mBinding.setViewmodle(viewTypeModel);
        mBinding.executePendingBindings();
        mBinding.viewtype.setOnClickListener();
    }
}
