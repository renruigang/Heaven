package com.renrg.heaven.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;

import com.renrg.heaven.HeavenApplication;
import com.renrg.heaven.R;
import com.renrg.heaven.activity.fragment.ContentFragment;
import com.renrg.heaven.activity.fragment.LeftMenuFragment;
import com.renrg.heaven.base.BaseActivity;
import com.renrg.heaven.listener.LeftMenuItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private LeftMenuFragment mLeftMenuFragment;
    private ContentFragment mCurrentFragment;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        Toolbar toolbar = mToolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mDrawerLayout = findViewById(R.id.id_drawerlayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mFragmentManager = getSupportFragmentManager();
        mLeftMenuFragment = (LeftMenuFragment) mFragmentManager.findFragmentById(R.id.id_left_menu_container);
        mCurrentFragment = (ContentFragment) mFragmentManager.findFragmentById(R.id.id_content_container);
        if (mCurrentFragment == null) {
            mCurrentFragment = new ContentFragment();
            mFragmentManager.beginTransaction().add(R.id.id_content_container, mCurrentFragment).commit();
        }
        if (mLeftMenuFragment == null) {
            mLeftMenuFragment = new LeftMenuFragment();
            mFragmentManager.beginTransaction().add(R.id.id_left_menu_container, mLeftMenuFragment).commit();
            mLeftMenuFragment.setLeftMenuItemSelectedListener(new LeftMenuItemSelectedListener() {
                @Override
                public void onMenuItemSelected(String tag, Object obj) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    switch (tag) {
                        case "path":
                            goToActivity(DirChoiceActivity.class);
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void addViewListener() {

    }

    //获取权限相关操作
    private List<String> needPermission;
    private final int REQUEST_CODE_PERMISSION = 0;
    private String[] permissionArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void requestData() {
        super.requestData();
        askMultiplePermission();
    }

    public void askMultiplePermission() {
        needPermission = new ArrayList<>();
        for (String permissionName :
                permissionArray) {
            if (!checkIsAskPermission(this, permissionName)) {
                needPermission.add(permissionName);
            }
        }
        if (needPermission.size() > 0) {
            //开始申请权限
            ActivityCompat.requestPermissions(this, needPermission.toArray(new String[needPermission.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    public boolean checkIsAskPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkIsAskPermissionState(Map<String, Integer> maps, String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (maps.get(list[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                Map<String, Integer> permissionMap = new HashMap<>();
                for (String name : needPermission) {
                    permissionMap.put(name, PackageManager.PERMISSION_GRANTED);
                }
                for (int i = 0; i < permissions.length; i++) {
                    permissionMap.put(permissions[i], grantResults[i]);
                }
                if (checkIsAskPermissionState(permissionMap, permissions)) {
                    //获取数据
                } else {
                    //提示权限获取不完成，可能有的功能不能使用
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*双击返回键退出*/
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                HeavenApplication.getInstance().exitApplication();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
