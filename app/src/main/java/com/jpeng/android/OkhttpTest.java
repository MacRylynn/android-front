package com.jpeng.android;

import com.alibaba.fastjson.JSON;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @ClassName OkhttpTest
 * @Description
 * @Author: lijiao73
 * @Date: 2019/11/27 0027 下午 8:02
 */
public class OkhttpTest {
    public static void main(String[] args) {
        String url = "http://120.77.221.43:8082/web/user/selectaccountuser";
        MediaType mediaType = MediaType.parse("application/json");

        UriAccountInfoReq uriAccountInfoReq = new UriAccountInfoReq();
        uriAccountInfoReq.setId(1L);
        CommonRequest<UriAccountInfoReq> commonRequest = new CommonRequest<>();
        commonRequest.setRequestData(uriAccountInfoReq);
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送请求
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("注册失败：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 500) {
                    System.out.println("注册失败，服务器内部异常");
                }
                if (response.code() == 200) {
                    String result = response.body().string();
                    System.out.println(request);
                    //                    JSONObject jsonObject = JSON.parseObject(result);
                    //                    if (jsonObject.getBoolean("resultData")) {
                    //                        System.out.println("注册成功,请返回登录界面登录");
                    //
                    //                    } else {
                    //                        String message = jsonObject.getString("resultMsg");
                    //                        System.out.println("注册失败：" + message);
                    //                    }
                }
            }
        });

    }
}