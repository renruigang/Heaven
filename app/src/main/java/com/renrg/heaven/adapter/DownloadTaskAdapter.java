package com.renrg.heaven.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangqu.greendao.entity.DownLoadTask;
import com.bangqu.lib.base.BaseRecyclerAdapter;
import com.bangqu.lib.widget.TextViewPlus;
import com.renrg.heaven.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-5-23 0023.
 */

public class DownloadTaskAdapter extends BaseRecyclerAdapter<DownLoadTask> {

    public DownloadTaskAdapter(Context mContext, List<DownLoadTask> mData) {
        super(mContext, mData);
    }

    @Override
    protected RecyclerView.ViewHolder initViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_downloadtask, null);
        return new ViewHolder(view);
    }

    @Override
    protected void bindingViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DownLoadTask task = mData.get(position);
        viewHolder.taskFilename.setText(task.getFile_name());
        viewHolder.taskSize.setText(task.getSave_path());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.task_icon)
        ImageView taskIcon;
        @BindView(R.id.task_filename)
        TextView taskFilename;
        @BindView(R.id.task_size)
        TextView taskSize;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
