package com.jpeng.android.index.check;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jpeng.android.R;
import com.jpeng.android.index.logs.UriCheckLogsActivity;
import com.jpeng.android.index.logs.UriSingleLogActivity;
import com.jpeng.android.utils.MyDatabaseHelper;
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriCheckResultReq;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */

public class UriSingleCheckActivity extends Activity {
    private static final String baseUrl = "http://120.77.221.43:8082/web/check/selectrecording";
    //界面上的组件
    private static final int[] values = {R.id.resultv0, R.id.resultv1, R.id.resultv2, R.id.resultv3,
            R.id.resultv4, R.id.resultv5, R.id.resultv6, R.id.resultv7,
            R.id.resultv8, R.id.resultv9, R.id.resultv10, R.id.resultv11};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlecheck);
        //得到上次的检测结果的id
        final long checkId = ((ShareData) getApplication()).getCheckId();
        //返回到家属选择页面
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriSingleCheckActivity.this, UriChooseAimAcyivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
        //直接显示检测结果
        showResults(getEveryValue(getCheckRecord(checkId)));
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

    private String getCheckRecord(long checkId) {
        String result = "";
        MediaType mediaType = MediaType.parse("application/json");
        UriCheckResultReq uriCheckResultReq = new UriCheckResultReq();
        uriCheckResultReq.setId(checkId);
        CommonRequest<UriCheckResultReq> commonRequest = new CommonRequest<UriCheckResultReq>();
        commonRequest.setRequestData(uriCheckResultReq);
        final Request request = new Request.Builder()
                .url(baseUrl)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            JSONObject jsonObject = JSON.parseObject(JSON.parseObject(response.body().string()).getString("resultData"));
            result = jsonObject.getString("checkResult");
        } catch (Exception e) {
            Toast.makeText(UriSingleCheckActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return result;
    }
}

