package com.jpeng.android.utils.system;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;


import com.jpeng.android.R;
import com.jpeng.android.utils.system.MainActivity;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.animate.AnimationType;

/**
 * 主页的第二个页面 使用教程
 */
public class Tab2Pager extends Fragment {
    //使用教程的视频地址
    private String videopPath = "http://120.77.221.43:8082/vedio/teach.mp4";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.tutorial, null);
        init(layout);
        final ImageView imageView = (layout).findViewById(R.id.start);
        final VideoView webView = (layout).findViewById(R.id.webView);
        webView.setVideoPath(videopPath);
        webView.getBufferPercentage();
        webView.getCurrentPosition();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //让播放的组件消失掉
                imageView.setVisibility(View.GONE);
                //开始播放视频
                webView.start();
            }
        });
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.VISIBLE);
                webView.pause();
            }
        });


        return layout;
    }

    private void init(View layout) {
        JPTabBar mTabBar = ((MainActivity) getActivity()).getTabbar();
        mTabBar.setAnimation(AnimationType.SCALE);
    }


}
