package com.jpeng.android.utils.system;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.jpeng.android.setting.UriAboutUsActivity;
import com.jpeng.android.setting.UriCorrectionActivity;
import com.jpeng.android.setting.UriFamilyActivity;
import com.jpeng.android.R;
import com.jpeng.android.setting.UriModifyAccountActivity;


/**
 * 主页的第四个主页面，里面包括登录和注册、设置、关于我们
 */
public class Tab4Pager extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.setting, null);

        //跳转到家庭信息设置页面
        TableRow btn1 = (TableRow) layout.findViewById(R.id.tablerow1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UriFamilyActivity.class);
                startActivity(intent);
            }
        });
        //跳转到手机相机校正页面
        TableRow btn2 = (TableRow) layout.findViewById(R.id.tablerow2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UriCorrectionActivity.class);
                startActivity(intent);
            }
        });

        //跳转到修改账号信息页面
        TableRow btn3 = (TableRow) layout.findViewById(R.id.tablerow3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UriModifyAccountActivity.class);
                startActivity(intent);
            }
        });
        //跳转到关于我们页面
        TableRow btn4 = (TableRow) layout.findViewById(R.id.tablerow4);
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
