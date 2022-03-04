# jdialog

**Android 常用弹窗组件，基于谷歌的MaterialDesign BottomSheetDialog**

**Android bottom dialog base on material design BottomSheetDialog.**

------

## 1、特性 Feature

- Android 5.0以上系统版本支持 Support Android 5.0+
- 只支持 AndroidX Support Only AndroidX
- 已适配Android暗黑模式 Supports Android App Dark Theme

## 2、依赖 Import

1、将JitPack存储库添加到您的构建文件中(项目根目录下build.gradle文件)

Add the JitPack maven repository to the list of repositories

**build.gradle**

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2、Add dependencies

[![](https://jitpack.io/v/jousen/jdialog.svg)](https://jitpack.io/#jousen/jdialog)

```
dependencies {
    implementation 'com.github.jousen:jdialog:4.5'
}
```

## 3、使用 Usage

##### 1、消息弹窗 Info dialog

```
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
```

<img src="https://github.com/jousen/jdialog/blob/main/img/1.png"/>

------

##### 2、Confirm dialog 确认弹窗

```
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
```

<img src="https://github.com/jousen/jdialog/blob/main/img/2.png"/>

------

##### 3、列表左侧带图标弹窗 List dialog with icon

```
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
```

<img src="https://github.com/jousen/jdialog/blob/main/img/3.png"/>

------

##### 4、网格带图标弹窗 grid dialog with icon

```
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
```

<img src="https://github.com/jousen/jdialog/blob/main/img/4.png"/>

------

##### 5、多列不带图标弹窗 multi list no icon

<img src="https://github.com/jousen/jdialog/blob/main/img/5.png"/>

```
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
```

##### 6、Dark Mode 暗黑模式

<img src="https://github.com/jousen/jdialog/blob/main/img/6.png"/>

##### 7、JListDialog 参数

```
    @param context    Context
    @param gridColumn 列数
    @param lineTopBar list顶部bar是否为横线
    public JListDialog(@NonNull Context context, int gridColumn, boolean lineTopBar)
```

## 4、注意

关闭activity或因切换横竖屏导致activity销毁重建时，最好检查下dialog是否已关闭，若未关闭，可能导致内存问题

------

## Licenses

```
Copyright 2022 jousen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```