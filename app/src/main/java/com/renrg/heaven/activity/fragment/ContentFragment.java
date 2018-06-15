package com.renrg.heaven.activity.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangqu.download.presenter.DownLoadImpl;
import com.bangqu.download.service.DownloadService;
import com.bangqu.download.utils.FileUtil;
import com.renrg.heaven.R;
import com.renrg.heaven.base.BaseFragment;
import com.renrg.heaven.model.Task;
import com.renrg.heaven.widget.TaskAddDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class ContentFragment extends BaseFragment implements DownLoadImpl {

    @BindView(R.id.content_recycler)
    RecyclerView recyclerView;

    private boolean isBindService;
    private TaskAddDialog taskAddDialog;
    DownloadService downloadService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_content, container, false);
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
        taskAddDialog = new TaskAddDialog(getContext(), onOperaClickedListener);
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipData.Item item = clipData.getItemAt(0);
            String clip = item.getText().toString();
//            clip = "ftp://ygdy8:ygdy8@yg90.dydytt.net:8340/阳光电影www.ygdy8.com.卧底巨星.BD.720p.国粤双语中字.mkv";
            if (!TextUtils.isEmpty(clip) && checkUrl(clip)) {
                taskAddDialog.setTaskUrl(clip);
                taskAddDialog.show();
            }
        }
    }

    TaskAddDialog.OnOperaClickedListener onOperaClickedListener = new TaskAddDialog.OnOperaClickedListener() {
        @Override
        public void operaClickedListener(Task value, boolean result) {

        }

        @Override
        public void onNullInputNotice() {
            showToast("请输入下载链接地址！");
        }
    };

    @OnClick({R.id.content_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.content_add:
                taskAddDialog.show();
                break;
        }
    }

    public boolean checkUrl(String url) {
        return url.startsWith("http") || url.startsWith("ftp");
    }

    @Override
    public void bindService(String apkUrl, String fileName) {
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, apkUrl);
        intent.putExtra(DownloadService.BUNDLE_KEY_FILENAME, fileName);
        isBindService = getActivity().getApplicationContext().bindService(intent, conn, Activity.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
            downloadService = binder.getService();
            //接口回调，下载进度
            downloadService.setOnProgressListener(new DownloadService.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {

                }

                @Override
                public void onProgressDone(File downloadFile) {
                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (isBindService) {
                        getActivity().getApplicationContext().unbindService(conn);
                        isBindService = false;
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
