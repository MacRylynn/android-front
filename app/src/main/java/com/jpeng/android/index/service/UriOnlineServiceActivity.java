package com.jpeng.android.index.service;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jpeng.android.R;
import com.jpeng.android.index.check.UriChooseAimAcyivity;
import com.jpeng.android.index.check.UriSingleCheckActivity;
import com.jpeng.android.utils.system.MainActivity;

import java.util.List;

/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */
public class UriOnlineServiceActivity extends Activity {
    private String phoneNumber = "18782930471";//这是默认的电话号码
    final String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=530454062&version=1";//这是默认的QQ 其中530454062是联系人的QQ号码

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlineservice);
        //返回到主页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriOnlineServiceActivity.this, MainActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });


        //点击之后直接拨打号码
        RelativeLayout btn1 = findViewById(R.id.relativeLayout1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);//设置活动类型// Intent.ACTION_DIAL: 激活拨号界面   Intent.ACTION_CALL: 直接拨打电话
                    intent.setData(Uri.parse("tel:" + phoneNumber));//设置数据
                    startActivity(intent);//开启意图
                } catch (Exception e) {
                    Toast.makeText(UriOnlineServiceActivity.this, "拨号失败，请检查是否启动电话权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //点击之后跳转到QQ聊天界面QQ号码设置在连接里面
        RelativeLayout btn = findViewById(R.id.relativeLayout2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQQClientAvailable(UriOnlineServiceActivity.this)) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qqUrl)));
                } else {
                    Toast.makeText(UriOnlineServiceActivity.this, "请安装QQ客户端", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //判断QQ客户端是否安装
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
