[//]: # (title: Getting Started with Gradle)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

Gradle是创建IntelliJ平台插件的首选解决方案。
IntelliJ IDEA Ultimate和Community版本捆绑了必要的插件来支持基于gradle的开发。
这些IntelliJ IDEA插件是 _Gradle_ 和 _Plugin DevKit_，它们是默认启用的。
验证这些插件是否安装和启用, 请参阅有关的帮助部分 [管理插件](https://www.jetbrains.com/help/idea/managing-plugins.html).

 >  [IntelliJ平台插件模板](https://github.com/JetBrains/intellij-platform-plugin-template) 让创建和维护你的IDE插件变得更容易，因为Gradle插件已经集成了，CI也覆盖了GitHub Actions。
 >
 {type="tip"}

 >  在Gradle构建脚本中添加额外的库时，始终使用HTTPS协议。
 >
 {type="warning"}

## 用新项目向导创建一个基于gradle的IntelliJ平台插件
使用 [New Project Wizard](https://www.jetbrains.com/help/idea/gradle.html#project_create_gradle) 创建新的基于gradle的IntelliJ平台插件项目。
向导根据一些模板输入创建所有必要的项目文件。

在创建一个新的Gradle项目之前，请先熟悉帮助主题 [创建一个新的Gradle项目](https://www.jetbrains.com/help/idea/getting-started-with-gradle.html#create_gradle_project),
这是一个在IntelliJ IDEA中创建通用Gradle项目的教程。
本页面重点介绍了创建基于gradle的IntelliJ Platform插件项目的步骤。
此外，*Youtube* 视频 [Working with Gradle in IntelliJ IDEA](https://www.youtube.com/watch?v=6V6G3RyxEMk) 提供了一个全面的介绍。

启动 [New Project Wizard](https://www.jetbrains.com/help/idea/gradle.html#project_create_gradle).
它通过两个屏幕指导您完成Gradle项目创建过程。

### 新建项目配置界面
在第一个界面中，配置了项目类型:
* 从左侧的项目类型窗格中，选择<control>Gradle</control>。
* 指定基于**Java 8** JDK的<control>Project SDK</control>。
  这个SDK将是用于运行Gradle的默认JRE，和JDK版本用来编译插件的Java源码。

 > 当目标为2020.3或更高版本时，现在需要使用Java 11, 请看 [blog post](https://blog.jetbrains.com/platform/2020/09/intellij-project-migrates-to-java-11/)
 >
 {type="note"}

* 在 <control>Additional Libraries and Frameworks</control> 面板, 选择 <control>Java</control> and <control>IntelliJ Platform Plugin</control>.
  这些设置将用于本教程的其余部分。

可选:
* 在插件中包含对Kotlin语言的支持, 选中 the _Kotlin/JVM_  (下面用绿色圈出).
  这个选项可以选择有或没有 the <control>Java</control> 语言.
  参看 [Kotlin for Plugin Developers](kotlin.md) 获取更多信息。
* 创建 <path>build.gradle</path> 文件作为Kotlin构建脚本 (<path>build.gradle.kts</path>) 而不是Groovy, 选中 _Kotlin DSL build script_  (下面用洋红色圈出).

然后点击 _Next_:

![在项目创建向导中选择Gradle](../../../images/tutorials/build_system/img/step1_new_gradle_project.png){width="800"}

### 项目命名/Artifact Coordinates 界面
点开 <control>Artifact Coordinates</control> 部分并指定 [GroupId, ArtifactId, and Version](https://www.jetbrains.com/help/idea/gradle.html#project_create_gradle) 使用 [Maven 命名](https://maven.apache.org/guides/mini/guide-naming-conventions.html) 惯例.
* <control>GroupId</control> 通常是一个Java包名, 在<path>build.gradle</path> 文件中它是用作 Gradle 属性 `project.group` 的值。
  举例, 输入 `com.your.company`.
* <control>ArtifactId</control> 是项目JAR文件的默认名称 (不包括版本号).
  在项目的 <path>settings.gradle</path> 文件中，它是用作 Gradle 属性 `rootProject.name` 的值  。
  举例，输入 `my_gradle_plugin`.
* <control>Version</control> 在 <path>build.gradle</path> 文件中，是用作 Gradle 属性 `project.version` 的值 .
  举例，输入 `1.0`.

The <control>Name</control> 字段自动与指定的 <control>ArtifactId</control> 值同步.

指定新项目的路径 <control>Location</control> 并点击 <control>Finish</control> 按钮生成新的项目。

### Wizard-Generated Gradle IntelliJ 平台插件的组件
For the [example](#creating-a-gradle-based-intellij-platform-plugin-with-new-project-wizard) `my_gradle_plugin`, the New Project Wizard creates the following directory content:

```text
my_gradle_plugin
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   └── resources
    │       └── META-INF
    │           └── plugin.xml
    └── test
        ├── java
        └── resources
```

* IntelliJ 平台默认 <path>build.gradle</path> 文件 (查看下一章节).
* The Gradle Wrapper 文件, 特别是 <path>gradle-wrapper.properties</path> 文件, 用来指定 Gradle 版本.
  如果有需要, IntelliJ IDEA Gradle 插件会下载这个文件中对应版本的Gradle。
* The <path>settings.gradle</path> 文件, 包括 `rootProject.name` 的定义。
*  <path>META-INF</path> 目录在默认的 `main` 目录下， [SourceSet](https://docs.gradle.org/current/userguide/java_plugin.html#sec:java_project_layout) 包含 [configuration file](plugin_configuration_file.md) 插件。

 > 注意: 生成的 <path>build.gradle</path> 文件的格式如下, 因为 IntelliJ IDEA 生成的模板与 gradle-intellij-plugin 1.0 release 不兼容。
 >  [Upgrade Instructions](https://lp.jetbrains.com/gradle-intellij-plugin/) 查看更多。
 >
 {type="warning"}

生成的 `my_gradle_plugin` 项目 <path>build.gradle</path> 文件:

```groovy
  plugins {
      id 'java'
      id 'org.jetbrains.intellij' version '1.3.0'
  }

  group 'com.your.company'
  version '1.0'
  sourceCompatibility = 1.8

  repositories {
      mavenCentral()
  }
  dependencies {
      testImplementation group: 'junit', name: 'junit', version: '4.13.2'
  }

  // See https://github.com/JetBrains/gradle-intellij-plugin/
  intellij {
      version = '2020.1.3'
  }
  patchPluginXml {
      changeNotes = """
        Add change notes here.<br/>
        <em>most HTML tags may be used</em>"""
  }
```

* Gradle显式声明了两个插件:
  *  [Gradle Java](https://docs.gradle.org/current/userguide/java_plugin.html) 插件.
  *  [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin/).
*  <control>GroupId</control> 来自向导 [Project Naming/Artifact Coordinates Screen](#project-namingartifact-coordinates-screen) 是 `project.group` 的值.
*  <control>Version</control> 来自向导 [Project Naming/Artifact Coordinates Screen](#project-namingartifact-coordinates-screen) 是 `project.version` 的值.
*  `sourceCompatibility` 行被注入，以强制使用Java 8 JDK来编译Java源代码.
* 文件中唯一的注释是指向 [README.md](https://github.com/JetBrains/gradle-intellij-plugin/blob/master/README.md) 对于 gradle-intellij-plugin 插件, 是它的配置DSL的参考.
* 设置DSL属性 `intellij.version` 的值指定用于构建插件的IntelliJ平台的版本号。
  它默认使用IntelliJ IDEA的版本，该版本用于运行新项目向导。
* 打补丁，DSL属性值 `patchPluginXml.changeNotes` 设置占位文本。

#### 插件Gradle属性和插件配置文件元素
Gradle 属性 `rootProject.name` 和 `project.group` 将不会匹配各自的 [plugin configuration file](plugin_configuration_file.md) <path>plugin.xml</path> 元素 `<name>` 和 `<id>`.
没有IntelliJ平台相关的理由，因为它们服务于不同的功能。

 `<name>` 元素 (用作插件的显示名称) 和 `rootProject.name` 一样, 但它可以更有解释性。

 `<id>` 的值必须是所有插件的唯一标识符， 通常是指定的 <control>GroupId</control> 和 <control>ArtifactId</control>。
 请注意，更改已发布插件的 `<id>` 而不丢失现有安装的自动更新是不可能的。

## 在现有的基于devkit的IntelliJ平台插件中添加Gradle支持

 > See [Revamping Plugins #3 – Migrating from DevKit to the Gradle build system](https://blog.jetbrains.com/platform/2021/12/migrating-from-devkit-to-the-gradle-build-system/) blog post for a step-by-step walk-through.
 >
 {type="tip"}

将一个 [基于devkit](using_dev_kit.md) 的插件项目转换为一个基于gradle的插件项目，可以使用New project Wizard来封装现有的基于devkit的项目创建一个基于gradle的项目:
* 如果需要，确保包含基于devkit的IntelliJ Platform插件项目的目录可以完全恢复。
* 删除基于devkit的项目的所有工件:
  * <path>.idea</path> 目录
  * <path>[modulename].iml</path> 文件
  * <path>out</path> 目录
* 排列项目中已有的源文件，按照Gradle [SourceSet](https://docs.gradle.org/current/userguide/java_plugin.html#sec:java_project_layout) 的格式。
* 使用 New Project Wizard 创建一个Gradle项目 [new Gradle project](#creating-a-gradle-based-intellij-platform-plugin-with-new-project-wizard) 。
* 在这个步骤 [Project Naming/Artifact Coordinates Screen](#project-namingartifact-coordinates-screen) 设置下面的值:
  * <control>GroupId</control> 初始化为已存在的包名。
  * <control>ArtifactId</control> 和已存在插件设置相同值。
  * <control>Version</control> 和已存在插件设置相同值。
  * <control>Name</control> 和已存在插件设置相同值。
    (应该是从 <control>ArtifactId</control> 中预填写。)
  * 设置 <control>Location</control> 的值为已经存在插件的目录地址。
* 点击 <control>Finish</control> 按钮创建一个基于Gralde的项目。
* [Add more modules](https://www.jetbrains.com/help/idea/gradle.html#gradle_add_module) 使用 Gradle [Source Sets](https://www.jetbrains.com/help/idea/gradle.html#gradle_source_sets) 根据需要.

## 运行一个简单的基于gradle的IntelliJ平台插件
Gradle项目是从IDE的Gradle Tool窗口运行的。

### 向项目中添加代码
在运行项目之前 [`my_gradle_project`](#components-of-a-wizard-generated-gradle-intellij-platform-plugin), 可以添加一些代码来提供简单的功能。
参见 [Creating Actions](working_with_custom_actions.md) 教程，逐步添加一个菜单行为。

### 执行插件
打开Gradle工具窗口，搜索 <control>runIde</control> 任务:
* 如果任务不存在，点击窗口上方的 [Refresh](https://www.jetbrains.com/help/idea/jetgradle-tool-window.html#1eeec055) 按钮.
* 或是 [Create a new Gradle Run Configuration](https://www.jetbrains.com/help/idea/create-run-debug-configuration-gradle-tasks.html).

![Gradle Tool Window](../../../images/tutorials/build_system/img/gradle_tasks_in_tool_window.png){width="398"}

双击 <control>runIde</control> 任务来执行。
参阅 IntelliJ IDEA 帮助文档来查看更多有关信息 [Working with Gradle tasks](https://www.jetbrains.com/help/idea/gradle.html#96bba6c3).

调试插件， in a _standalone_ IDE 实例,参见 [How to Debug Your Own IntelliJ IDEA Instance](https://medium.com/agorapulse-stories/how-to-debug-your-own-intellij-idea-instance-7d7df185a48d) 博客。
