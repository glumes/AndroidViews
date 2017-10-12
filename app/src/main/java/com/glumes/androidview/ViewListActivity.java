package com.glumes.androidview;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.glumes.androidview.databinding.ActivityViewListBinding;
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


    }
}
