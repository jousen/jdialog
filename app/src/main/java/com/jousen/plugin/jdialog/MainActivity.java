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
        findViewById(R.id.dialog_info).setOnClickListener(v -> infoDialog());
        //用户确认弹窗
        findViewById(R.id.dialog_confirm).setOnClickListener(v -> confirmDialog());
        //单列带图标弹窗
        findViewById(R.id.dialog_list).setOnClickListener(v -> listDialog());
        //网格带图标弹窗
        findViewById(R.id.dialog_grid).setOnClickListener(v -> gridDialog());
        //多列不带图标弹窗
        findViewById(R.id.dialog_multi_list).setOnClickListener(v -> multiListDialog());
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

    private void infoDialog() {
        String title = "版本升级";
        String text = "本次更新:\n\n1. 修复bug\n2. 修复bug\n3. 修复bug";
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

        JInfoDialog jDialog = new JInfoDialog(context);
        jDialog.setTitle(title);
        jDialog.setText(text);
        jDialog.appendText(user_protocol);
        jDialog.setTextBold();
        jDialog.setTextMovement();
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
    }

    private void confirmDialog() {
        String title = "删除确认删除确认删除确认删除确认删除确认";
        String text = "确定要删除吗？";

        JConfirmDialog jDialog = new JConfirmDialog(context);
        jDialog.setTitle(title, 4);//setTitle(String title, int titleMaxLength)
        jDialog.setText(text);
        jDialog.setTextBold();
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
    }

    private void listDialog() {
        List<JDialogItem> jDialogItems = new ArrayList<>();
        jDialogItems.add(new JDialogItem(R.drawable.ic_box, "文本+图标2"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_box, "文本+图标2"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标1"));
        jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));
        jDialogItems.add(new JDialogItem("纯文本2"));
        jDialogItems.add(new JDialogItem("纯文本3"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标3"));
        jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));
        jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));

        JListDialog jDialog = new JListDialog(context);
        jDialog.setData(jDialogItems);
        jDialog.setTextBold();
        jDialog.onItemClick(position -> Toast.makeText(context, "点击了第 " + position + " 项", Toast.LENGTH_SHORT).show());
        jDialog.show();
    }

    private void gridDialog() {
        List<JDialogItem> jDialogItems = new ArrayList<>();
        jDialogItems.add(new JDialogItem(R.drawable.ic_box, "网格文本1"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "网格文本2"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "网格文本3"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "网格文本4"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "网格文本5"));
        jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "网格文本6"));

        JListDialog jDialog = new JListDialog(context,4,false);
        jDialog.setData(jDialogItems);
        jDialog.onItemClick(position -> Toast.makeText(context, "点击了第 " + position + " 项", Toast.LENGTH_SHORT).show());
        jDialog.show();
    }

    private void multiListDialog() {
        List<JDialogItem> jDialogItems = new ArrayList<>();
        jDialogItems.add(new JDialogItem("文本1"));
        jDialogItems.add(new JDialogItem("文本2"));
        jDialogItems.add(new JDialogItem("文本3"));
        jDialogItems.add(new JDialogItem("文本4"));
        jDialogItems.add(new JDialogItem("文本5"));

        JListDialog jDialog = new JListDialog(context,3,true);
        jDialog.setData(jDialogItems);
        jDialog.onItemClick(position -> Toast.makeText(context, "点击了第 " + position + " 项", Toast.LENGTH_SHORT).show());
        jDialog.show();
    }
}