package com.yao.feicui.newsapp.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yao.feicui.newsapp.R;

/**
 * Created by 16245 on 2016/06/02.
 */
public class FragmentMenuRight extends Fragment{
    private View view;
    private RelativeLayout relativeLayout_logined;
    private RelativeLayout relativeLayout_unlogin;
    private ImageView imageView1;
    private TextView textView1, updateTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//    利用回调中的参数 LayoutInflater 对象导入布局文件，并发挥此 View
        View view = inflater.inflate(R.layout.fragment_menu_right, container, false);
        relativeLayout_unlogin= (RelativeLayout) view.findViewById(R.id.relativelayout_unlogin);
        relativeLayout_logined= (RelativeLayout) view.findViewById(R.id.relativelayout_logined);
        imageView1= (ImageView) view.findViewById(R.id.imageView1);
        textView1= (TextView) view.findViewById(R.id.textView1);
        updateTv= (TextView) view.findViewById(R.id.update_version);
        return view;
    }

}
