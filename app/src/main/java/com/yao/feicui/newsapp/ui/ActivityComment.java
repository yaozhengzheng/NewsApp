package com.yao.feicui.newsapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yao.feicui.newsapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * 分享微信界面
 * Created by 16245 on 2016/06/07.
 */
public class ActivityComment extends Activity {
    //应用程序ID
    public static final String APP_ID = "wx5daa14d439b06c07";
    //    调用API变量
    private IWXAPI api;
    private CheckBox mShareFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        //将APP_ID注册到微信中
        api.registerApp(APP_ID);
        mShareFriends = (CheckBox) findViewById(R.id.checkBox_share_friend);
    }

    //启动微信客户端
    public void onClick_weiXin(View view) {

        Toast.makeText(this, String.valueOf(api.openWXApp()), Toast.LENGTH_SHORT).show();
    }

    //请求生成一个唯一的标识
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.
                currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 向好友或朋友圈发送文本
     */

    public void onClick_send(View view) {
        //动态创建EditText，用于输入文本
        final EditText editor = new EditText(this);
        //设置布局
        editor.setLayoutParams(new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        // 设置默认的分享文本
        editor.setText("请输入要分享的文字！");
        // 创建dialog对象
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("共享文本");
        //将EditText与对话框绑定
        builder.setView(editor);
        builder.setMessage("请输入要分享的文本");
        builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获取待分享文本
                String text = editor.getText().toString();
                if (text == null || text.length() == 0) {
                    return;
                }

                //第一步：创建一个用于封装待分享文本的WXTextObject对象
                WXTextObject textObj = new WXTextObject();
                textObj.text = text;

                //第二步： 创建WXMediaMessage对象，该对象用于Android客户端向微信发送数据
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObj;
                msg.description = text;//设置一个描述

                //第三步： 创建一个请求微信客户端的SendMessageToWX.Req对象
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.message = msg;
                //设置唯一的标识
                req.transaction = buildTransaction("Text");
                //表示发送给朋友还是朋友圈
                req.scene = mShareFriends.isChecked() ?
                        SendMessageToWX.Req.WXSceneTimeline :
                        SendMessageToWX.Req.WXSceneSession;

                // 第四步：发送给微信客户端
                Toast.makeText(ActivityComment.this, String.valueOf
                        (api.sendReq(req)), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 压缩图片功能不能使用
     * @param bitmap
     * @param needRecycle
     * @return
     */
    //将bitmap装换成byte格式的数组
private byte[]bmpToByteArray(final Bitmap bitmap,final boolean needRecycle){
    ByteArrayOutputStream output=new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
    if (needRecycle){
        bitmap.recycle();
    }
    byte[]result=output.toByteArray();
    try {
        output.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}
    /**
     *  发送二进制图像
     */
    public void onClick_binary(View view){

        //第一步：获取二进制图像的Bitmap对象
        Bitmap bitmap= BitmapFactory.decodeResource
                (getResources(), R.drawable.city);
        Log.d("abc", String.valueOf(bitmap));
        //第二步：创建WXImageObject对象，并包装Bitmap
        WXImageObject imgObj=new WXImageObject(bitmap);

        //第三步：创建WXMediaMessage对象，并包装WXImageObject
        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=imgObj;

        //第四步：压缩图像
//        Bitmap thumbBmp=Bitmap.createScaledBitmap(bitmap,120,150,true);
//        //释放图像所占用的内存资源
//        bitmap.recycle();
//        //设置缩略图
//        msg.thumbData=bmpToByteArray(thumbBmp,true);
//        Log.d("abd", String.valueOf(thumbBmp));
        //第五步：创建SendMessageToWX.Req对象，用于发送数据
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        //发送图片标识
        req.transaction=buildTransaction("img");
        req.message=msg;
        //设置是否发送到朋友圈
        req.scene=mShareFriends.isChecked()?
                SendMessageToWX.Req.WXSceneTimeline:
                SendMessageToWX.Req.WXSceneSession;
        Toast.makeText(this, String.valueOf(api.sendReq(req)),
                Toast.LENGTH_LONG).show();
        finish();
    }
    /**
     * 发送本地图像
     */
    public void onClick_local(View view){
        //第一步：判断图像文件是否存在，并设置path
        String path="sdcard/DCIM/Camera/IMG_20160120_185917.jpg";
        File file=new File(path);
        if (!file.exists()){
            Toast.makeText(ActivityComment.this, "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        //第二步：创建WXImageObject对象，并包装Bitmap
        WXImageObject imgObj=new WXImageObject();
        //设置文件图像的路径
        imgObj.setImagePath(path);

        //第三步：创建WXMediaMessage对象，并包装WXImageObject
        WXMediaMessage msg=new WXMediaMessage();
        msg.mediaObject=imgObj;

        //第四步：压缩图像
        Bitmap bitmap=BitmapFactory.decodeFile(path);
//        Bitmap thumbBmp=Bitmap.createScaledBitmap(bitmap,120,150,true);
        //释放图像所占用的内存资源
//        bitmap.recycle();
        //设置缩略图
//        msg.thumbData=bmpToByteArray(thumbBmp,true);
//        Log.d("abd", String.valueOf(thumbBmp));
        //第五步：创建SendMessageToWX.Req对象，用于发送数据
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        //发送图片标识
        req.transaction=buildTransaction("img");
        req.message=msg;
        //设置是否发送到朋友圈
        req.scene=mShareFriends.isChecked()?
                SendMessageToWX.Req.WXSceneTimeline:
                SendMessageToWX.Req.WXSceneSession;
        Toast.makeText(this, String.valueOf(api.sendReq(req)),
                Toast.LENGTH_LONG).show();
        finish();
    }
  /*  *//**
     * 发送URL图像
     *//*
    public void onClick_url(View view){
        //安卓不能在主程序中访问网络，所以用线程
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
               try{
                   //第一步：创建WXImageObject对象，并设置URL
                   URL url=new URL("http://image.xinmin.cn/2011/09/26/20110926093910045512.jpg");
                   WXImageObject imgObj=new WXImageObject();
                   //设置文件图像的Url
                   //第三步：创建WXMediaMessage对象，并包装WXImageObject
                   WXMediaMessage msg=new WXMediaMessage();
                   msg.mediaObject=imgObj;

                   //第五步：创建SendMessageToWX.Req对象，用于发送数据
                   SendMessageToWX.Req req=new SendMessageToWX.Req();
                   //发送图片标识
                   req.transaction=buildTransaction("img");
                   req.message=msg;
                   //设置是否发送到朋友圈
                   req.scene=mShareFriends.isChecked()?
                           SendMessageToWX.Req.WXSceneTimeline:
                           SendMessageToWX.Req.WXSceneSession;
                   Toast.makeText(ActivityComment.this, String.valueOf(api.sendReq(req)),Toast.LENGTH_LONG).show();
                   finish();
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
    }*/
    /**
     * 发送URL音频
     */
    public void send_url_audio(View view){
        //第一步：创建WXMusicObject对象，用来指定url音频
        WXMusicObject music=new WXMusicObject();
        music.musicUrl="http://music.baidu.com/song/999104?pst=sug";
       //第二步： 创建WXMediaMessage对象
        WXMediaMessage message=new WXMediaMessage();
        message.mediaObject=music;
        message.title="向天再借五百年";
        message.description="演唱：韩磊";
        //创建 SendMessageToWX.Req对象
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        req.transaction=buildTransaction("music");
        req.message=message;
        req.scene=mShareFriends.isChecked()?
                SendMessageToWX.Req.WXSceneTimeline:
                SendMessageToWX.Req.WXSceneSession;
        Toast.makeText(this, String.valueOf(api.sendReq(req)),
                Toast.LENGTH_LONG).show();
        finish();
    }
}
