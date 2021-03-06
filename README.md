# jdialog

**Android bottom dialog base on material design BottomSheetDialog.** 

**Android 底部弹窗组件，基于谷歌的MaterialDesign BottomSheetDialog**

------

## 1、Feature 特性

- Support Android 5.1+       Android 5.1以上系统版本支持
- Support Only AndroidX    只支持 AndroidX
- Supports Android App Dark Theme  已适配Android暗黑模式

## 2、Import 依赖

1、Add the JitPack maven repository to the list of repositories 将JitPack存储库添加到您的构建文件中(项目根目录下build.gradle文件)

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

```
dependencies {
    implementation 'com.github.jousen:jdialog:2.0'
}
```

## 3、Usage 使用

##### 1、Info dialog 消息弹窗

```
JDialog jDialog = new JDialog(context);
jDialog.setTitle(title);//dialog title
jDialog.setText(text);//dialog content
jDialog.onButtonClick(new OnButtonClickListener() {
    @Override
    public void closeClick() {
        Toast.makeText(context, "点击了关闭按钮 click close button", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmClick() {
        Toast.makeText(context, "点击了确认按钮 click confirm button", Toast.LENGTH_SHORT).show();
    }
});
jDialog.show();
```

<img src="\img\Screenshot_1614988263.png" alt="Screenshot_1614988263" style="zoom: 25%;" />

------

##### 2、Confirm dialog 确认弹窗

```
JDialog jDialog = new JDialog(context, true);
jDialog.setTitle(title, 4);
jDialog.setText(text);
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



<img src="\img\Screenshot_1614988267.png" alt="Screenshot_1614988267" style="zoom: 25%;" />

------

##### 3、List dialog 列表弹窗

```
List<JDialogItem> jDialogItems = new ArrayList<>();
jDialogItems.add(new JDialogItem("纯文本1"));
jDialogItems.add(new JDialogItem(R.drawable.ic_test_icon, "文本+图标1"));
jDialogItems.add(new JDialogItem("长文本\n长文本\n长文本"));

JDialog jDialog = new JDialog(context, jDialogItems);
jDialog.onItemClick(position -> Toast.makeText(context, "点击了第 " + position + " 项", Toast.LENGTH_SHORT).show());
jDialog.show();
```



<img src="\img\Screenshot_1614988273.png" alt="Screenshot_1614988273" style="zoom:25%;" />



------

##### 4、Dark Mode 暗黑模式



<img src="\img\Screenshot_1614988287.png" alt="Screenshot_1614988287" style="zoom:25%;" />



# Licenses

```
Copyright 2021 jousen

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