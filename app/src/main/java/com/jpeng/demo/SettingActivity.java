package com.jpeng.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2018/6/5.
 */

public class SettingActivity extends Activity {
    private Button cheanPic;
    private MyDatabaseHelper dbHelper;
    private Dialog mDialog;
    public static String fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic/hello.png";//图片的路径


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        //注销登录，删除SQLite中的最后一条数据的password
        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);//打开数据库
        Button logoff = findViewById(R.id.button3);
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    logOffDialog();
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //清除缓存，删除保存的原图
        cheanPic = findViewById(R.id.button1);
        cheanPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CheanPicDialog();
                } catch (Exception E) {
                    Toast.makeText(SettingActivity.this, E.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //提示注销登录的操作
    private void logOffDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("温馨提示");
        builder.setMessage("是否要注销登录");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("user_results", null, null, null, null, null, null);
                if (cursor.moveToLast()) {
                    String password = cursor.getString(cursor.getColumnIndex("user_password"));
                    ContentValues values = new ContentValues();
                    values.put("user_password", "");
                    db.update("user_results", values, "user_name=?", new String[]{password});
                }
                cursor.close();
                Intent intent = new Intent(SettingActivity.this, LogInActivity.class);
                startActivity(intent);
                dialog.dismiss();
                Toast.makeText(SettingActivity.this, "注销成功", Toast.LENGTH_SHORT).show();

            }
        });
        mDialog = builder.create();
        mDialog.show();
    }

    //提示清除图片的操作，缓存只有拍摄的原图和SQLite里面的数据，这里缓存只删除原图
    private void CheanPicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("温馨提示");
        builder.setMessage("是否要清除数据");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(fileName)) {
                    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    ContentResolver contentResolver = SettingActivity.this.getContentResolver();//cutPic.this是一个上下文
                    String url = MediaStore.Images.Media.DATA + "='" + fileName + "'";
                    //删除图片
                    contentResolver.delete(uri, url, null);
                }
                dialog.dismiss();
                Toast.makeText(SettingActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
            }
        });
        mDialog = builder.create();
        mDialog.show();

    }
}
