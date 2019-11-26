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


import com.jpeng.android.R;
import com.jpeng.android.index.logs.UriCheckLogsActivity;
import com.jpeng.android.index.logs.UriSingleLogActivity;
import com.jpeng.android.utils.MyDatabaseHelper;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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
    private String BaseUrl = "http://120.77.221.43:8082/web/addresult";
    public static String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic/hello.png";//图片的路径
    private MyDatabaseHelper dbHelper;
    OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlecheck);
        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);
        //得到用户的名称

        //返回到家属选择页面
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriSingleCheckActivity.this, UriChooseAimAcyivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });

        //显示结果
        Button cButton = (Button) findViewById(R.id.button8);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理得到的图像
                try {

                    int a[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    //  int a[] = ProcessResults.getlevel(src);//调处理的算法
                    String result = Arrays.toString(a);
                    // 显示用户的信息  将结果绑定到用户上然后将数据保存到本地数据库
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.query("user_results", null, null, null, null, null, null);
                    String name = new String();
                    if (cursor.moveToLast()) {
                        name = cursor.getString(cursor.getColumnIndex("user_name"));
                        Toast.makeText(UriSingleCheckActivity.this, "用户名：" + name + "\n" + "结果：" + result, Toast.LENGTH_SHORT).show();
                        //把数据库里面的用户名name，连通测得的最后的结果result，和保存的图片fileName 都上传到服务器
                        RequestBody requestBody = new FormBody.Builder()
                                .add("testResultUser", name)
                                .add("testResult", result)
                                .add("testImage", fileName)
                                .build();
                        //2.构造Request
                        Request.Builder builder = new Request.Builder();
                        Request request = builder.url(BaseUrl).post(requestBody).build();
                        Call call = okHttpClient.newCall(request);
                        //执行call
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("onFailure", e.getMessage());

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.e("onResponse", "success");
                            }
                        });
                    }
                    cursor.close();
                    ContentValues values = new ContentValues();
                    values.put("user_name", name);
                    values.put("user_result", result);
                    db.insert("user_results", null, values);
                    //显示每一个数据的等级
                    TextView textView1 = (TextView) findViewById(R.id.result1_v);
                    String Results1 = String.valueOf(a[0]);
                    textView1.setText(Results1);

                    TextView textView2 = (TextView) findViewById(R.id.result2_v);
                    String Results2 =String.valueOf(a[1]);
                    textView2.setText(Results2);

                    TextView textView3 = (TextView) findViewById(R.id.result3_v);
                    String Results3 = String.valueOf(a[2]);
                    textView3.setText(Results3);

                    TextView textView4 = (TextView) findViewById(R.id.result4_v);
                    String Results4 = String.valueOf(a[3]);
                    textView4.setText(Results4);

                    TextView textView5 = (TextView) findViewById(R.id.result5_v);
                    String Results5 = String.valueOf(a[4]);
                    textView5.setText(Results5);

                    TextView textView6 = (TextView) findViewById(R.id.result6_v);
                    String Results6 = String.valueOf(a[5]);
                    textView6.setText(Results6);

                    TextView textView7 = (TextView) findViewById(R.id.result7_v);
                    String Results7 = String.valueOf(a[6]);
                    textView7.setText(Results7);

                    TextView textView8 = (TextView) findViewById(R.id.result8_v);
                    String Results8 = String.valueOf(a[7]);
                    textView8.setText(Results8);

                    TextView textView9 = (TextView) findViewById(R.id.result9_v);
                    String Results9 = String.valueOf(a[8]);
                    textView9.setText(Results9);

                    TextView textView10 = (TextView) findViewById(R.id.result10_v);
                    String Results10 = String.valueOf(a[9]);
                    textView10.setText(Results10);

                    TextView textView11 = (TextView) findViewById(R.id.result11_v);
                    String Results11 = String.valueOf(a[10]);
                    textView11.setText(Results11);

                    TextView textView12 = (TextView) findViewById(R.id.result12_v);
                    String Results12 = String.valueOf(a[11]);
                    textView12.setText(Results12);

                } catch (Exception E) {
                    Toast.makeText(UriSingleCheckActivity.this, "算法调用失败,请检查图片的位置是否摆放合适", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
    }


    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }


}

