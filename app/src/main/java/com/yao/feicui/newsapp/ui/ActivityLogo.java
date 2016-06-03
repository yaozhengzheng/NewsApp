package com.yao.feicui.newsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.yao.feicui.newsapp.R;
import com.yao.feicui.newsapp.ui.base.MyBaseActivity;

/**
 * Created by 16245 on 2016/06/02.
 */
public class ActivityLogo extends MyBaseActivity {

    private ImageView mLogo;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        mLogo = (ImageView) findViewById(R.id.iv_logo);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.logo);
//        mLogo.startAnimation(mAnimation);
//        Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(ActivityLogo.this,ActivityMain.class));
//
//            }
//        },3000);
        mAnimation.setFillAfter(true);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            //动画启动时调用
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画重复式调用
            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            //动画结束时调用
            @Override
            public void onAnimationEnd(Animation animation) {
                openActivity(ActivityMain.class);

                ActivityLogo.this.finish();
            }
        });
        mLogo.setAnimation(mAnimation);
    }
}
