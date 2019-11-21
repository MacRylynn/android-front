package com.jpeng.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by ${LJ} on 2018/10/22.
 */

public class VedioTestActivity extends Activity {
    /*用来记录录像存储路径*/
    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic/hello.mp4");//设置录像存储路径
    //File file = new File("storage/SDCARD/vedio/video.mp4" );//设置录像存储路径
    Uri uri = FileProvider.getUriForFile(this, "my.provider", file);


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vediotest);
        Button vedioStart = (Button) findViewById(R.id.button9);
        vedioStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存录像到指定的路径
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // 激活系统的照相机进行录像，通过Intent激活相机并实现录像功能
                Intent intent = new Intent();
                intent.setAction("android.media.action.VIDEO_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
                Toast.makeText(VedioTestActivity.this, file.getPath(), Toast.LENGTH_LONG).show();

            }
        });
        Button vedioTest = (Button) findViewById(R.id.button10);
        vedioTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(VedioTestActivity.this, "kaiasdasd", Toast.LENGTH_LONG).show();

            }
        });

    }
}
