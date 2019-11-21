package com.jpeng.android.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jpeng.android.R;
import com.jpeng.jptabbar.JPTabBar;

/**
 * 主页的第三个页面  健康小知识
 */
public class Tab3Pager extends Fragment implements View.OnClickListener {
    JPTabBar mTabBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activitythree, null);
        init(layout);
        return layout;
    }

    /**
     * 初始化
     */
    private void init(View layout) {
        mTabBar = ((MainActivity) getActivity()).getTabbar();

    }

    @Override
    public void onClick(View v) {
    }
}
