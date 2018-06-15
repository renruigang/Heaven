package com.renrg.heaven.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.bangqu.lib.base.EshowActivity;
import com.renrg.heaven.HeavenApplication;
import com.renrg.heaven.util.SharedPref;

import butterknife.ButterKnife;

/*
* 方法执行顺序
*  1、setLayoutView(savedInstanceState);
*  2、initView();
*  3、requestData();
*  4、addViewListener();
* */
public class BaseActivity extends EshowActivity {

    protected SharedPref sharedPref;
    protected Context mContext;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        HeavenApplication.getInstance().addStackActivity(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mContext = this;
        sharedPref = new SharedPref(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void addViewListener() {

    }

    @Override
    protected <T> void addToRequestQueue(Request<T> req) {

    }

    protected void setTextValue(TextView tv, String value) {
        if (!TextUtils.isEmpty(value)) {
            tv.setText(value);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
