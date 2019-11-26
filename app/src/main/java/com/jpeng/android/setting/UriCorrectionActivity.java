package com.jpeng.android.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jpeng.android.R;
import com.jpeng.android.utils.system.MainActivity;

/**
 * @ClassName UriCorrectionActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:10
 */
public class UriCorrectionActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correction);
        //返回上一页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriCorrectionActivity.this.finish();
            }
        });
    }
}
