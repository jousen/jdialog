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
import com.jousen.plugin.jdialog.adapter.JDialogMultiListAdapter;
import com.jousen.plugin.jdialog.adapter.JDialogSingleListAdapter;
import com.jousen.plugin.jdialog.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class JListDialog {
    private final Context context;
    private final View dialogView;
    private TextView titleView;
    private final RecyclerView listView;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private OnItemClickListener onItemClickListener;
    private List<JDialogItem> dialogItems;
    private int gridColumn;
    private boolean hideIcon;
    private boolean boldText;
    private int windowsHeight = 1920;

    /**
     * 初始化
     *
     * @param context Context
     */
    public JListDialog(@NonNull Context context) {
        this(context, 1, false);
    }

    /**
     * 初始化
     *
     * @param context    Context
     * @param gridColumn 列数
     */
    public JListDialog(@NonNull Context context, int gridColumn) {
        this(context, gridColumn, false);
    }

    /**
     * 初始化
     *
     * @param context    Context
     * @param gridColumn 列数
     * @param lineTopBar list顶部bar是否为横线
     */
    public JListDialog(@NonNull Context context, int gridColumn, boolean lineTopBar) {
        this.context = context;
        this.gridColumn = gridColumn;
        //获取屏幕高度
        getWindowHeight(context);
        //初始化弹窗
        if (lineTopBar) {
            dialogView = View.inflate(context, R.layout.jdialog_list_simple, null);
        } else {
            dialogView = View.inflate(context, R.layout.jdialog_list, null);
            titleView = dialogView.findViewById(R.id.jdialog_title);
        }
        //初始化弹窗参数
        initDialogOption();
        //初始化弹窗内部元素
        dialogView.findViewById(R.id.jdialog_close).setOnClickListener(v -> closeDialog());
        listView = dialogView.findViewById(R.id.jdialog_list);
        listView.setHasFixedSize(true);
    }

    /**
     * 设置为网格模式
     *
     * @param gridColumn 列数
     */
    public void setGridMode(int gridColumn) {
        this.gridColumn = gridColumn;
    }

    /**
     * 设置图标隐藏
     */
    public void setIconHide() {
        this.hideIcon = true;
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
     * 设置文字加粗
     */
    public void setTextBold() {
        this.boldText = true;
    }

    /**
     * 显示弹窗
     */
    public void show() {
        if (bottomSheetDialog == null) {
            return;
        }
        //检查列表数据
        if (dialogItems == null) {
            dialogItems = new ArrayList<>();
        }
        //1、不带图标模式
        if (hideIcon) {
            listView.setLayoutManager(new GridLayoutManager(context, gridColumn, RecyclerView.VERTICAL, false));
            JDialogMultiListAdapter adapter = new JDialogMultiListAdapter(dialogItems, boldText);
            listView.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> {
                onItemClickListener.itemClick(position);
                closeDialog();
            });
            bottomSheetDialog.show();
            return;
        }
        //2、单列带图标模式（图标在左侧）
        if (gridColumn == 1) {
            JDialogSingleListAdapter adapter = new JDialogSingleListAdapter(dialogItems, boldText);
            listView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            listView.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> {
                onItemClickListener.itemClick(position);
                closeDialog();
            });
            bottomSheetDialog.show();
            return;
        }
        listView.setLayoutManager(new GridLayoutManager(context, gridColumn, RecyclerView.VERTICAL, false));
        //3、多列带图标模式（图标在上侧）
        JDialogGridAdapter adapter = new JDialogGridAdapter(dialogItems, boldText);
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
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    /**
     * 设置title并限定长度
     *
     * @param title          弹窗标题
     * @param titleMaxLength 标题最大长度
     */
    public void setTitle(String title, int titleMaxLength) {
        if (titleView != null) {
            titleView.setText(StrSub.limit(title, titleMaxLength));
        }
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
    public void closeDialog() {
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
}