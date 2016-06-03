package com.yao.feicui.newsapp.ui.base;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by 16245 on 2016/06/02.
 */
public class MyBaseActivity extends FragmentActivity{
    private Toast toast;
    public static int screenW, screenH;
    public Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        screenW = getWindowManager().getDefaultDisplay().getWidth();
        screenH = getWindowManager().getDefaultDisplay().getHeight();
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    public void openActivity(Class<?>pClass, Bundle bundle, Uri uri){
        Intent intent=new Intent(this, pClass);
        if(bundle!=null)
            intent.putExtras(bundle);
        if(uri!=null)
            intent.setData(uri);
        startActivity(intent);
    }
    //通过 class 名字进行界面跳转只带 Bundle 数据
    public void openActivity(Class<?> pClass,Bundle bundle){
        openActivity(pClass, bundle, null);
    }
    //通过 class 名字进行界面跳转
    public void openActivity(Class<?> pClass){
        openActivity(pClass, null, null);
    }
    //通过 action 字符串进行界面跳转
    public void openActivity(String action){
        openActivity(action, null, null);
    }
    //通过 action 字符串进行界面跳转，只带 Bundle 数据
    public void openActivity(String action,Bundle bundle){
        openActivity(action, bundle, null);
    }
    //通过 action 字符串进行界面跳转，并带 Bundle 和 Url 数据
    public void openActivity(String action,Bundle bundle,Uri uri){
        Intent intent=new Intent(action);
        if(bundle!=null)
            intent.putExtras(bundle);
        if(uri!=null)
            intent.setData(uri);
        startActivity(intent);
    }
    /**
     * 公共功能封装
     */
    public void showToast(int resId){
        showToast(getString(resId));
    }
    public void showToast(String msg){
        if(toast==null)
            toast=Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
}
