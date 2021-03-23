package com.jousen.plugin.jdialog;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jousen.plugin.jdialog.adapter.JDialogGridAdapter;
import com.jousen.plugin.jdialog.adapter.JDialogListAdapter;
import com.jousen.plugin.jdialog.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class JListDialog extends BottomSheetDialog {
    private final Context context;
    private final View dialogView;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private OnItemClickListener onItemClickListener;
    private final TextView titleView;
    private final RecyclerView listView;

    private List<JDialogItem> dialogItems;
    private int listType = 0;//0 Linear 1 Grid
    private int gridColumn;
    private boolean hideIcon;
    private boolean boldText;
    private int windowsHeight = 1920;

    public JListDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        //获取屏幕高度
        getWindowHeight(context);
        //初始化弹窗
        dialogView = View.inflate(context, R.layout.jdialog_list, null);
        //初始化弹窗参数
        initDialogOption();
        //初始化弹窗内部元素
        titleView = dialogView.findViewById(R.id.j_dialog_title);
        dialogView.findViewById(R.id.j_dialog_close).setOnClickListener(v -> closeDialog());
        listView = dialogView.findViewById(R.id.j_dialog_list);
        listView.setHasFixedSize(true);
    }

    /**
     * 设置列表数据
     *
     * @param items 数据
     */
    public void setData(List<JDialogItem> items) {
        this.dialogItems = items;
    }

    /**
     * 设置为网格模式
     *
     * @param gridColumn 列数
     */
    public void setGridMode(int gridColumn) {
        this.listType = 1;
        this.gridColumn = gridColumn;
    }

    /**
     * 设置文字加粗
     */
    public void setTextBold() {
        this.boldText = true;
    }

    /**
     * 设置图标隐藏
     */
    public void setIconHide() {
        this.hideIcon = true;
    }

    /**
     * 显示弹窗
     */
    public void show() {
        //检查列表数据
        if (dialogItems == null) {
            dialogItems = new ArrayList<>();
        }
        //设置显示样式
        if (listType == 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            listView.setLayoutManager(linearLayoutManager);

            JDialogListAdapter adapter = new JDialogListAdapter(dialogItems, hideIcon, boldText);
            listView.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> {
                onItemClickListener.itemClick(position);
                closeDialog();
            });

            bottomSheetDialog.show();
            return;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, gridColumn);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listView.setLayoutManager(gridLayoutManager);

        JDialogGridAdapter adapter = new JDialogGridAdapter(dialogItems, hideIcon, boldText);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            onItemClickListener.itemClick(position);
            closeDialog();
        });

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
     * 点击控件回调
     *
     * @param listener 回调
     */
    public void onItemClick(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
        int defaultHeight = (int) (windowsHeight / 1.5);
        bottomSheetBehavior.setPeekHeight(defaultHeight);
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
     * 获取屏幕高度
     *
     * @param context 上下文
     */
    private void getWindowHeight(Context context) {
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        this.windowsHeight = displayMetrics.heightPixels;
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