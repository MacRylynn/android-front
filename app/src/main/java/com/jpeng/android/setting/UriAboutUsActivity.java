package com.jpeng.android.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jpeng.android.R;
import com.jpeng.android.index.logs.UriCheckLogsActivity;
import com.jpeng.android.index.logs.UriSingleLogActivity;
import com.jpeng.android.utils.system.MainActivity;
import com.jpeng.android.utils.system.Tab4Pager;


/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */

public class UriAboutUsActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        //返回上一页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriAboutUsActivity.this.finish();
            }
        });

    }


}
