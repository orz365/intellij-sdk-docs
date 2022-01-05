[//]: # (title: Building Plugins with Gradle)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

[gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin) Gradle插件是构建IntelliJ平台插件的推荐解决方案。
这个插件会照顾你插件项目的依赖关系 - 基本IDE和其他插件依赖。
它提供了任务来运行带有插件的IDE，并打包和发布插件到 [JetBrains 插件仓库](https://plugins.jetbrains.com).
确保插件不受 [API changes](api_changes_list.md), 可能发生在平台的主要发行之间, 你可以通过其他ide和版本快速验证你的插件。

 >  [IntelliJ 平台插件模板](github_template.md) 使创建和维护你的IDE插件变得更容易, 已经集成了Gradle插件，并且使用GitHub Actions覆盖了CI。
 >
 {type="tip"}

 >  如果一个新的插件将是基于scala的, 专用的SBT插件 [sbt-idea-plugin](https://github.com/JetBrains/sbt-idea-plugin) 是可用的。
 >
 {type="note"}


 > Please make sure to always upgrade `gradle-intellij-plugin` to the latest version [![GitHub Release](https://img.shields.io/github/release/jetbrains/gradle-intellij-plugin.svg?style=flat-square)](https://github.com/jetbrains/gradle-intellij-plugin/releases)
 >
 > See [What's New & Upgrade Instructions](https://lp.jetbrains.com/gradle-intellij-plugin) for upgrading from pre-1.0 versions.
 >
 {type="note"}

Below are a series of guides to developing and deploying Gradle-based IntelliJ Platform Plugins:

* [Getting Started with Gradle](gradle_prerequisites.md)
* [Configuring Gradle Projects](gradle_guide.md)
* [Publishing Plugins with Gradle](deployment.md)
