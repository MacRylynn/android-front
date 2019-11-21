package com.jpeng.android.index.logs;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jpeng.android.R;
import com.jpeng.android.utils.MyDatabaseHelper;

import java.util.Objects;

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
        setContentView(R.layout.testrecord);
        dbHelper = new MyDatabaseHelper(this, "user_results.db", null, 1);
        final TextView textView = findViewById(R.id.textView2);
        Button showRecord = findViewById(R.id.button);
        //显示检测记录，防止一直点击多次显示用isDisplay来控制点击次数
        showRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("user_results", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String result = cursor.getString(cursor.getColumnIndex("user_result"));
                        if (result != null) {
                            results = results + result + "\n";
                        }
                    } while (cursor.moveToNext());
                }
                if (isDisplay) {
                    if (!Objects.equals(results, "检测结果：" + "\n")) {
                        textView.setText(results);
                        isDisplay = false;
                    } else {
                        Toast.makeText(UriCheckLogsActivity.this, "您还没有记录,请开启检测", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });
    }
}
