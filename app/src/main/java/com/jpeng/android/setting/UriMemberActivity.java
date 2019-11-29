package com.jpeng.android.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jpeng.android.R;
import com.jpeng.android.login.UriLoginActivity;
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriUserInfoReq;
import com.jpeng.android.utils.system.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName UriMemberActivity
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/21 0021 下午 11:11
 */
public class UriMemberActivity extends Activity {
    private static final String baseUrl = "http://120.77.221.43:8082/web/user/adduser";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membersetting);
        final String userCode = ((ShareData) getApplication()).getUserCode();
        final String relationType = ((ShareData) getApplication()).getRelationType();
        //返回上一页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriMemberActivity.this.finish();
            }
        });
        //保存
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveMemberInfo(userCode, relationType);
                    Intent intent = new Intent(UriMemberActivity.this, UriFamilyActivity.class);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //重置
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInfo();
            }
        });
    }

    /**
     * 保存家庭成员信息
     *
     * @param userCode
     */
    private void saveMemberInfo(String userCode, String relationType) throws ParseException {
        MediaType mediaType = MediaType.parse("application/json");
        CommonRequest<UriUserInfoReq> commonRequest = new CommonRequest<UriUserInfoReq>();
        commonRequest.setRequestData(build(userCode, relationType));
        final Request request = new Request.Builder()
                .url(baseUrl)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String responseStr = response.body().string();
            try {
                boolean result = JSON.parseObject(responseStr).getBoolean("resultData");
                System.out.println(responseStr);
                if (result) {
                    Toast.makeText(UriMemberActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UriMemberActivity.this, "添加失败，未知错误", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(UriMemberActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(UriMemberActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private UriUserInfoReq build(String userCode, String relationType) throws ParseException {
        EditText textView1 = findViewById(R.id.text1);
        EditText textView2 = findViewById(R.id.text2);
        EditText textView3 = findViewById(R.id.text3);
        EditText textView4 = findViewById(R.id.text4);
        EditText textView5 = findViewById(R.id.text5);
        EditText textView6 = findViewById(R.id.text6);
        EditText textView7 = findViewById(R.id.text7);
        String time = textView7.getText().toString();
        //格式化时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        UriUserInfoReq uriUserInfoReq = new UriUserInfoReq();
        uriUserInfoReq.setUserAge(Integer.valueOf(textView1.getText().toString()));
        uriUserInfoReq.setUserCareer(textView2.getText().toString());
        uriUserInfoReq.setUserName(textView3.getText().toString());
        uriUserInfoReq.setUserPhone(textView4.getText().toString());
        uriUserInfoReq.setUserAddress(textView5.getText().toString());
        uriUserInfoReq.setEverUriSick(textView6.getText().toString());
        uriUserInfoReq.setRelationType(relationType);
        if (!time.equals("")) {
            Date date = format.parse(time);
            uriUserInfoReq.setSickTime(date);
        }
        uriUserInfoReq.setUserCode(userCode);
        return uriUserInfoReq;
    }

    /**
     * 重置界面填写信息
     */
    private void resetInfo() {
        EditText textView1 = findViewById(R.id.text1);
        EditText textView2 = findViewById(R.id.text2);
        EditText textView3 = findViewById(R.id.text3);
        EditText textView4 = findViewById(R.id.text4);
        EditText textView5 = findViewById(R.id.text5);
        EditText textView6 = findViewById(R.id.text6);
        EditText textView7 = findViewById(R.id.text7);
        textView1.setText(" ");
        textView2.setText(" ");
        textView3.setText(" ");
        textView4.setText(" ");
        textView5.setText(" ");
        textView6.setText(" ");
        textView7.setText(" ");
    }
}
