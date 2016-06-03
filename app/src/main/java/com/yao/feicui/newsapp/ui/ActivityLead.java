package com.yao.feicui.newsapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;


import com.yao.feicui.newsapp.R;
import com.yao.feicui.newsapp.ui.adapter.LeadImgAdapter;
import com.yao.feicui.newsapp.ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16245 on 2016/06/02.
 */
public class ActivityLead extends MyBaseActivity {
    private ViewPager viewpager;
    private ImageView[] points = new ImageView[4];
    private LeadImgAdapter adapter;

    private void setPoint(int index) {
        for (int i = 0; i < points.length; i++) {
            if (i == index) {
                points[i].setAlpha(255);
            } else {
                points[i].setAlpha(100);
            }
        }
    }

    //当界面切换后调用
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        //当界面切换时调用
        @Override
        public void onPageScrolled(int arg0,
                                   float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            setPoint(arg0);
            if (arg0 >= 3) {
                openActivity(ActivityLogo.class);
                finish();
                //判断是否是第一次运行
                SharedPreferences preferences=
                        getSharedPreferences("runconfig",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("isFirstRun",false);
                editor.commit();
            }
        }



        //滑动状态变化时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setOnPageChangeListener(listener);
        //设置每一个界面的样式
        List<View> viewList = new ArrayList<View>();
        viewList.add(getLayoutInflater().inflate(R.layout.lead_1, null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_2, null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_3, null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_4, null));
        points[0] = (ImageView) findViewById(R.id.iv_p1);
        points[1] = (ImageView) findViewById(R.id.iv_p2);
        points[2] = (ImageView) findViewById(R.id.iv_p3);
        points[3] = (ImageView) findViewById(R.id.iv_p4);
        setPoint(0);
        //初始化适配器
        adapter = new LeadImgAdapter(viewList);
        //设置适配器
        viewpager.setAdapter(adapter);

    }
}
