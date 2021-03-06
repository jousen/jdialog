package com.jousen.plugin.jdialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.jousen.plugin.jdialog.listener.OnButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    private void bindView() {
        //普通消息弹窗
        findViewById(R.id.dialog_info).setOnClickListener(v -> {
            String title = "版本升级";
            String text = "本次更新:\n\n1. 修复bug\n2. 修复bug\n3. 修复bug\n4. 修复bug\n5. 修复bug\n1. 修复bug\n2. 修复bug\n3. 修复bug\n4. 修复bug\n5. 修复bug\n1. 修复bug\n2. 修复bug\n3. 修复bug\n4. 修复bug\n5. 修复bug\n1. 修复bug\n2. 修复bug\n3. 修复bug\n4. 修复bug\n5. 修复bug\n1. 修复bug\n2. 修复bug\n3. 修复bug\n4. 修复bug\n5. 修复bug\n";
            SpannableString user_protocol = new SpannableString("测试链接点击");
            user_protocol.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Toast.makeText(context, "点击了文本内链接", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);
                }
            }, 0, user_protocol.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            JDialog jDialog = new JDialog(context);
            jDialog.setTitle(title);
            jDialog.setText(text);
            jDialog.setTextScrollable();
            jDialog.appendText(user_protocol);
            jDialog.onButtonClick(new OnButtonClickListener() {
                @Override
                public void closeClick() {
                    Toast.makeText(context, "点击了关闭按钮", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void confirmClick() {
                    Toast.makeText(context, "点击了确认按钮", Toast.LENGTH_SHORT).show();
                }
            });
            jDialog.show();
        });

        //用户确认弹窗
        findViewById(R.id.dialog_confirm).setOnClickListener(v -> {
            String title = "删除确认删除确认删除确认删除确认删除确认";
            String text = "确定要删除吗？";

            JDialog jDialog = new JDialog(context, true);
            jDialog.setTitle(title, 4);//setTitle(String title, int titleMaxLength)
            jDialog.setText(text);
            jDialog.setConfirmText("立刻删除");
            jDialog.onButtonClick(new OnButtonClickListener() {
                @Override
                public void closeClick() {
                    Toast.makeText(context, "点击了取消按钮", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void confirmClick() {
                    Toast.makeText(context, "点击了确认按钮", Toast.LENGTH_SHORT).show();
                }
            });
            jDialog.show();
        });

        //列表弹窗
        findViewById(R.id.dialog_list).setOnClickListener(v -> {
            List<JDialogItem> jDialogItems = new ArrayList<>();
            jDialogItems.add(new JDialogItem("纯文本1"));
            jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标1"));
            jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));
            jDialogItems.add(new JDialogItem("纯文本2"));
            jDialogItems.add(new JDialogItem("纯文本3"));
            jDialogItems.add(new JDialogItem("纯文本4"));
            jDialogItems.add(new JDialogItem("纯文本5"));
            jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标2"));
            jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标3"));
            jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标4"));
            jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标5"));
            jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));
            jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));
            jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));
            jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));

            JDialog jDialog = new JDialog(context, jDialogItems);
            jDialog.onItemClick(position -> Toast.makeText(context, "点击了第 " + position + " 项", Toast.LENGTH_SHORT).show());
            jDialog.show();
        });

        //暗黑模式切换
        findViewById(R.id.day_night).setOnClickListener(v -> {
            MyApp app = (MyApp) getApplicationContext();
            if (app.day_night_mode == AppCompatDelegate.MODE_NIGHT_YES) {
                app.day_night_mode = AppCompatDelegate.MODE_NIGHT_NO;
            } else {
                app.day_night_mode = AppCompatDelegate.MODE_NIGHT_YES;
            }
            AppCompatDelegate.setDefaultNightMode(app.day_night_mode);
            recreate();
        });
    }
}