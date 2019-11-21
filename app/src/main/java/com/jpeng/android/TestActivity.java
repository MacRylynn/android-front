package com.jpeng.android;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class TestActivity extends Activity  {
    private String BaseUrl = "http://120.77.221.43:8082/web/addresult";
    public static String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic/hello.png";//图片的路径
    private MyDatabaseHelper dbHelper;
    OkHttpClient okHttpClient = new OkHttpClient();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_results);
        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);
        //得到用户的名称

        //显示结果
        Button cButton = (Button) findViewById(R.id.show_results);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理得到的图像
                try {

                    int a[] ={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                  //  int a[] = ProcessResults.getlevel(src);//调处理的算法
                    String result = Arrays.toString(a);
                   // 显示用户的信息  将结果绑定到用户上然后将数据保存到本地数据库
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor cursor = db.query("user_results", null, null, null, null, null, null);
                    String name= new String();
                    if (cursor.moveToLast()) {
                        name = cursor.getString(cursor.getColumnIndex("user_name"));
                        Toast.makeText(TestActivity.this, "用户名：" + name + "\n" + "结果：" + result, Toast.LENGTH_SHORT).show();
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
                                Log.e("onFailure",e.getMessage());

                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.e("onResponse","success");
                            }
                        });
                    }
                    cursor.close();
                    ContentValues values = new ContentValues();
                    values.put("user_name", name);
                    values.put("user_result", result);
                    db.insert("user_results", null, values);
                    //显示每一个数据的等级
                    TextView textView1 = (TextView) findViewById(R.id.TextView1);
                    String Results1 = "第" + a[0] + "级";
                    textView1.setText(Results1);

                    TextView textView2 = (TextView) findViewById(R.id.TextView2);
                    String Results2 = "第" + a[1] + "级";
                    textView2.setText(Results2);

                    TextView textView3 = (TextView) findViewById(R.id.TextView3);
                    String Results3 = "第" + a[2] + "级";
                    textView3.setText(Results3);

                    TextView textView4 = (TextView) findViewById(R.id.TextView4);
                    String Results4 = "第" + a[3] + "级";
                    textView4.setText(Results4);

                    TextView textView5 = (TextView) findViewById(R.id.TextView5);
                    String Results5 = "第" + a[4] + "级";
                    textView5.setText(Results5);

                    TextView textView6 = (TextView) findViewById(R.id.TextView6);
                    String Results6 = "第" + a[5] + "级";
                    textView6.setText(Results6);

                    TextView textView7 = (TextView) findViewById(R.id.TextView7);
                    String Results7 = "第" + a[6] + "级";
                    textView7.setText(Results7);

                    TextView textView8 = (TextView) findViewById(R.id.TextView8);
                    String Results8 = "第" + a[7] + "级";
                    textView8.setText(Results8);

                    TextView textView9 = (TextView) findViewById(R.id.TextView9);
                    String Results9 = "第" + a[8] + "级";
                    textView9.setText(Results9);

                    TextView textView10 = (TextView) findViewById(R.id.TextView10);
                    String Results10 = "第" + a[9] + "级";
                    textView10.setText(Results10);

                    TextView textView11 = (TextView) findViewById(R.id.TextView11);
                    String Results11 = "第" + a[10] + "级";
                    textView11.setText(Results11);

                    TextView textView12 = (TextView) findViewById(R.id.TextView12);
                    String Results12 = "第" + a[11] + "级";
                    textView12.setText(Results12);

                    TextView textView13 = (TextView) findViewById(R.id.TextView13);
                    String Results13 = "第" + a[12] + "级";
                    textView13.setText(Results13);

                    TextView textView14 = (TextView) findViewById(R.id.TextView14);
                    String Results14 = "第" + a[13] + "级";
                    textView14.setText(Results14);
                } catch (Exception E) {
                    Toast.makeText(TestActivity.this, "算法调用失败,请检查图片的位置是否摆放合适", Toast.LENGTH_SHORT).show();
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

