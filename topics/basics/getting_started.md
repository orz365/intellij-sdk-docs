[//]: # (title: Creating Your First Plugin)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

本文档部分将帮助您开始为IntelliJ平台开发插件。
你可以使用 [IntelliJ IDEA社区版](https://www.jetbrains.com/idea/download/) 或是 [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/download/) 作为你的开发工具 (IntelliJ IDEA强烈推荐使用最新版本).
两者都包含完整的插件开发工具集。
为了更熟悉IntelliJ IDEA, 请参阅 [IntelliJ IDEA Web Help](https://www.jetbrains.com/idea/help/).

有三个支持的工作流可用来构建插件。
新项目推荐的工作流程是 [使用 GitHub 模板](#using-github-template) 或是 [使用 Gradle](#using-gradle) 从头开始创建。
老的 [Plugin DevKit](#using-devkit) 工作流程仍然支持现有项目。

 >  如果一个新的插件是基于Scala的，一个专用的SBT [sbt-idea-plugin](https://github.com/JetBrains/sbt-idea-plugin) 插件是可用的。
 >
 {type="note"}

Gradle工作流提供了几个优势：
  * 源代码集、模块和项目的表示是可移植的，
  * 任何规模或复杂性的项目通常都需要脚本来进行构建管理，而这些构建管理是Gradle本地处理的，
  * 通用Gradle主题的培训、文档和社区帮助随处可见。

针对于使用IntelliJ IDEA的Gradle插件来开发IntelliJ平台插件：
  *
  * 更改插件目标更方便，因为这都是在Gradle的构建文件中完成的：
      * 切换目标IntelliJ平台(IDE)的版本，
      * 改变目标基于IntelliJ平台的IDE，例如，从IntelliJ IDEA到PyCharm，
      * 在JetBrains运行时的其他版本上运行插件。
  * Gradle与持续集成系统完全集成， [JetBrains 插件仓库](https://plugins.jetbrains.com), 因此，可以很容易地定制和扩展构建和发布过程。
  * 内置的验证任务 <path>plugin.xml</path> ， 插件分布结构.
  * 内置集成 [IntelliJ 插件验证器](https://github.com/JetBrains/intellij-plugin-verifier) 用于执行兼容性检查的工具 [JetBrains 插件仓库](https://plugins.jetbrains.com).

## 使用GitHub模板

* [使用GitHub模板开发插件](../tutorials/github_template.md)

## 使用Gradle

* [使用Gradle开发插件](../tutorials/gradle_build_system.md)
    * [开始使用Gradle](../tutorials/build_system/gradle_prerequisites.md)
    * [配置Gradle项目](../tutorials/build_system/gradle_guide.md)
    * [用Gradle发布插件](../tutorials/build_system/deployment.md)

## 使用DevKit

* [使用DevKit开发插件](getting_started/using_dev_kit.md)
    * [建立开发环境](getting_started/setting_up_environment.md)
    * [创建插件项目](getting_started/creating_plugin_project.md)
    * [创建 Actions](getting_started/working_with_custom_actions.md)
    * [运行和调试插件](getting_started/running_and_debugging_a_plugin.md)
    * [部署插件](getting_started/deploying_plugin.md)
    * [发布插件](getting_started/publishing_plugin.md)
