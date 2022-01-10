[//]: # (title: Custom Language Support Tutorial)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

在本教程中，我们将添加对 [.properties](https://en.wikipedia.org/wiki/.properties) 语言和它在Java中的用法的支持。

> IntelliJ平台对自定义语言的支持可参考 [自定义语言支持](custom_language_support.md) 部分。
> 相应的部分链接在本教程每个页面顶部的**Reference**下。
>
 {type="tip"}


> 本教程中使用的完整且完全工作的示例插件是  [`simple_language_plugin`](https://github.com/JetBrains/intellij-sdk-code-samples/tree/main/simple_language_plugin)
>
 {type="note"}

这是一个循序渐进的教程，需要按照以下顺序完成每个步骤:

*  [准备工作](prerequisites.md)
*  [语言和文件类型](language_and_filetype.md)
*  [语法和解析器](grammar_and_parser.md)
*  [词法和解析器定义](lexer_and_parser_definition.md)
*  [高亮显示和颜色设置页面](syntax_highlighter_and_color_settings_page.md)
*  [PSI辅助工具](psi_helper_and_utilities.md)
*  [注解器](annotator.md)
*  [行标记提供器](line_marker_provider.md)
*  [自动补全贡献者](completion_contributor.md)
*  [引用贡献者](reference_contributor.md)
*  [查找引用提供器](find_usages_provider.md)
*  [折叠构建器](folding_builder.md)
*  [导航特性](go_to_symbol_contributor.md)
*  [结构视图工场](structure_view_factory.md)
*  [格式化程序](formatter.md)
*  [代码风格设置](code_style_settings.md)
*  [代码注释](commenter.md)
*  [快速修复](quick_fix.md)
