package com.jpeng.android.index.check;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jpeng.android.R;
import com.jpeng.android.utils.system.MainActivity;


import static com.jpeng.android.R.*;

/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */

public class UriChooseAimAcyivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.chooseaim);
        //跳转到检测页面
        Button btn = (Button) findViewById(R.id.choose);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriChooseAimAcyivity.this, UriCheckActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
        //返回到检测主页
        TextView textView = findViewById(id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriChooseAimAcyivity.this, MainActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });

    }
}
