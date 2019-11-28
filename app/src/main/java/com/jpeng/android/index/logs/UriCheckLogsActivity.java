package com.jpeng.android.index.logs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jpeng.android.R;
import com.jpeng.android.utils.MyDatabaseHelper;
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriUserInfoReq;
import com.jpeng.android.utils.domain.response.UriCheckResultVo;
import com.jpeng.android.utils.domain.response.UriUserInfoVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

public class UriCheckLogsActivity extends Activity {

    private static final String baseUrl = "http://120.77.221.43:8082/web/check/selectrecordings";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklogs);
        //申明政策允许所有的操作（为了从服务端同步获取信息）
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //从服务端获取数据
        long userId = ((ShareData) getApplication()).getUserId();
        List<UriCheckResultVo> list = getRecords(userId);
        //创建UI
        createUI(list);
        //返回家属选择页面
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriCheckLogsActivity.this, UriChoosePersonAvtivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
    }

    /**
     * 根据userId查询检测记录
     *
     * @param userId
     * @return
     */
    private List<UriCheckResultVo> getRecords(long userId) {
        List<UriCheckResultVo> list = new ArrayList<>();
        MediaType mediaType = MediaType.parse("application/json");
        UriUserInfoReq uriUserInfoReq = new UriUserInfoReq();
        uriUserInfoReq.setId(userId);
        CommonRequest<UriUserInfoReq> commonRequest = new CommonRequest<>();
        commonRequest.setRequestData(uriUserInfoReq);
        final Request request = new Request.Builder()
                .url(baseUrl)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            list = selectCheckRecords(response.body().string());
        } catch (Exception e) {
            Toast.makeText(UriCheckLogsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        for (UriCheckResultVo uriCheckResultVo : list) {
            System.out.println(uriCheckResultVo.toString());
        }
        return list;
    }

    /**
     * 将Response 转为结果
     *
     * @param response
     * @return
     */
    private List<UriCheckResultVo> selectCheckRecords(String response) {
        List<UriCheckResultVo> resList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(JSON.parseObject(response).getString("resultData"));
        if (jsonArray == null || jsonArray.size() <= 0) {
            System.out.println("数据返回失败或者没有记录！");
            return resList;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            UriCheckResultVo uriCheckResultVo = new UriCheckResultVo();
            uriCheckResultVo.setCheckResult(jsonArray.getJSONObject(i).getString("checkResult"));
            uriCheckResultVo.setCheckTime(jsonArray.getJSONObject(i).getDate("checkTime"));
            uriCheckResultVo.setId(jsonArray.getJSONObject(i).getLong("id"));
            uriCheckResultVo.setResultImagePath(jsonArray.getJSONObject(i).getString("resultImagePath"));
            uriCheckResultVo.setUserId(jsonArray.getJSONObject(i).getLong("userId"));
            resList.add(uriCheckResultVo);
        }
        return resList;
    }

    /**
     * 根据记录数创建UI
     *
     * @param list
     */
    private void createUI(List<UriCheckResultVo> list) {
        LinearLayout linearLayout = findViewById(R.id.line1);
        for (final UriCheckResultVo uriCheckResultVo : list) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            final String time = format.format(uriCheckResultVo.getCheckTime());
            Button button = new Button(UriCheckLogsActivity.this);
            button.setWidth(200);
            button.setHeight(20);
            button.setTextSize(15);
            button.setText(time + "   " + uriCheckResultVo.getCheckResult());
            //跳转到检测页面
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 按钮会将检测结果和检测时间携带到详情记录里面
                    ((ShareData) getApplication()).setCheckResult(uriCheckResultVo.getCheckResult());
                    ((ShareData) getApplication()).setCheckTime(time);
                    //从Activity跳转到Activity
                    Intent intent = new Intent(UriCheckLogsActivity.this, UriSingleLogActivity.class);
                    startActivity(intent);
                }
            });
            linearLayout.addView(button);
        }
    }
}
