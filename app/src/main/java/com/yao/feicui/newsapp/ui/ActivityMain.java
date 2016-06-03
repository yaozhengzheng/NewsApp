package com.yao.feicui.newsapp.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.yao.feicui.newsapp.FragmentMain;
import com.yao.feicui.newsapp.R;


import com.yao.feicui.newsapp.ui.base.FragmentMenuRight;
import com.yao.feicui.newsapp.ui.base.MyBaseActivity;
import com.yao.feicui.newsapp.view.slidingmenu.SlidingMenu;

/**
 * Created by 16245 on 2016/06/02.
 */
public class ActivityMain extends MyBaseActivity {
    private FragmentMenu fragmentMenu;
    private FragmentMenuRight fragmentMenuRight;
    private FragmentMain fragmentMain;
    public static SlidingMenu slidingMenu;
    private TextView textView_title;
    private ImageView iv_set, iv_user;

    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        textView_title = (TextView) findViewById(R.id.textView1);
        iv_set = (ImageView) findViewById(R.id.imageView_set);
        iv_user = (ImageView) findViewById(R.id.imageView_user);
        iv_set.setOnClickListener(onClickListener);
        iv_user.setOnClickListener(onClickListener);
//        showFragmentMain();
        idinitSlidingMenu();

    }
private View.OnClickListener onClickListener=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_set:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showMenu();
                }
                break;
            case R.id.imageView_user:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showSecondaryMenu();
                }
                break;
        }
    }
};
    //初始化侧滑菜单
    public void idinitSlidingMenu() {
        fragmentMenu = new FragmentMenu();
        fragmentMenuRight = new FragmentMenuRight();
        slidingMenu = new SlidingMenu(this);
        //设置侧拉栏为双向测拉栏
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置左右菜单栏的布局
        slidingMenu.setMenu(R.layout.layout_menu);
        slidingMenu.setSecondaryMenu(R.layout.layout_menu_right);

        getFragmentManager().beginTransaction()
                .replace(R.id.layout_menu, fragmentMenu).commit();
        getFragmentManager().beginTransaction()
                .replace(R.id.layout_menu_right, fragmentMenuRight).commit();

    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        } else {
            exitTwice();
        }
    }

    //两次退出
    private boolean isFirstExit = true;

    private void exitTwice() {
        if (isFirstExit) {
            Toast.makeText(this, "再按一次退出！", Toast.LENGTH_SHORT).show();
            isFirstExit = false;
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                        isFirstExit = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            finish();
        }
    }
}
