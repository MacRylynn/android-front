package com.jpeng.android.utils.system;


import android.annotation.SuppressLint;
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


import com.jpeng.android.R;
import com.jpeng.android.utils.system.MainActivity;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.animate.AnimationType;

/**
 * 主页的第二个页面 使用教程
 */
public class Tab2Pager extends Fragment {
    //使用教程的视频地址
    private String videopPath = "https://v.youku.com/v_show/id_XMzY2MTYyMDYwMA==.html?spm=a2hzp.8244740.0.0";
    private  WebView webview;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activitytwo, null);
        init(layout);
        webview = (layout).findViewById(R.id.webView);
        Button startPlay = (layout).findViewById(R.id.button4);
        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebSettings ws = webview.getSettings();
                ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
                ws.setUseWideViewPort(true);// 可任意比例缩放
                ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。
                // setLoadWithOverviewMode方法是设置webview加载的页面的模式。
                ws.setSavePassword(true);
                ws.setSaveFormData(true);// 保存表单数据
                ws.setJavaScriptEnabled(true);
                ws.setDomStorageEnabled(true);
                ws.setSupportMultipleWindows(true);
                webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                webview.setWebChromeClient(new WebChromeClient());
                webview.setWebViewClient(new WebViewClient());
                webview.loadUrl(videopPath);
            }
        });

        return layout;
    }

    private void init(View layout) {
        JPTabBar mTabBar = ((MainActivity) getActivity()).getTabbar();
        mTabBar.setAnimation(AnimationType.SCALE);
    }


}
