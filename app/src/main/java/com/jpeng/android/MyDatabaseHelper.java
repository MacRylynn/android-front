package com.jpeng.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/7/5.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    public static final  String CREATE_USERINFO = "create table user_results("+
            //primary key 将id设为主键 autoincrement表示id列是自增长的
            "user_id integer primary key autoincrement,"+
            "user_name text,"+
            "user_password text,"+
            "user_result text,"+
            "user_image text)";
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase语句来建立数据库
        db.execSQL(CREATE_USERINFO);
        //创建成功之后的回应
        //Toast.makeText(mContext,"成功创建数据库",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
