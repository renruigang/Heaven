package com.renrg.heaven.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.bangqu.greendao.entity.DownLoadTask;
import com.bangqu.lib.utils.AppUtils;
import com.renrg.heaven.R;
import com.renrg.heaven.comm.Constants;
import com.renrg.heaven.util.SharedPref;

/**
 * Created by Administrator on 2016/7/4.
 */
public class TaskAddDialog extends Dialog {

    private EditText taskUrl, taskName, taskPath;
    private Context mContext;
    private OnOperaClickedListener onOperaClickedListener;
    private SharedPref sharedPref;

    public void setTaskUrl(String url) {
        taskUrl.setText(url);
        if (url.lastIndexOf("/") > 0) {
            taskName.setText(url.substring(url.lastIndexOf("/") + 1));
        }
    }

    public TaskAddDialog(Context context, OnOperaClickedListener listener) {
        super(context, R.style.menu_dialog_style);
        mContext = context;
        sharedPref = new SharedPref(context);
        onOperaClickedListener = listener;
        setContentView(R.layout.dialog_taskadd);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (AppUtils.getDisplayMetrics(context).widthPixels * 0.8); //
        window.setAttributes(p);
        taskUrl = findViewById(R.id.task_url);
        taskName = findViewById(R.id.task_name);
        taskPath = findViewById(R.id.task_path);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.task_ok) {
                    if (onOperaClickedListener != null) {
                        if (TextUtils.isEmpty(taskUrl.getText())) {
                            onOperaClickedListener.onNullInputNotice();
                            return;
                        }
                        DownLoadTask task = new DownLoadTask();
                        task.setDownload_url(taskUrl.getText().toString());
                        task.setFile_name(taskName.getText().toString());
                        task.setSave_path(taskPath.getText().toString());
                        onOperaClickedListener.operaClickedListener(task, true);
                    }
                    AppUtils.hideSoftInput(mContext, taskUrl);
                    dismiss();
                } else if (i == R.id.task_cancel) {
                    AppUtils.hideSoftInput(mContext, taskUrl);
                    if (onOperaClickedListener != null) {
                        onOperaClickedListener.operaClickedListener(null, false);
                    }
                    dismiss();
                }
            }
        };
        findViewById(R.id.task_ok).setOnClickListener(onClickListener);
        findViewById(R.id.task_cancel).setOnClickListener(onClickListener);
    }

    @Override
    public void show() {
        super.show();
        taskPath.setText(sharedPref.getString(Constants.SAVE_PATH));
    }

    public interface OnOperaClickedListener {
        void operaClickedListener(DownLoadTask value, boolean result);

        void onNullInputNotice();
    }

}
