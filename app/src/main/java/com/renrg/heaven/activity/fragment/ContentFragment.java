package com.renrg.heaven.activity.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangqu.greendao.dao.DownLoadTaskDao;
import com.bangqu.greendao.db.DbManager;
import com.bangqu.greendao.entity.DownLoadTask;
import com.renrg.heaven.R;
import com.renrg.heaven.adapter.DownloadTaskAdapter;
import com.renrg.heaven.base.BaseFragment;
import com.renrg.heaven.util.DownloadUtils;
import com.renrg.heaven.widget.TaskAddDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class ContentFragment extends BaseFragment {

    @BindView(R.id.content_recycler)
    RecyclerView recyclerView;

    private TaskAddDialog taskAddDialog;
    private List<DownLoadTask> downLoadTaskList = new ArrayList<>();
    private DownloadTaskAdapter downloadTaskAdapter;
    private DownLoadTaskDao downLoadTaskDao;

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
        downLoadTaskDao = DbManager.getDaoSession(getContext()).getDownLoadTaskDao();
        taskAddDialog = new TaskAddDialog(getContext(), onOperaClickedListener);
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            ClipData.Item item = clipData.getItemAt(0);
            String clip = item.getText().toString();
            clip = "ftp://ygdy8:ygdy8@yg90.dydytt.net:8340/阳光电影www.ygdy8.com.卧底巨星.BD.720p.国粤双语中字.mkv";
            if (!TextUtils.isEmpty(clip) && checkUrl(clip)) {
                taskAddDialog.setTaskUrl(clip);
                taskAddDialog.show();
            }
        }
        downLoadTaskList.addAll(downLoadTaskDao.loadAll());
        downloadTaskAdapter = new DownloadTaskAdapter(getContext(), downLoadTaskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(downloadTaskAdapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL_LIST, 2f, Color.TRANSPARENT));
    }

    TaskAddDialog.OnOperaClickedListener onOperaClickedListener = new TaskAddDialog.OnOperaClickedListener() {
        @Override
        public void operaClickedListener(DownLoadTask value, boolean result) {
            if (result)
                addIntoDownLoadTask(value);
        }

        @Override
        public void onNullInputNotice() {
            showToast("请输入下载链接地址！");
        }
    };

    private void addIntoDownLoadTask(DownLoadTask value) {
        downLoadTaskList.add(value);
        downloadTaskAdapter.notifyDataSetChanged();
        long taskId = DownloadUtils.getInstance(getContext()).addDownloadTask(value.getDownload_url(), value.getFile_name());
        value.setDownload_id(taskId);
        downLoadTaskDao.insertInTx(value);
    }

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

}
