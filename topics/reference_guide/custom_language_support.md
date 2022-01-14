[//]: # (title: Custom Language Support)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

IntelliJ Platform是一个强大的平台，可以构建针对*任何*语言的开发工具。
IDE的大部分特性由独立于语言的部分(由平台提供)和特定于语言的部分组成。
支持一种新语言的特定特性只需要少量的努力:
插件必须只实现特定于语言的部分。

## 引言

文档的这一部分解释了*Language API*的主要概念，并指导您完成开发自定义语言插件通常需要的一系列步骤。
您可以从*Language API*类的JavaDoc注释和Properties语言支持源代码中获得有关*Language API*的额外信息, 是 [IntelliJ IDEA Community Edition](https://github.com/JetBrains/intellij-community) 源代码的一部分.

## 教程

如果您喜欢完整的示例而不是本节提供的详细描述，请查看关于如何为 _Simple language_ 创建自定义语言支持的循序渐进的教程:
[自定义语言支持教程](custom_language_support_tutorial.md).
教程的相应步骤链接在本参考资料每页的“**示例**”部分下。

 [How We Built Comma, the Raku IDE, on the IntelliJ Platform](https://blog.jetbrains.com/platform/2020/01/webinar-recording-how-we-built-comma-the-raku-ide-on-the-intellij-platform/) 也提供了一个很好的介绍。

<video href="zDP9uUMYrvs" title="How We Built Comma, the Raku IDE, on the IntelliJ Platform" width="300"/>

## 主题

### 初始设定

* [注册文件类型](custom_language_support/registering_file_type.md)
* [实现词法分析程序](custom_language_support/implementing_lexer.md)
* [实现解析器和psi](custom_language_support/implementing_parser_and_psi.md)
* [语法高亮和错误高亮](custom_language_support/syntax_highlighting_and_error_highlighting.md)

### Resolving and Completion

* [引用和解析](references_and_resolve.md)
* [符号](symbols.md)
* [声明和引用](declarations_and_references.md)
* [导航](navigation.md)
* [代码自动完成](code_completion.md)

### 重构

* [查找引用](find_usages.md)
* [重命名重构](rename_refactoring.md)
* [安全删除重构](safe_delete_refactoring.md)

### 编辑器和IDE特性

* [代码格式化](code_formatting.md)
* [代码检查和意图](code_inspections_and_intentions.md)
* [结构视图](structure_view.md)
* [包裹代码](surround_with.md)
* [](go_to_class_and_go_to_symbol.md)
* [](documentation.md)
* [](additional_minor_features.md)
