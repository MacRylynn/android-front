package com.jpeng.android.index.logs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jpeng.android.R;
import com.jpeng.android.index.check.UriCheckActivity;
import com.jpeng.android.index.check.UriChooseAimAcyivity;
import com.jpeng.android.utils.system.MainActivity;


/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */
public class UriChoosePersonAvtivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseperson);
        //跳转到检测记录页面
        Button btn = findViewById(R.id.choose);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriChoosePersonAvtivity.this, UriCheckLogsActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
        //返回到记录主页
         TextView textView = findViewById(R.id.textView8) ;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriChoosePersonAvtivity.this, MainActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });

    }
}
