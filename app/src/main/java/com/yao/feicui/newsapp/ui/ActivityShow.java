package com.yao.feicui.newsapp.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yao.feicui.newsapp.R;
import com.yao.feicui.newsapp.modle.modle.entity.News;
import com.yao.feicui.newsapp.ui.base.MyBaseActivity;


/**
 * 新闻具体页面
 * Created by 16245 on 2016/06/07.
 */
public class ActivityShow extends MyBaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private TextView tv_commentCount;
    private ImageView imageViewBack;
    private ImageView imageViewMenu;
    private News newsItem;

    private PopupWindow popupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tv_commentCount= (TextView) findViewById(R.id.textView2);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mWebView = (WebView) findViewById(R.id.webView1);
        imageViewBack = (ImageView) findViewById(R.id.imageView_back);
        imageViewMenu = (ImageView) findViewById(R.id.imageView_menu);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
//       mWebView.getSettings().getCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        WebChromeClient client=new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        };
        mWebView.setWebChromeClient(client);
        mWebView.loadUrl(url);
        tv_commentCount.setOnClickListener(clickListener);
        imageViewBack.setOnClickListener(clickListener);
        imageViewMenu.setOnClickListener(clickListener);

        initPopupWindow();
    }

    private void initPopupWindow() {
        View popView = getLayoutInflater().inflate(R.layout.item_pop_save, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView tv_saveLocal = (TextView) popView.findViewById(R.id.saveLocal);
        tv_saveLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView_back:
                    finish();
                    break;
                case R.id.textView2:
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("nid", newsItem.getNid());
                    openActivity(ActivityComment.class);
                    break;
                case R.id.imageView_menu:
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else if (popupWindow != null) {
                        popupWindow.showAsDropDown(imageViewMenu, 0, 12);
                    }
                    break;
            }
        }
    };
}
