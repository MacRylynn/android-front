package com.jpeng.android.utils.system;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jpeng.android.setting.UriAboutUsActivity;
import com.jpeng.android.setting.UriFamilyActivity;
import com.jpeng.android.R;
import com.jpeng.android.setting.UriSettingActivity;


/**
 * 主页的第四个主页面，里面包括登录和注册、设置、关于我们
 */
public class Tab4Pager extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.setting, null);

        //跳转到设置页面
        Button btn2 = (Button) layout.findViewById(R.id.button5);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UriSettingActivity.class);
                startActivity(intent);
            }
        });
        //跳转到家庭成员页面
        Button btn3 = (Button) layout.findViewById(R.id.button6);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UriFamilyActivity.class);
                startActivity(intent);
            }
        });
        //跳转到关于我们页面
        Button btn4 = (Button) layout.findViewById(R.id.button7);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UriAboutUsActivity.class);
                startActivity(intent);
            }
        });
        return layout;
    }

    @Override
    public void onClick(View view) {
    }

}
