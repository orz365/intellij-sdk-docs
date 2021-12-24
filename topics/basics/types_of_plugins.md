[//]: # (title: Main Types of Plugins)

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

Products based on the IntelliJ Platform can be modified and adjusted for custom purposes by adding plugins.
All downloadable plugins are available at the [JetBrains Plugins Repository](https://plugins.jetbrains.com/).

基于IntelliJ平台的产品可以通过添加插件来修改和调整。
所有可下载的插件都可以在[JetBrains插件仓库](https://plugins.jetbrains.com/) 里下载。

插件最插件的类型包括：

* UI Themes（主题）
* Custom language support（自定义语言支持）
* Framework integration（框架整合）
* Tool integration（工具整合）
* User interface add-ons（用户界面插件）

## UI主题

[主题](themes_intro.md) 让设计人员能够自定义内置IDE UI元素的外观。
自定义UI主题可以:

* 替换图标,
* 改变图标和UI控件的颜色,
* 改变UI控件的边框和插入,
* 提供自定义编辑器方案,
* 添加背景图片.

[UI主题下载](https://plugins.jetbrains.com/search?headline=164-theme&tags=Theme) 证实了创造性的可能.


## 自定义语言支持

自定义语言支持为使用特定编程语言提供了基本功能，包括:

* 文件类型识别
* 词法分析
* 语法高亮显示
* 格式化
* 代码洞察力和代码完成
* 检查和快速解决
* 意图动作

插件还可以增强现有的(绑定的)定制语言，例如，通过提供额外的检查、意图或任何其他特性。

请参阅[自定义语言支持教程](custom_language_support_tutorial.md) 了解更多有关该主题的信息。

## Framework Integration

框架集成包括改进的代码洞察特性(对于给定的框架来说是典型的)，以及直接从IDE使用特定于框架的功能的选项。
有时它还包括自定义语法或DSL的语言支持元素。

* 具体代码的洞察力
* 直接访问特定于框架的功能

参阅 [Struts 2 插件](https://github.com/JetBrains/intellij-plugins/tree/master/struts2) 作为框架集成的一个例子。
更多的参考插件可以在 [JetBrains 插件仓库](https://plugins.jetbrains.com/search?orderBy=update%20date&shouldHaveSource=true&tags=Framework%20integration).

## 工具集成

工具集成使得直接从IDE操作第三方工具和组件成为可能，而不需要切换上下文，这意味着:

* 额外动作的实现
* 相关的UI组件
* 访问外部资源

    参阅 [Gerrit集成](https://plugins.jetbrains.com/plugin/7272?pr=idea) 以插件为例。

## 用户界面插件

这类插件应用于IDE标准用户界面的各种变化。
一些新添加的组件是交互式的，并提供了新的功能，而另一些则仅限于视觉修改。
  [随机背景](https://plugins.jetbrains.com/plugin/9692-random-background) 插件就是一个例子。
