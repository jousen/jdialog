package com.jousen.plugin.jdialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jousen.plugin.jdialog.listener.OnButtonClickListener;

public class JConfirmDialog extends BottomSheetDialog {
    private final Context context;
    private final View dialogView;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private OnButtonClickListener onButtonClickListener;
    private final TextView titleView;
    private final TextView textView;
    private final Button confirmView;
    private final Button cancelView;
    private int windowsHeight = 1920;

    public JConfirmDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        //获取屏幕高度
        getWindowHeight(context);
        //初始化弹窗
        dialogView = View.inflate(context, R.layout.jdialog_confirm, null);
        //初始化弹窗参数
        initDialogOption();
        //初始化弹窗内部元素

        confirmView = dialogView.findViewById(R.id.j_dialog_confirm);
        confirmView.setOnClickListener(v -> {
            onButtonClickListener.confirmClick();
            closeDialog();
        });
        cancelView = dialogView.findViewById(R.id.j_dialog_close);
        cancelView.setOnClickListener(v -> {
            onButtonClickListener.closeClick();
            closeDialog();
        });

        titleView = dialogView.findViewById(R.id.j_dialog_title);
        textView = dialogView.findViewById(R.id.j_dialog_text);
        int maxPixels = (int) (windowsHeight * 0.4);
        textView.setMaxHeight(maxPixels);
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
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * 设置title
     *
     * @param text 弹窗内容
     */
    public void setText(SpannableString text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * 设置title追加内容
     *
     * @param text 弹窗内容
     */
    public void appendText(SpannableString text) {
        if (textView != null) {
            textView.append(text);
        }
    }

    /**
     * 设置文字加粗
     */
    public void setTextBold() {
        if (textView == null) {
            return;
        }
        TextPaint paint = textView.getPaint();
        if (paint == null) {
            return;
        }
        paint.setFakeBoldText(true);
    }

    /**
     * 设置text可点击 内容过长时使用
     */
    @SuppressLint("ClickableViewAccessibility")
    public void setTextScrollable() {
        if (textView != null) {
            textView.setMovementMethod(LinkAndScrollMovement.getInstance());
            textView.setOnTouchListener(onTouchListener);
        }
    }

    /**
     * 设置确认按钮文字
     *
     * @param text 按钮文字
     */
    public void setConfirmText(String text) {
        if (confirmView != null) {
            confirmView.setText(text);
        }
    }

    /**
     * 设置取消按钮文字
     *
     * @param text 按钮文字
     */
    public void setCancelText(String text) {
        if (cancelView != null) {
            cancelView.setText(text);
        }
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

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener onTouchListener = (v, event) -> {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            v.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    };
}
