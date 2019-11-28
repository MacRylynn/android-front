package com.jpeng.android.index.logs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.jpeng.android.utils.ShareData;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;
import com.jpeng.android.utils.domain.response.UriUserInfoVo;
import com.jpeng.android.utils.system.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class UriChoosePersonAvtivity extends Activity {
    private static final String BaseUrl = "http://120.77.221.43:8082/web/user/selectaccountuser";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseperson);
        //申明政策允许所有的操作（为了从服务端同步获取信息）
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        long accountId = ((ShareData) getApplication()).getAccountId();
        List<UriUserInfoVo> list = new ArrayList<>();
        try {
            list = selectAccountUsers(accountId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建UI
        createUI(list);
        //返回到记录主页
        TextView textView = findViewById(R.id.textView8);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UriChoosePersonAvtivity.this, MainActivity.class);//从Activity跳转到Activity
                startActivity(intent);
            }
        });

    }


    /**
     * 页面出现的时候就请求获取这个账户下的所有家庭成员
     *
     * @param accountId 账户ID
     */
    private List<UriUserInfoVo> selectAccountUsers(long accountId) throws IOException {
        List<UriUserInfoVo> list = new ArrayList<>();
        MediaType mediaType = MediaType.parse("application/json");
        UriAccountInfoReq uriAccountInfoReq = new UriAccountInfoReq();
        uriAccountInfoReq.setId(accountId);
        CommonRequest<UriAccountInfoReq> commonRequest = new CommonRequest<UriAccountInfoReq>();
        commonRequest.setRequestData(uriAccountInfoReq);
        Request request = new Request.Builder()
                .url(BaseUrl)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return getUserList(response.body().string());
        } catch (Exception e) {
            Toast.makeText(UriChoosePersonAvtivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return list;
    }

    /**
     * 返回一个账户下的所有家庭成员信息
     *
     * @param response
     * @return List<UriUserInfoVo>
     */
    private List<UriUserInfoVo> getUserList(String response) {
        List<UriUserInfoVo> reslist = new ArrayList<UriUserInfoVo>();
        JSONObject jsonObject = JSON.parseObject(response);
        String resultData = jsonObject.getString("resultData");
        JSONArray userLisr = JSON.parseArray(resultData);
        if (userLisr.size() == 0) {
            Toast.makeText(UriChoosePersonAvtivity.this, "您还没有录入家庭成员系信息，请前往设置中录入", Toast.LENGTH_LONG).show();
            return reslist;
        }
        for (int i = 0; i < userLisr.size(); i++) {
            UriUserInfoVo uriUserInfoVo = new UriUserInfoVo();
            uriUserInfoVo.setEverUriSick(userLisr.getJSONObject(i).getString("everUriSick"));
            uriUserInfoVo.setId(Long.valueOf(userLisr.getJSONObject(i).getString("id")));
            uriUserInfoVo.setRelationType(userLisr.getJSONObject(i).getString("relationType"));
            uriUserInfoVo.setUserAddress(userLisr.getJSONObject(i).getString("userAddress"));
            uriUserInfoVo.setUserAge(Integer.valueOf(userLisr.getJSONObject(i).getString("userAge")));
            uriUserInfoVo.setUserCareer(userLisr.getJSONObject(i).getString("userCareer"));
            uriUserInfoVo.setUserName(userLisr.getJSONObject(i).getString("userName"));
            uriUserInfoVo.setUserPhone(userLisr.getJSONObject(i).getString("userPhone"));
            reslist.add(uriUserInfoVo);
        }
        return reslist;
    }

    /**
     * 动态创建组件函数
     *
     * @param list
     */
    private void createUI(List<UriUserInfoVo> list) {
        LinearLayout elLayout = findViewById(R.id.line1);
        for (final UriUserInfoVo user : list) {
            System.out.println(list.size());
            Button button = new Button(UriChoosePersonAvtivity.this);
            button.setWidth(200);
            button.setHeight(20);
            button.setTextSize(15);
            button.setText(user.getRelationType() + "   " + user.getUserName());
            button.setText(user.getRelationType() + "   " + user.getUserName());
            //跳转到检测页面
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // userId在哪里用，就要在哪里更新，而accountId全局只有一个
                    ((ShareData) getApplication()).setUserId(user.getId());
                    Intent intent = new Intent(UriChoosePersonAvtivity.this, UriCheckLogsActivity.class);//从Activity跳转到Activity
                    startActivity(intent);
                }
            });
            elLayout.addView(button);
        }
    }
}
