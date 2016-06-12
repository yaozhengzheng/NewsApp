package com.yao.feicui.newsapp.ui;
/**
 * 下拉刷新
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yao.feicui.newsapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 16245 on 2016/06/06.
 */
public class ReFlashListView extends ListView implements AbsListView.OnScrollListener {

    View header;//定义布局文件
    int headerHeight;//顶部布局文件的高度
    int firstVisibleItem; //当前第一个可见的item位置
    int scrollState;//listView当前滚动状态
    boolean isRemark;//标记当前是在listView最顶端摁下的
    int startY; //摁下时的Y值

    int state;//当前的状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//刷新状态
    IReFlashListener ireFlashListener;//刷新数据的接口
    public ReFlashListView(Context context) {
        super(context);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化界面，添加顶部文件到listView中
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        Log.d("tag", "headerHeight: " + headerHeight);
        TopPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }

    /**
     * 通知父布局占用的宽，高
     *
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
            int height;
            int tempHeight = p.height;
            if (tempHeight > 0) {
                height = MeasureSpec.makeMeasureSpec(tempHeight,
                        MeasureSpec.EXACTLY);
            } else {
                height = MeasureSpec.makeMeasureSpec(0,
                        MeasureSpec.UNSPECIFIED);
            }
            view.measure(width, height);
        }
    }

    /**
     * 设置header布局，上边距
     *
     * @param topPadding
     */
    private void TopPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    //加载最新数据
                    reFlashViewByState();
                    ireFlashListener.onReFlash();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reFlashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断移动过程中的操作
     *
     * @param ev
     */
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();//移动的位置
        int space = tempY - startY;
        int topPadding = space - headerHeight;//顶部的位置一点一点显示出来
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reFlashViewByState();
                }
                break;
            case PULL:
                TopPadding(topPadding);
                if (space > headerHeight + 30 &&
                        scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reFlashViewByState();
                }
                break;
            case RELESE:
                TopPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reFlashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reFlashViewByState();
                }
                break;
        }
    }

    /**
     * 根据当前状态更改界面显示
     */
    private void reFlashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progressbar);
//        添加箭头动画效果
        RotateAnimation animation = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        RotateAnimation animation1 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation1.setDuration(500);
        animation1.setFillAfter(true);
        switch (state) {
            case NONE:
                arrow.clearAnimation();
                TopPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(animation1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(animation);
                break;
            case REFLASHING:
                TopPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新");
                arrow.clearAnimation();
                break;
        }
    }

    /**
     * 获取完数据
     */
    public void reFlashComplete() {
        state = NONE;
        isRemark = false;
        reFlashViewByState();
        TextView lastUpdateTime = (TextView) header.findViewById(R.id.lastUpdate_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastUpdateTime.setText(time);
    }

    public void setInterface(IReFlashListener ireFlashListener) {
        this.ireFlashListener=ireFlashListener;

    }

    /**
     * 刷新数据接口0
     */
    public interface IReFlashListener {
        public void onReFlash();

    }
}
