package com.jpeng.android.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jpeng.android.R;
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;
import com.jpeng.android.utils.system.MainActivity;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName UriModifyAccountActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:11
 */
public class UriModifyAccountActivity extends Activity {
    private static final String baseUrl = "http://120.77.221.43:8082/web/user/modifyaccount";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyaccount);
        //申明政策允许所有的操作（为了从服务端同步获取信息）
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //返回上一页
        TextView textView = findViewById(R.id.textView4);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriModifyAccountActivity.this.finish();
            }
        });
        //确认修改
        final long accountId = ((ShareData) getApplication()).getAccountId();
        Button btn = findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = findViewById(R.id.editText2);
                EditText editText2 = findViewById(R.id.editText3);
                modifyAccount(accountId, editText1.getText().toString(), editText2.getText().toString());
            }
        });
    }

    /**
     * 修改账号密码
     *
     * @param accountId      账号id
     * @param password       密码
     * @param repeatPassword 重复密码
     */
    private void modifyAccount(long accountId, String password, String repeatPassword) {
        if (Objects.equals(password, "") || Objects.equals(repeatPassword, "")) {
            Toast.makeText(UriModifyAccountActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repeatPassword)) {
            Toast.makeText(UriModifyAccountActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        MediaType mediaType = MediaType.parse("application/json");
        UriAccountInfoReq uriAccountInfoReq = new UriAccountInfoReq();
        uriAccountInfoReq.setId(accountId);
        uriAccountInfoReq.setAccountPassword(password);
        uriAccountInfoReq.setRepeatAccountPassword(repeatPassword);
        CommonRequest<UriAccountInfoReq> commonRequest = new CommonRequest<>();
        commonRequest.setRequestData(uriAccountInfoReq);
        final Request request = new Request.Builder()
                .url(baseUrl)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            JSONObject jsonObject = JSON.parseObject(result);
            if (response.code() == 500) {
                Toast.makeText(UriModifyAccountActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
            if (response.code() == 200) {
                if (jsonObject.getBoolean("resultData")) {
                    Toast.makeText(UriModifyAccountActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(UriModifyAccountActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
