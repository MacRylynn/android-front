package com.jpeng.android.utils;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jpeng.android.CheckActivity;
import com.jpeng.android.R;
import com.jpeng.android.ServiceOnlineActivity;
import com.jpeng.android.TestRecordActivity;
import com.jpeng.android.VedioTestActivity;

/**
 * 第一个主页面 里面包括检测、检测记录、在线客服
 */
public class Tab1Pager extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activityone, null);
        init(layout);
        //跳转到检测页面
        Button btn = (Button) layout.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckActivity.class);//从implements跳转到其他的Activity
                startActivity(intent);
            }
        });
        //跳转到检测记录页面
        Button btn2 = (Button) layout.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestRecordActivity.class);
                startActivity(intent);
            }
        });
        //跳转到在线客服页面
        Button btn3 = (Button) layout.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ServiceOnlineActivity.class);
                startActivity(intent);
            }
        });
        Button btn4 = (Button) layout.findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VedioTestActivity.class);
                startActivity(intent);
            }
        });

        return layout;
    }

    //启用APP时的初始化
    private void init(View layout) {

    }

    @Override
    public void onClick(View v) {

    }

}
