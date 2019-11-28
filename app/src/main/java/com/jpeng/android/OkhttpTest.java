package com.jpeng.android;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jpeng.android.index.logs.UriCheckLogsActivity;
import com.jpeng.android.index.logs.UriChoosePersonAvtivity;
import com.jpeng.android.utils.domain.base.CommonRequest;
import com.jpeng.android.utils.domain.request.UriAccountInfoReq;
import com.jpeng.android.utils.domain.request.UriUserInfoReq;
import com.jpeng.android.utils.domain.response.UriCheckResultVo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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


    public static void main(String[] args) throws IOException {
        String url = "http://120.77.221.43:8082/web/check/selectrecordings";
        List<UriCheckResultVo> list = new ArrayList<>();
        MediaType mediaType = MediaType.parse("application/json");
        UriUserInfoReq uriUserInfoReq = new UriUserInfoReq();
        uriUserInfoReq.setId(1L);
        CommonRequest<UriUserInfoReq> commonRequest = new CommonRequest<>();
        commonRequest.setRequestData(uriUserInfoReq);
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, JSON.toJSONString(commonRequest)))
                .build();
        //构建http请求，并发送同步请求(真是项目中一般是不用的)
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(request).execute();
            list = selectCheckRecords(response.body().string());
        } catch (Exception e) {
        }


        //格式化时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (UriCheckResultVo uriCheckResultVo:list){
            String time=format.format(uriCheckResultVo.getCheckTime());
            System.out.println(time+" "+"检测结果："+uriCheckResultVo.getCheckResult());
        }


    }



    private static List<UriCheckResultVo> selectCheckRecords(String response) {
        List<UriCheckResultVo> resList = new ArrayList<>();
        JSONArray jsonArray = JSON.parseArray(JSON.parseObject(response).getString("resultData"));
        if (jsonArray == null || jsonArray.size() <= 0) {
            System.out.println("数据返回失败或者没有记录！");
            return resList;
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            UriCheckResultVo uriCheckResultVo = new UriCheckResultVo();
            uriCheckResultVo.setCheckResult(jsonArray.getJSONObject(i).getString("checkResult"));
            uriCheckResultVo.setCheckTime(jsonArray.getJSONObject(i).getDate("checkTime"));
            uriCheckResultVo.setId(jsonArray.getJSONObject(i).getLong("id"));
            uriCheckResultVo.setResultImagePath(jsonArray.getJSONObject(i).getString("resultImagePath"));
            uriCheckResultVo.setUserId(jsonArray.getJSONObject(i).getLong("userId"));
            resList.add(uriCheckResultVo);
        }
        return resList;
    }
}
