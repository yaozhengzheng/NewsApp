package com.yao.feicui.newsapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;


import com.tencent.stat.StatService;
import com.yao.feicui.newsapp.R;
import com.yao.feicui.newsapp.common.SharedPreferenceUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        StatService.trackCustomEvent(this, "onCreate", "");
        //判断是否是第一次登录
        if (TextUtils.isEmpty(SharedPreferenceUtils.getSP(this, "FirstLand"))) {
            initViewPager(); //初次登陆时的图片翻页效果
            SharedPreferenceUtils.saveSP(this, "FirstLand", "true");
        } else {
            startActivity(new Intent(ActivityLead.this,ActivityLogo.class));
            finish();
        }
    }
private void initViewPager(){
    viewpager = (ViewPager) findViewById(R.id.viewpager);
    viewpager.setOnPageChangeListener(listener);
    //设置每一个界面的样式
    List<View> viewList = new ArrayList<>();
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
        public void onPageScrolled(int arg0,float arg1, int arg2) {
        }
//当滑动到最后一张图片的时候，2秒后跳转至动画页面
        @Override
        public void onPageSelected(int arg0) {
            setPoint(arg0);
            if (arg0 == 3) {
//                openActivity(ActivityLogo.class);
//                finish();
                final Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        startActivity(new Intent(ActivityLead.this,ActivityLogo.class));
                        finish();
                    }
                };
           Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        super.run();
                    }
                };
                thread.start();
            }
        }
        //滑动状态变化时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
