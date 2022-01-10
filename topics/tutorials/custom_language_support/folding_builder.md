[//]: # (title: 12. Folding Builder)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

折叠构建器标识代码中的折叠区域。
在本教程的这一步中，折叠生成器用于识别折叠区域，并将这些区域替换为特定的文本。
与通常使用折叠构建器将类、方法或注释折叠到更少的行不同，折叠构建器用它们对应的值替换了 Simple Language 键值。

## 定义折叠构建器
`SimpleFoldingBuilder` 默认用属性的值替换属性的用法。
开始通过子类化 [`FoldingBuilderEx`](upsource:///platform/core-api/src/com/intellij/lang/folding/FoldingBuilderEx.java)

注意 `SimpleFoldingBuilder` 同样实现了 [`DumbAware`](upsource:///platform/core-api/src/com/intellij/openapi/project/DumbAware.java)，
这意味着当索引在后台更新时，类可以在哑模式(dumb mode)下运行。

 >  折叠构建器必须实现 [`DumbAware`](upsource:///platform/core-api/src/com/intellij/openapi/project/DumbAware.java) 在本教程中运行并通过测试。
 >
 {type="note"}

`buildFoldRegions()` 方法从 `root` 开始向下搜索 PSI 树，查找包含  [simple 前缀](annotator.md#define-an-annotator) 的所有字面量表达式。

这个字符串的其余部分应该包含一个Simple Language键,
所以文本范围被存储为 [`FoldingDescriptor`](upsource:///platform/core-api/src/com/intellij/lang/folding/FoldingDescriptor.java).

`getPlaceholderText()` 方法检索与所提供的(ASTNode)相关联的键对应的Simple Language值。
当代码被折叠时，IntelliJ平台使用这个值来替换key。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFoldingBuilder.java"}

## Register the Folding Builder
`SimpleFoldingBuilder`的实现是通过 `com.intellij.lang.foldingBuilder` 扩展点在插件配置文件中注册到IntelliJ平台的。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.foldingBuilder language="JAVA"
            implementationClass="org.intellij.sdk.language.SimpleFoldingBuilder"/>
  </extensions>
```

## 运行项目
使用Graldle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行项目。

现在，当在编辑器中打开Java文件时，它会显示属性的值，而不是键。
这是因为 `SimpleFoldingBuilder.isCollapsedByDefault()` 总是返回 `true`.
试着使用 **Code \| Folding \| Expand All** 来显示关键字，而不是关键字的值。

![Folding](../../../images/tutorials/custom_language_support/img/folding.png)
