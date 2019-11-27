package com.jpeng.android.login;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.jpeng.android.utils.MyDatabaseHelper;
import com.jpeng.android.R;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;
import com.jpeng.android.utils.system.MainActivity;

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

public class UriLoginActivity extends Activity {

    private static final String BaseUrl = "http://120.77.221.43:8082/web/user/login";
    private EditText accountEdit;
    private EditText posswordEdit;
    private MyDatabaseHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        accountEdit = findViewById(R.id.editText);
        posswordEdit = findViewById(R.id.editText2);
        //跳转到注册的页面
        TextView btn2 = findViewById(R.id.textView5);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriLoginActivity.this, UriRegisterActivity.class);
                startActivity(intent);
            }
        });
        //登录操作
        Button btn3 = findViewById(R.id.button4);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(accountEdit.getText().toString(), posswordEdit.getText().toString());
            }
        });
        //创建数据库 如果数据库中有保存的账号和密码则自己获取账号和密码
        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("user_results", null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            String acount = cursor.getString(cursor.getColumnIndex("user_name"));
            String password = cursor.getString(cursor.getColumnIndex("user_password"));
            accountEdit.setText(acount);
            posswordEdit.setText(password);
        }
        cursor.close();
    }

    /**
     * 登录操作
     *
     * @param account  账号
     * @param password 密码
     */
    private void login(final String account, final String password) {
        //拿到OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        if (!account.equals("") && !password.equals("")) {
            MediaType mediaType = MediaType.parse("application/json");
            UriAccountInfoReq uriAccountInfoReq = new UriAccountInfoReq();
            uriAccountInfoReq.setAccountNo(account);
            uriAccountInfoReq.setAccountPassword(password);
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
                    Toast.makeText(UriLoginActivity.this, "登录出现异常", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //在子线程里面显示Toast需要    Looper.prepare(); 和 Looper.loop();
                    Looper.prepare();
                    if (response.code() == 500) {
                        Toast.makeText(UriLoginActivity.this, "登录失败,请验证账号是否存在或密码输入是否正确", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 200) {
                        Toast.makeText(UriLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //跳转到主界面
                        Intent intent = new Intent(UriLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        //结束此Activity
                        finish();
                        //把传进来的用户数据保存在本地数据库，然后再TestActivity里面获得用户值，然后方便把用户名和检查结果一起上传到后台的数据库
                        String result = response.body().string();
                        SQLiteDatabase db = dbHelper.getWritableDatabase();//创建或者打开现有的本地数据库
                        ContentValues values = new ContentValues();
                        values.put("user_name", account);
                        values.put("user_id", getAccountId(result));//此ID供以后用
                        values.put("user_password", password);
                        db.insert("user_results", null, values);//把用户名保存到本地数据库
                        System.out.println("用户数据保存成功");
                    } else {
                        Toast.makeText(UriLoginActivity.this, "登录出现未知错误", Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
            });
        } else {
            Looper.prepare();
            Toast.makeText(UriLoginActivity.this, "请输入账号和密码进行登录", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

    }

    /**
     * 根据返回获取登录账号ID
     *
     * @param response
     * @return 账号ID
     */
    private static Long getAccountId(String response) {
        JSONObject jsonObject = JSON.parseObject(response);
        long userId = 0L;
        if (jsonObject.containsKey("resultData")) {
            userId = jsonObject.getLongValue("resultData");
        }
        return userId;
    }

}

