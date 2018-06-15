package com.renrg.heaven.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renrg.heaven.R;
import com.renrg.heaven.base.BaseFragment;
import com.renrg.heaven.listener.LeftMenuItemSelectedListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class LeftMenuFragment extends BaseFragment {

    private LeftMenuItemSelectedListener leftMenuItemSelectedListener;

    public void setLeftMenuItemSelectedListener(LeftMenuItemSelectedListener leftMenuItemSelectedListener) {
        this.leftMenuItemSelectedListener = leftMenuItemSelectedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leftmenu, container,
                    false);
            unbinder = ButterKnife.bind(this, rootView);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    private void initView() {
    }

    @OnClick({R.id.leftmenu_path, R.id.leftmenu_aboutus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.leftmenu_path:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("path", null);
                }
                break;
            case R.id.leftmenu_aboutus:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("about", null);
                }
                break;
        }
    }

}