package com.iitbhu.technex18;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.iitbhu.technex18.adapter.FullScreenImageAdapter;
import com.liuguangqiang.swipeback.SwipeBackActivity;

/**
 * Created by abhinav on 10/10/17.
 */

public class FullScreenViewActivity extends SwipeBackActivity {

//    private Utils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
//        setDragEdge(SwipeBackLayout.DragEdge.TOP);
//        setDragEdge(SwipeBackLayout.DragEdge.BOTTOM);

        viewPager = (ViewPager) findViewById(R.id.pager);


        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        int w=getWindowManager().getDefaultDisplay().getWidth();
        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,w);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }


}