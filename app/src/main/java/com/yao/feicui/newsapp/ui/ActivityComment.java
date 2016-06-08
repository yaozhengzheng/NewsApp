package com.yao.feicui.newsapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yao.feicui.newsapp.R;



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
    private String buildTransaction(final String type){
        return (type==null)?String.valueOf(System.
                currentTimeMillis()):type+System.currentTimeMillis();
    }
    //向好友或朋友圈发送文本
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
             String text=editor.getText().toString();
                if (text==null||text.length()==0){
                    return;
                }

                //第一步：创建一个用于封装待分享文本的WXTextObject对象
                WXTextObject textObj=new WXTextObject();
                textObj.text=text;

                //第二步： 创建WXMediaMessage对象，该对象用于Android客户端向微信发送数据
                WXMediaMessage msg=new WXMediaMessage();
                msg.mediaObject=textObj;
                msg.description=text;//设置一个描述

                //第三步： 创建一个请求微信客户端的SendMessageToWX.Req对象
                SendMessageToWX.Req req=new SendMessageToWX.Req();
                req.message=msg;
                //设置唯一的标识
                req.transaction=buildTransaction("Text");
                //表示发送给朋友还是朋友圈
                req.scene=mShareFriends.isChecked()?
                        SendMessageToWX.Req.WXSceneTimeline:
                        SendMessageToWX.Req.WXSceneSession;

                // 第四步：发送给微信客户端
                Toast.makeText(ActivityComment.this,String.valueOf
                        (api.sendReq(req)), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消",null);
        final AlertDialog alert=builder.create();
        alert.show();


    }
}
