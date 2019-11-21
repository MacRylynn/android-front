package com.jpeng.android;

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
 * Created by Administrator on 2018/6/5.
 */

public class LogInActivity extends Activity {
    private String BaseUrl = "http://120.77.221.43:8082/web/login";
    private EditText editText1;
    private EditText editText2;
    private MyDatabaseHelper dbHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        //跳转到注册的页面
        Button btn2 = findViewById(R.id.button8);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        //创建数据库 如果数据库中有保存的账号和密码则自己获取账号和密码
        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor =db.query("user_results",null,null,null,null,null,null);
        if(cursor.moveToLast()){
            String name =cursor.getString(cursor.getColumnIndex("user_name"));
            String password =cursor.getString(cursor.getColumnIndex("user_password"));
            editText1.setText(name);
            editText2.setText(password);
        }
        cursor.close();
    }



    public void getPage(View view) {
        //1.拿到OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //前台获得数据
        final String userName = editText1.getText().toString();
        final String userPassword = editText2.getText().toString();
        if (!userName.equals("") && !userPassword.equals("")) {
            //把前台获取的数据传到后台和数据库比较
            RequestBody requestBody = new FormBody.Builder()
                    .add("userName", userName)
                    .add("userPassword", userPassword)
                    .build();
            //2.构造Request
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(BaseUrl).post(requestBody).build();
            Call call = okHttpClient.newCall(request);
            //执行call
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
                    Log.e("onResponse:", "success");
                    final String res = response.body().string();
                    if (res.equals("true")) {
                        //在子线程里面显示Toast需要    Looper.prepare(); 和 Looper.loop();
                        Looper.prepare();
                        Toast.makeText(LogInActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        //跳转到主界面
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        //把传进来的用户数据保存在本地数据库，然后再TestActivity里面获得用户值，然后方便把用户名和检查结果一起上传到后台的数据库
                        SQLiteDatabase db = dbHelper.getWritableDatabase();//创建或者打开现有的本地数据库
                        ContentValues values = new ContentValues();
                        values.put("user_name", userName);
                        values.put("user_password", userPassword);
                        db.insert("user_results", null, values);//把用户名保存到本地数据库
                        System.out.println("用户数据保存成功");
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(LogInActivity.this, "登录失败，请检查账号密码是否正确", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            });
        } else {
            Toast.makeText(LogInActivity.this, "登录失败，请检查用户名和密码是否正确", Toast.LENGTH_SHORT).show();
        }
    }
}
