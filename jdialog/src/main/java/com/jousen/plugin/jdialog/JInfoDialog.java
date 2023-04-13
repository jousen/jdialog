package com.jousen.plugin.jdialog;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jousen.plugin.jdialog.listener.OnButtonClickListener;

public class JInfoDialog {
    private final BottomSheetDialog bottomSheetDialog;
    private final BottomSheetBehavior<View> bottomSheetBehavior;
    private OnButtonClickListener onButtonClickListener;
    private final TextView titleView;
    private final TextView textView;
    private final Button confirmView;

    public JInfoDialog(@NonNull Context context) {
        //获取屏幕高度 计算弹窗高度
        Resources res = context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        int defaultDialogHeight = (int) (displayMetrics.heightPixels / 1.5);//弹窗默认高度
        //初始化弹窗界面
        View dialogView = View.inflate(context, R.layout.jdialog_info, null);
        titleView = dialogView.findViewById(R.id.jdialog_title);
        textView = dialogView.findViewById(R.id.jdialog_text);
        confirmView = dialogView.findViewById(R.id.jdialog_confirm);
        //初始化Bottom Sheet Dialog
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setOnCancelListener(dialog -> onButtonClickListener.closeClick());
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
            public void onSlide(@NonNull View view, float v) {

            }
        });
        //初始化弹窗元素点击事件
        confirmView.setOnClickListener(v -> {
            onButtonClickListener.confirmClick();
            closeDialog();
        });
        dialogView.findViewById(R.id.jdialog_close).setOnClickListener(v -> {
            onButtonClickListener.closeClick();
            closeDialog();
        });
    }

    /**
     * 显示弹窗
     */
    public void show() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
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
     * 设置title
     *
     * @param title 弹窗标题
     */
    public void setTitle(String title) {
        titleView.setText(title);
    }

    /**
     * 返回title view 方便自定义设置
     *
     * @return titleView TextView
     */
    public TextView getTitleView() {
        return titleView;
    }

    /**
     * 设置text
     *
     * @param text 弹窗内容
     */
    public void setText(CharSequence text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * 设置text追加内容
     *
     * @param text 弹窗内容
     */
    public void appendText(CharSequence text) {
        if (textView != null) {
            textView.append(text);
        }
    }

    /**
     * 设置text文字加粗
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
     * 设置text文字居中
     */
    public void setTextCenter() {
        if (textView == null) {
            return;
        }
        textView.setGravity(Gravity.CENTER);
    }

    /**
     * 设置text可点击和滚动 内容过长时使用(不建议使用过长文本)
     */
    public void setTextMovement() {
        if (textView != null) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * 返回text view 方便自定义设置
     *
     * @return textView TextView
     */
    public TextView getTextView() {
        return textView;
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
     * 设置确认按钮文字颜色
     *
     * @param color 按钮文字颜色
     */
    public void setConfirmTextColor(@ColorInt int color) {
        if (confirmView != null) {
            confirmView.setTextColor(color);
        }
    }

    /**
     * 设置确认按钮样式
     *
     * @param resId 资源id
     */
    public void setConfirmBackgroundRes(int resId) {
        if (confirmView != null) {
            confirmView.setBackgroundResource(resId);
        }
    }

    /**
     * 返回Confirm Button View 方便自定义设置
     *
     * @return confirmView Button
     */
    public TextView getConfirmButtonView() {
        return confirmView;
    }

    /**
     * 点击控件回调
     *
     * @param listener 回调
     */
    public void onButtonClick(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }
}