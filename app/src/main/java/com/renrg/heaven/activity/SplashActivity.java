package com.renrg.heaven.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.renrg.heaven.base.BaseActivity;
import com.renrg.heaven.comm.Constants;

public class SplashActivity extends BaseActivity {

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, Constants.SPLASH_TIME);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addViewListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
