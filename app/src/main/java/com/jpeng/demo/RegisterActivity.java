package com.jpeng.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/29.
 */

public class RegisterActivity extends Activity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private String BaseUrl = "http://120.77.221.43:8082/web/adduser";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
    }

    //请求传递的方法  实现增加数据的功能 （注册）
    public void doPost(View view) {
        //1.拿到OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //前台获得数据
        String userName = editText1.getText().toString();
        String userPassword = editText2.getText().toString();
        String userPassword2 = editText3.getText().toString();
        //判断账号和密码是否符合规范
        if ((userName.length() >= 5 && userPassword.length() >= 6) && (userName.length() <= 16 && userPassword.length() <= 14)) {
            if (userPassword.equals(userPassword2)) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("userName", userName)
                        .add("userPassword", userPassword)
                        .build();
                //2.构造Request
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(BaseUrl).post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                //3.执行call
                call.enqueue(new Callback() {
                    @Override
                    //异步
                    public void onFailure(Call call, IOException e) {
                        Log.e("onFailure:", e.getMessage());
                    }

                    @Override
                    //同步
                    public void onResponse(Call call, Response response) throws IOException {
                        //response不是ui线程而是子线程
                        final String res = response.body().string();
                        if (res.equals("true")) {
                            //在子线程当中不能调用Toast 需要先 1.Looper.prepare();2.调用Toast 3.  Looper.loop();
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this, "注册成功,请登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                            startActivity(intent);
                            Looper.loop();
                            Log.e("onResponse:", res);
                        } else {
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this, "注册失败,用户账号已经存在", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "注册失败,请检查两次输入的密码是否正确", Toast.LENGTH_SHORT).show();
            }
        } else {
            //如果账号密码不符合规范则抛出此信息
            Toast.makeText(RegisterActivity.this, "注册失败,请检查用户名和密码是否规范", Toast.LENGTH_SHORT).show();
        }
    }
}
