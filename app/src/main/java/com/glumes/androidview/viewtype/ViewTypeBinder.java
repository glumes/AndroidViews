package com.glumes.androidview.viewtype;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.glumes.androidview.R;
import com.glumes.androidview.databinding.ViewTypeItemLayoutBinding;
import com.glumes.databindingadapter.ViewHolderBinder;

/**
 * @author glumes
 */

public class ViewTypeBinder extends ViewHolderBinder<ViewTypeModel, ViewTypeHolder> {


    @Override
    public ViewTypeHolder createViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        ViewTypeItemLayoutBinding mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.view_type_item_layout, viewGroup, false);

        return new ViewTypeHolder(mBinding);

    }

    @Override
    public void onBindViewHolder(ViewTypeHolder viewTypeHolder, ViewTypeModel viewTypeModel, int position) {
        viewTypeHolder.onBind(viewTypeModel, position);
    }
}
