package com.jpeng.android.index.check;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jpeng.android.R;
import com.jpeng.android.utils.system.MainActivity;

import java.io.File;
import java.io.IOException;


/**
 * @ClassName UriSingleLogActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:04
 */

public class UriCheckActivity extends Activity implements View.OnClickListener {
    @SuppressLint("SdCardPath")
    private Dialog mDialog;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决储存过程的报错 exposed beyond app through ClipData.Item.getUri() android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //和界面连接
        setContentView(R.layout.check);
        //返回到选择家属页面
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriCheckActivity.this, UriChooseAimAcyivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
        //跳转到上传检测页面
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriCheckActivity.this, UriSingleCheckActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });
        //弹框（计时）结束后开启相机
        if (dialog()) {
            initView();
        }
    }

    //初始化相机的函数
    private void initView() {
        findViewById(R.id.button8).setOnClickListener(this);
    }

    //定时拍照的的弹框函数
    private boolean dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("温馨提示");
        builder.setMessage("请等待40秒后此对话框消失后点击“打开相机拍照并保存”进行检测/点击“取消”取消倒计时");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                timer.cancel();//取消倒计时
            }
        });
        mDialog = builder.create();
        mDialog.show();
        timer.start();
        return true;
    }

    //计时的函数，用来等待试剂条反应所需要的时间 CountDownTimer()有两个参数，第一个参数是需要反应的时间第二个参数不用管
    CountDownTimer timer = new CountDownTimer(40000, 1000) {
        @Override
        public void onTick(long arg0) {
            int thetime = (int) (arg0 / 1000);
            if (mDialog != null) {
            }
        }

        @Override
        public void onFinish() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    };

    //拍照的回调函数，用来保存和显示图片
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果不加resultCode!=Activity.RESULT_CANCELED 那不拍照就返回将出错
        if (requestCode == 1 && resultCode != Activity.RESULT_CANCELED) {
            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) return;
            // 把原图显示到界面上
            View view = findViewById(R.id.imageView);
            view.setBackgroundDrawable(new BitmapDrawable(readpic()));
            view.animate().rotation(90);
            try {
                // 保存缩略图
                writeFile(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //监听按钮的事件，当我们按下camera_btn按钮时就会触发此事件
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.button8:
                try {
                    buttonClick();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                break;

        }
    }

    //当按下了camera_btn按钮后采取的动作
    private void buttonClick() throws IOException {
        Intent intent = new Intent();
        try {
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        } catch (Exception E) {
            Toast.makeText(UriCheckActivity.this, "请检查是已经开启相机权限", Toast.LENGTH_SHORT).show();

        }
        // 获取文件
        File file = createFileIfNeed("hello.png");
        //拍照后原图回存入此路径下
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 1);
    }

    // 从保存原图的地址读取图片
    private Bitmap readpic() {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic/hello.png";
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

    // 在sd卡中创建一保存图片（原图和缩略图共用的）文件夹
    private File createFileIfNeed(String fileName) throws IOException {
        String fileA = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/nbinpic";
        Toast.makeText(this, "请对准试剂条拍照", Toast.LENGTH_SHORT).show();
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    //保存缩略图代码中没用到
    private void writeFile(Intent data) throws IOException {
    }

}
