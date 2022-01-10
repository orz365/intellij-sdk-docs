[//]: # (title: 15. Formatter)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

IntelliJ平台包含一个强大的框架，用于实现自定义语言的格式化。
格式化程序支持根据代码样式设置自动重新格式化代码。
格式化程序控制空格、缩进、换行和对齐。

**引用**: [code_formatting](code_formatting.md)

## 定义一个块 Define a Block
格式化模型将文件的格式化结构表示为 [`Block`](upsource:///platform/code-style-api/src/com/intellij/formatting/Block.java) 对象树, 与相关的缩进，换行，对齐和间距设置。
目标是用这样的块覆盖每个PSI元素。
因为每个块构建它的子块，它可以生成额外的块或跳过任何PSI元素。
基于 [`AbstractBlock`](upsource:///platform/code-style-impl/src/com/intellij/psi/formatter/common/AbstractBlock.java) 定义 `SimpleBlock`。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleBlock.java"}

## 定义一个格式化模型生成器
定义一个格式化程序，除去属性分隔符周围的单个空格之外的额外空格。
例如， 格式化 "foo  = &nbsp;&nbsp;&nbsp;&nbsp;bar" 变成 "foo = bar".

创建 `SimpleFormattingModelBuilder` 通过子类化 [`FormattingModelBuilder`](upsource:///platform/code-style-api/src/com/intellij/formatting/FormattingModelBuilder.java).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFormattingModelBuilder.java"}

## 格式化程序注册
`SimpleFormattingModelBuilder` 实现使用 `com.intellij.lang.formatter` 扩展点在插件配置文件中注册给IntelliJ平台。
```xml
 <extensions defaultExtensionNs="com.intellij">
    <lang.formatter language="Simple"
            implementationClass="org.intellij.sdk.language.SimpleFormattingModelBuilder"/>
  </extensions>
```

## 运行该项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行该项目。

打开示例 Simple Language [properties 文件 ](lexer_and_parser_definition.md#run-the-project) 使用IDE开发实例。
在 `language` 和 `English` 之间的 `=` 两边添加额外的空格分隔符
重新格式化代码，通过选择 **Code \| Show Reformat File Dialog** and choose **Run**.

![Formatter](../../../images/tutorials/custom_language_support/img/formatter.png)
