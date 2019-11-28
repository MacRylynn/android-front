package com.jpeng.android.index.logs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jpeng.android.R;
import com.jpeng.android.index.check.UriCheckActivity;
import com.jpeng.android.index.check.UriChooseAimAcyivity;
import com.jpeng.android.utils.MyDatabaseHelper;
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;
import com.jpeng.android.utils.domain.response.UriUserInfoVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    private MyDatabaseHelper dbHelper;
    private String results = "检测结果：" + "\n";
    private boolean isDisplay = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklogs);
        //跳转到具体的记录
        Button btn = findViewById(R.id.button5);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriCheckLogsActivity.this, UriSingleLogActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });

        //返回家属选择页面
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriCheckLogsActivity.this, UriChoosePersonAvtivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
//        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);
//        final TextView textView = findViewById(R.id.textView2);
//        Button showRecord = findViewById(R.id.button);
//        //显示检测记录，防止一直点击多次显示用isDisplay来控制点击次数
//        showRecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                Cursor cursor = db.query("user_results", null, null, null, null, null, null);
//                if (cursor.moveToFirst()) {
//                    do {
//                        String result = cursor.getString(cursor.getColumnIndex("user_result"));
//                        if (result != null) {
//                            results = results + result + "\n";
//                        }
//                    } while (cursor.moveToNext());
//                }
//                if (isDisplay) {
//                    if (!Objects.equals(results, "检测结果：" + "\n")) {
//                        textView.setText(results);
//                        isDisplay = false;
//                    } else {
//                        Toast.makeText(UriCheckLogsActivity.this, "您还没有记录,请开启检测", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                cursor.close();
//            }
//        });
    }


}
