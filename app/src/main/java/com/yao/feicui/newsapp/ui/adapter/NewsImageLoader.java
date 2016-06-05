package com.yao.feicui.newsapp.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 16245 on 2016/06/05.
 */
public class NewsImageLoader {
    private ImageView mImageView;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImageView.setImageBitmap((Bitmap) msg.obj);
        }
    };
    public void showImageByThread(ImageView imageView, final String url) {
        mImageView=imageView;
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromUrl(url);
                Message message=Message.obtain();
                message.obj=bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }
    public Bitmap getBitmapFromUrl(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(connection.getInputStream());
            //通过工厂方法将is传递进来
            bitmap= BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
