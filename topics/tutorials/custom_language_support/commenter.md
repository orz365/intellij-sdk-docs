[//]: # (title: 17. Commenter)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

注释器使用户能够自动注释掉光标处的一行代码或所选代码。
 [`注释器`](upsource:///platform/core-api/src/com/intellij/lang/Commenter.java) 定义支持 **Code \| Comment with Line Comment** and **Code \| Comment with Block Comment** 行为.

## 定义注释器
Simple Language 注释器将行注释前缀定义为 `#`.

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleCommenter.java"}

## 注册注释器
`SimpleCommenter` 的实现使用 `com.intellij.lang.commenter` 扩展点在插件配置文件中注册给IntelliJ平台。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.commenter language="Simple" implementationClass="org.intellij.sdk.language.SimpleCommenter"/>
  </extensions>
```

## 运行该项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行该项目。

打开示例 Simple Language [properties 文件 ](lexer_and_parser_definition.md#run-the-project) 使用IDE开发实例。
将光标放在 `website` 行.
选择 **Code \| Comment with Line Comment**.
该行被转换为注释。
再次选择 **Code \| Comment with Line Comment** ，注释被转换回活动代码。

![Commenter](../../../images/tutorials/custom_language_support/img/commenter.png)
