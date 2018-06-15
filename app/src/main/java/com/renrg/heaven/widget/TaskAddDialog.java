package com.renrg.heaven.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.bangqu.lib.utils.AppUtils;
import com.renrg.heaven.R;
import com.renrg.heaven.model.Task;

/**
 * Created by Administrator on 2016/7/4.
 */
public class TaskAddDialog extends Dialog {

    private EditText taskUrl, taskName;
    private Context mContext;
    private OnOperaClickedListener onOperaClickedListener;

    public void setTaskUrl(String url) {
        taskUrl.setText(url);
        if (url.lastIndexOf("/") > 0) {
            taskName.setText(url.substring(url.lastIndexOf("/") + 1));
        }
    }

    public TaskAddDialog(Context context, OnOperaClickedListener listener) {
        super(context, R.style.menu_dialog_style);
        mContext = context;
        onOperaClickedListener = listener;
        setContentView(R.layout.dialog_taskadd);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (AppUtils.getDisplayMetrics(context).widthPixels * 0.8); //
        window.setAttributes(p);
        taskUrl = findViewById(R.id.task_url);
        taskName = findViewById(R.id.task_name);
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
                        onOperaClickedListener.operaClickedListener(new Task(), true);
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

    public interface OnOperaClickedListener {
        void operaClickedListener(Task value, boolean result);

        void onNullInputNotice();
    }

}
