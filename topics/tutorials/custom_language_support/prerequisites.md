[//]: # (title: 1. Prerequisites)

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

## 下载并安装IntelliJ IDEA
载并安装IntelliJ IDEA Ultimate或IntelliJ IDEA社区版  [点击下载](https://www.jetbrains.com/idea/download/) 。

## 查看社区版源文件
 > 虽然不是必需的，但拥有平台的全部资源和所有可浏览的绑定插件，就可以找到相关的实现。
 >
 {type="note"}

按照IntelliJ IDEA社区版的描述下载IntelliJ IDEA社区版的源文件 [README](upsource:///README.md) 文件.

## 安装必要的插件
确保已启用绑定的*Plugin DevKit* 插件。
安装并启用 [Grammar-Kit](https://plugins.jetbrains.com/plugin/6606-grammar-kit) 和 [PsiViewer](https://plugins.jetbrains.com/plugin/227-psiviewer) 插件.

## 创建项目
创建一个 [IntelliJ 平台插件项目](../build_system/gradle_prerequisites.md).
