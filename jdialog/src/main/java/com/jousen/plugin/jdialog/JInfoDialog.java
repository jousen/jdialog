package com.jousen.plugin.jdialog;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.jousen.plugin.jdialog.listener.OnButtonClickListener;

public class JInfoDialog {
    private final AlertDialog dialog;
    private final TextView titleView;
    private final TextView textView;
    private final Button confirmView;
    private OnButtonClickListener onButtonClickListener;

    public JInfoDialog(@NonNull Context context) {
        //弹窗界面
        View dialogView = View.inflate(context, R.layout.jdialog_info, null);
        //初始化弹窗参数
        dialog = new AlertDialog.Builder(context, R.style.JDialogStyle).setView(dialogView).setCancelable(true).create();
        //初始化弹窗内部元素
        dialogView.findViewById(R.id.jdialog_close).setOnClickListener(v -> {
            onButtonClickListener.closeClick();
            closeDialog();
        });
        confirmView = dialogView.findViewById(R.id.jdialog_confirm);
        confirmView.setOnClickListener(v -> {
            onButtonClickListener.confirmClick();
            closeDialog();
        });
        titleView = dialogView.findViewById(R.id.jdialog_title);
        textView = dialogView.findViewById(R.id.jdialog_text);
    }

    /**
     * 显示弹窗
     */
    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    /**
     * 设置title
     *
     * @param title 弹窗标题
     */
    public void setTitle(String title) {
        titleView.setText(StrSub.limit(title, 16));
    }

    /**
     * 设置title并限定长度
     *
     * @param title          弹窗标题
     * @param titleMaxLength 标题最大长度
     */
    public void setTitle(String title, int titleMaxLength) {
        titleView.setText(StrSub.limit(title, titleMaxLength));
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
     * 设置文字居中
     */
    public void setTextCenter() {
        if (textView == null) {
            return;
        }
        textView.setGravity(Gravity.CENTER);
    }

    /**
     * 设置text可滚动 内容过长时使用(不建议使用过长文本)
     * {@link JInfoDialog#setTextMovement}
     */
    @Deprecated
    public void setTextScrollable() {
        setTextMovement();
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
     * 点击控件回调
     *
     * @param listener 回调
     */
    public void onButtonClick(OnButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }

    /**
     * 销毁弹窗
     */
    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}