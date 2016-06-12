package com.yao.feicui.newsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 16245 on 2016/06/12.
 */
public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
            Bundle bundle=intent.getExtras();
            String title=bundle.getString(JPushInterface.EXTRA_TITLE);
            String message=bundle.getString(JPushInterface.EXTRA_MESSAGE);

            Toast.makeText(context,
                    "Message title:"+title+"content:"+message,Toast.LENGTH_LONG).show();
        }

    }
}
