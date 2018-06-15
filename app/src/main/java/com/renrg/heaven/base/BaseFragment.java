package com.renrg.heaven.base;

import android.content.Context;
import android.view.View;

import com.bangqu.lib.base.EshowFragment;

import butterknife.Unbinder;

public abstract class BaseFragment extends EshowFragment {

    protected Unbinder unbinder;
    protected View rootView;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null!=unbinder){
            unbinder.unbind();
        }
    }

}