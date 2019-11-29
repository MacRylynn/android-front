package com.jpeng.android.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jpeng.android.R;
import com.jpeng.android.login.UriLoginActivity;
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;
import com.jpeng.android.utils.system.MainActivity;

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

public class UriFamilyActivity extends Activity {
    private static final String getAccountUrl = "http://120.77.221.43:8082/web/user/selectuser";
    //界面上的组件
    private static final int[] values = {R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5, R.id.image6,};
    //所有的类型
    private static final String[] relationTypes = {"SELF", "PARENTS", "SPOUNSE", "RELATIVE", "CHILDREN", "OTHERS",};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.familysetting);
        //申明政策允许所有的操作（为了从服务端同步获取信息）
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //将这个账号的用户号存入共享数据
        long accountId = ((ShareData) getApplication()).getAccountId();
        ((ShareData) getApplication()).setUserCode(getCurrentUserCodeByAccountId(accountId));
        //选择动作
        chooseMember();
        //返回上一页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UriFamilyActivity.this.finish();
            }
        });
    }

    /**
     * 得到当前登录用户，以便获取用户号
     *
     * @param accountId 当前账号ID
     * @return
     */
    private String getCurrentUserCodeByAccountId(long accountId) {
        String userCode = "";
        MediaType mediaType = MediaType.parse("application/json");
        UriAccountInfoReq uriAccountInfoReq = new UriAccountInfoReq();
        uriAccountInfoReq.setId(accountId);
        CommonRequest<UriAccountInfoReq> commonRequest = new CommonRequest<>();
        commonRequest.setRequestData(uriAccountInfoReq);
        final Request request = new Request.Builder()
                .url(getAccountUrl)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            JSONObject jsonObject = JSON.parseObject(JSON.parseObject(result).getString("resultData"));
            userCode = jsonObject.getString("userCode");
        } catch (Exception e) {
            Toast.makeText(UriFamilyActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return userCode;
    }

    private void chooseMember() {
        for (int i = 0; i < values.length; i++) {
            ImageView imageView = findViewById(values[i]);
            final String relationType = relationTypes[i];
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UriFamilyActivity.this, UriMemberActivity.class);
                    ((ShareData) getApplication()).setRelationType(relationType);
                    startActivity(intent);
                }
            });
        }
    }
}
