package com.yao.feicui.newsapp.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yao.feicui.newsapp.R;

/**
 * 左边侧滑界面
 * Created by 16245 on 2016/06/02.
 */
public class FragmentMenu extends Fragment {
    private RelativeLayout[] rls = new RelativeLayout[5];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //利用回调中的参数 LayoutInflater 对象导入布局文件，并发挥此 View
        View view = inflater.inflate(R.layout.fragment_menu_left, container, false);
        rls[0] = (RelativeLayout) view.findViewById(R.id.rl_news);
        rls[1] = (RelativeLayout) view.findViewById(R.id.rl_reading);
        rls[2] = (RelativeLayout) view.findViewById(R.id.rl_local);
        rls[3] = (RelativeLayout) view.findViewById(R.id.rl_commnet);
        rls[4] = (RelativeLayout) view.findViewById(R.id.rl_photo);
        for (int i = 0; i < rls.length; i++) {
            rls[i].setOnClickListener(OnClickListener);
        }

        return view;
    }

    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < rls.length; i++) {
                rls[0].setBackgroundColor(0);
            }
            switch (getId()) {
                case R.id.rl_news:
                    rls[0].setBackgroundColor(0x33c85555);
//                    ((ActivityMain) getActivity()).showFragmentMain();
                    break;
                case R.id.rl_reading:
                    rls[1].setBackgroundColor(0x33c85555);
                 //跳转收藏界面
                    break;
                case R.id.rl_local:
                    rls[2].setBackgroundColor(0x33c85555);
                    break;
                case R.id.rl_commnet:
                    rls[3].setBackgroundColor(0x33c85555);
                    break;
                case R.id.rl_photo:
                    rls[4].setBackgroundColor(0x33c85555);
                    break;
            }
        }
    };
}
