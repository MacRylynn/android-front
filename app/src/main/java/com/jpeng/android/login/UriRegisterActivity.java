package com.jpeng.android.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jpeng.android.R;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;

import java.io.IOException;


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

public class UriRegisterActivity extends Activity {

    private EditText accoutEdit;
    private EditText passwordEdit;
    private EditText repeatPasswordEdit;
    private static final String BaseUrl = "http://120.77.221.43:8082/web/user/addaccount";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        accoutEdit = findViewById(R.id.editText);
        passwordEdit = findViewById(R.id.editText2);
        repeatPasswordEdit = findViewById(R.id.editText3);
        //注册账号
        Button btn = findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registered(accoutEdit.getText().toString(), passwordEdit.getText().toString(), repeatPasswordEdit.getText().toString());
            }
        });
        //跳转到登录界面
        TextView textView = findViewById(R.id.textView4);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriRegisterActivity.this, UriLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 注册操作
     *
     * @param account        账号
     * @param password       密码
     * @param repeatPassword 重复密码
     */
    public void registered(final String account, final String password, final String repeatPassword) {
        //拿到OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        //判断账号和密码是否符合规范
        if ((account.length() >= 5 && password.length() >= 6) && (account.length() <= 16 && password.length() <= 14)) {
            if (password.equals(repeatPassword)) {
                UriAccountInfoReq uriAccountInfoReq = new UriAccountInfoReq();
                uriAccountInfoReq.setAccountNo(account);
                //todo 新注册的名字都叫 User
                uriAccountInfoReq.setAccountName("User");
                uriAccountInfoReq.setAccountPassword(password);
                uriAccountInfoReq.setRepeatAccountPassword(repeatPassword);
                CommonRequest<UriAccountInfoReq> commonRequest = new CommonRequest<>();
                commonRequest.setRequestData(uriAccountInfoReq);
                final Request request = new Request.Builder()
                        .url(BaseUrl)
                        .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                        .build();
                //构建http请求，并发送请求
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        Toast.makeText(UriRegisterActivity.this, "注册失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Looper.prepare();
                        if (response.code() == 500) {
                            Toast.makeText(UriRegisterActivity.this, "注册失败，服务器内部异常", Toast.LENGTH_SHORT).show();
                        }
                        if (response.code() == 200) {
                            String result = response.body().string();
                            JSONObject jsonObject = JSON.parseObject(result);
                            if (jsonObject.getBoolean("resultData")){
                                Toast.makeText(UriRegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UriRegisterActivity.this, UriLoginActivity.class);
                                startActivity(intent);
                            }else {
                                String message = jsonObject.getString("resultMsg");
                                Toast.makeText(UriRegisterActivity.this, "注册失败："+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        Looper.loop();
                    }
                });
            } else {
                Toast.makeText(UriRegisterActivity.this, "注册失败,请检查两次输入的密码是否正确", Toast.LENGTH_SHORT).show();
            }
        } else {
            //如果账号密码不符合规范则抛出此信息
            Toast.makeText(UriRegisterActivity.this, "注册失败,请检查用户名和密码是否规范", Toast.LENGTH_SHORT).show();
        }

    }
}
