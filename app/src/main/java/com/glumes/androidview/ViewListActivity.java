package com.glumes.androidview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.glumes.androidview.databinding.ActivityViewListBinding;
import com.glumes.androidview.util.Constants;
import com.glumes.androidview.viewtype.ViewTypeBinder;
import com.glumes.androidview.viewtype.ViewTypeModel;
import com.glumes.databindingadapter.DataBindingAdapter;
import com.glumes.databindingadapter.Items;


public class ViewListActivity extends AppCompatActivity {


    ActivityViewListBinding mListBinding;

    Items mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_list);
        mListBinding.recyclerview.setHasFixedSize(true);
        mListBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItems = new Items();

        DataBindingAdapter dataBindingAdapter = new DataBindingAdapter();
        dataBindingAdapter.map(ViewTypeModel.class, new ViewTypeBinder()).setItems(mItems);

        mListBinding.recyclerview.setAdapter(dataBindingAdapter);

        mItems.add(new ViewTypeModel("伸缩气泡效果", Constants.BEZIER_BUBBLE));
        mItems.add(new ViewTypeModel("贝塞尔送花效果", Constants.BEZIER_FLOWER));
    }
}
