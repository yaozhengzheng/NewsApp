package com.yao.feicui.newsapp.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.yao.feicui.newsapp.FragmentMain;
import com.yao.feicui.newsapp.R;


import com.yao.feicui.newsapp.common.HttpURLConnectionUtil;
import com.yao.feicui.newsapp.common.bean.NewsBean;
import com.yao.feicui.newsapp.common.parse.NewsParse;
import com.yao.feicui.newsapp.ui.adapter.NewsAdapter;
import com.yao.feicui.newsapp.ui.base.MyBaseActivity;
import com.yao.feicui.newsapp.view.slidingmenu.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by 16245 on 2016/06/02.
 */
public class ActivityMain extends MyBaseActivity implements AdapterView.OnItemClickListener, ReFlashListView.IReFlashListener {
    private FragmentMenu fragmentMenu;
    private FragmentMenuRight fragmentMenuRight;
    private FragmentMain fragmentMain;
    public static SlidingMenu slidingMenu;
    private TextView textView_title;
    private ReFlashListView mListView;
    private ImageView iv_set, iv_user;
    private static int mWhat = 1;
    private NewsAdapter mAdapter;
    private ArrayList<NewsBean.DataBean> jsonList = new ArrayList<>();

    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        textView_title = (TextView) findViewById(R.id.textView1);
        iv_set = (ImageView) findViewById(R.id.imageView_set);
        iv_user = (ImageView) findViewById(R.id.imageView_user);
        mListView = (ReFlashListView) findViewById(R.id.lv_news_listview);
        mListView.setInterface(this);
        iv_set.setOnClickListener(onClickListener);
        iv_user.setOnClickListener(onClickListener);
        mListView.setOnItemClickListener(this);
//        showFragmentMain();
        idinitSlidingMenu();
        //解析json数据
        new Thread() {
            @Override
            public void run() {
                String json11 = "http://118.244.212.82:9092/newsClient/news_list?" +
                        "ver=4&subid=1&dir=1&nid=2&stamp=20150601&cnt=20";
                String json = HttpURLConnectionUtil.getHttpJson(json11);
                jsonList = NewsParse.parseNewsJson(json);
                handler.sendEmptyMessage(mWhat);
            }
        }.start();

    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mAdapter = new NewsAdapter(ActivityMain.this, jsonList, mListView);
                    mListView.setAdapter(mAdapter);
                    break;
            }

        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
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

    //listView设置监听事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onReFlash() {
        //获取最新数据

        // 通知界面显示数据

       //通知listView刷新数据完毕
      mListView.reFlashComplete();
    }
}
