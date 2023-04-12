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
import com.jousen.plugin.jdialog.adapter.JDialogItem;
import com.jousen.plugin.jdialog.adapter.JDialogMultiListAdapter;
import com.jousen.plugin.jdialog.adapter.JDialogSingleListAdapter;
import com.jousen.plugin.jdialog.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class JListDialog {
    private final Context context;
    private final BottomSheetDialog bottomSheetDialog;
    private final BottomSheetBehavior<View> bottomSheetBehavior;
    private OnItemClickListener onItemClickListener;

    private final RecyclerView listView;
    private TextView titleView;//列表标题

    private List<JDialogItem> dialogItems;//列表数据
    private int gridColumn;//列表的列数
    private boolean hideIcon;//是否隐藏列表左侧图标
    private boolean boldText;//列表文字是否大写

    /**
     * 初始化弹窗(一列，顶部显示标题)
     *
     * @param context Context
     */
    public JListDialog(@NonNull Context context) {
        this(context, 1, false);
    }

    /**
     * 初始化弹窗(多列，顶部显示标题)
     *
     * @param context    Context
     * @param gridColumn 列数
     */
    public JListDialog(@NonNull Context context, int gridColumn) {
        this(context, gridColumn, false);
    }

    /**
     * 初始化弹窗
     *
     * @param context    Context
     * @param gridColumn 列数
     * @param hideTitle  是否隐藏顶部标题，若隐藏则显示为横线
     */
    public JListDialog(@NonNull Context context, int gridColumn, boolean hideTitle) {
        this.context = context;
        this.gridColumn = gridColumn;
        //获取屏幕高度
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int defaultDialogHeight = (int) (displayMetrics.heightPixels / 1.5);//弹窗默认高度
        //初始化弹窗界面
        View dialogView;
        if (hideTitle) {
            dialogView = View.inflate(context, R.layout.jdialog_list_simple, null);
        } else {
            dialogView = View.inflate(context, R.layout.jdialog_list_base, null);
            titleView = dialogView.findViewById(R.id.jdialog_title);
        }
        listView = dialogView.findViewById(R.id.jdialog_list);
        listView.setHasFixedSize(true);
        //初始化Bottom Sheet Dialog
        bottomSheetDialog = new BottomSheetDialog(this.context);
        bottomSheetDialog.setContentView(dialogView);
        //设置背景为透明
        try {
            ViewGroup parent = (ViewGroup) dialogView.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottomSheetBehavior = BottomSheetBehavior.from((View) dialogView.getParent());
        bottomSheetBehavior.setPeekHeight(defaultDialogHeight);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    closeDialog();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v1) {

            }
        });
        //初始化弹窗元素点击事件
        dialogView.findViewById(R.id.jdialog_close).setOnClickListener(v -> closeDialog());
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
        //1、多列不带图标
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
        //2、单列图标模式（图标在左侧）
        if (gridColumn == 1) {
            listView.setLayoutManager(new LinearLayoutManager(context));
            JDialogSingleListAdapter adapter = new JDialogSingleListAdapter(dialogItems, boldText);
            listView.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> {
                onItemClickListener.itemClick(position);
                closeDialog();
            });
            bottomSheetDialog.show();
            return;
        }
        //3、多列图标模式（图标在上侧）
        listView.setLayoutManager(new GridLayoutManager(context, gridColumn, RecyclerView.VERTICAL, false));
        JDialogGridAdapter adapter = new JDialogGridAdapter(dialogItems, boldText);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            onItemClickListener.itemClick(position);
            closeDialog();
        });
        bottomSheetDialog.show();
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
     * 设置列表文字加粗
     */
    public void setTextBold() {
        this.boldText = true;
    }

    /**
     * 点击控件回调
     *
     * @param listener 回调
     */
    public void onItemClick(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}