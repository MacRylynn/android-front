package com.jpeng.android.index.logs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jpeng.android.R;
import com.jpeng.android.utils.ShareData;

/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */
public class UriSingleLogActivity extends Activity {
    //界面上的组件
    private static final int[] values = {R.id.value0, R.id.value1, R.id.value2, R.id.value3,
            R.id.value4, R.id.value5, R.id.value6, R.id.value7,
            R.id.value8, R.id.value9, R.id.value10, R.id.value11};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlelog);
        String result = ((ShareData) getApplication()).getCheckResult();
        String checkTime = ((ShareData) getApplication()).getCheckTime();
        //标题的改变
        Button topic = findViewById(R.id.button);
        topic.setText(checkTime + "-" + "检测结果");
        //结果的呈现
        showResults(getEveryValue(result));
        //返回上一页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriSingleLogActivity.this, UriCheckLogsActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
    }

    /**
     * 根据传进来的结果显示到界面上对应的组件上
     *
     * @param results
     */
    private void showResults(String[] results) {
        for (int i = 0; i < results.length; i++) {
            TextView textView = findViewById(values[i]);
            textView.setText(results[i]);
        }
    }

    /**
     * 根据原始的结果字符串得到每一级结果
     *
     * @param result
     * @return
     */
    private String[] getEveryValue(String result) {
        String[] everyValue = new String[12];
        JSONArray jsonArray = JSON.parseArray(result);
        for (int i = 0; i < jsonArray.size(); i++) {
            everyValue[i] = jsonArray.getString(i);
        }
        return everyValue;
    }


}
