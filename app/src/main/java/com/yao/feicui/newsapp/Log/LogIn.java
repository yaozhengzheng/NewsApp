package com.yao.feicui.newsapp.Log;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.yao.feicui.newsapp.R;

/**
 * Created by 16245 on 2016/06/07.
 */
public class LogIn extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.login_layout);
    }
}
