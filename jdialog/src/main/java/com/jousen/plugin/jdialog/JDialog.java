package com.jousen.plugin.jdialog;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jousen.plugin.jdialog.listener.OnButtonClickListener;
import com.jousen.plugin.jdialog.listener.OnItemClickListener;

import java.util.List;

public class JDialog extends BottomSheetDialog {
    private final Context context;
    private View dialogView;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private OnButtonClickListener onButtonClickListener;
    private OnItemClickListener onItemClickListener;

    private TextView titleView;
    private TextView textView;

    public JDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initInfoDialog(context, false);
    }

    public JDialog(@NonNull Context context, boolean isConfirm) {
        super(context);
        this.context = context;
        initInfoDialog(context, isConfirm);
    }

    public JDialog(@NonNull Context context, List<JDialogItem> dialogItems) {
        super(context);
        this.context = context;
        //初始化弹窗
        dialogView = View.inflate(context, R.layout.j_dialog_list, null);
        //初始化弹窗参数
        initDialogOption();
        //初始化弹窗内部元素
        titleView = dialogView.findViewById(R.id.j_dialog_title);
        dialogView.findViewById(R.id.j_dialog_close).setOnClickListener(v -> closeDialog());
        RecyclerView listView = dialogView.findViewById(R.id.j_dialog_list);
        listView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);

        JDialogListAdapter adapter = new JDialogListAdapter(dialogItems);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            onItemClickListener.itemClick(position);
            closeDialog();
        });
    }

    /**
     * 显示弹窗
     */
    public void show() {
        bottomSheetDialog.show();
    }

    /**
     * 设置title
     *
     * @param title 弹窗标题
     */
    public void setTitle(String title) {
        titleView.setText(subString(title, 16));
    }

    /**
     * 设置title并限定长度
     *
     * @param title          弹窗标题
     * @param titleMaxLength 标题最大长度
     */
    public void setTitle(String title, int titleMaxLength) {
        titleView.setText(subString(title, titleMaxLength));
    }

    /**
     * 设置title
     *
     * @param text 弹窗内容
     */
    public void setText(String text) {
        textView.setText(text);
    }

    /**
     * 点击控件回调
     *
     * @param listener 回调
     */
    public void onButtonClick(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }

    /**
     * 点击控件回调
     *
     * @param listener 回调
     */
    public void onItemClick(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private void initInfoDialog(@NonNull Context context, boolean isConfirm) {
        //初始化弹窗
        if (isConfirm) {
            dialogView = View.inflate(context, R.layout.j_dialog_confirm, null);
        } else {
            dialogView = View.inflate(context, R.layout.j_dialog_info, null);
        }
        //初始化弹窗参数
        initDialogOption();
        //初始化弹窗内部元素
        dialogView.findViewById(R.id.j_dialog_close).setOnClickListener(v -> {
            onButtonClickListener.closeClick();
            closeDialog();
        });
        dialogView.findViewById(R.id.j_dialog_confirm).setOnClickListener(v -> {
            onButtonClickListener.confirmClick();
            closeDialog();
        });
        titleView = dialogView.findViewById(R.id.j_dialog_title);
        textView = dialogView.findViewById(R.id.j_dialog_text);
    }

    /**
     * 设置弹窗参数
     */
    private void initDialogOption() {
        //初始化dialog
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(dialogView);
        //设置背景为透明
        try {
            ViewGroup parent = (ViewGroup) dialogView.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottomSheetBehavior = BottomSheetBehavior.from((View) dialogView.getParent());
        bottomSheetBehavior.setPeekHeight(getWindowHeight(context));
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    closeDialog();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    /**
     * 销毁弹窗
     */
    private void closeDialog() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }

    /**
     * 获取弹窗默认屏幕高度（默认显示三分之二）
     *
     * @param context 上下文
     * @return int
     */
    private int getWindowHeight(Context context) {
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels - displayMetrics.heightPixels / 3;
    }

    /**
     * 标题长度限制
     *
     * @param string         标题
     * @param titleMaxLength 最大长度
     * @return string
     */
    private String subString(String string, int titleMaxLength) {
        if (string == null) {
            return "";
        }
        if (titleMaxLength == 0 || string.length() < titleMaxLength) {
            return string;
        }
        return string.substring(0, titleMaxLength) + "…";
    }
}
