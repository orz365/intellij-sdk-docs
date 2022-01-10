[//]: # (title: 9. Completion Contributor)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

自定义语言使用两种方法之一提供代码补全： Contributor 和 Reference-based (see [reference_contributor](reference_contributor.md)) completion.

**Reference**: [](code_completion.md)

## 定义自动补全
对于本教程， `simple_language_plugin` 为 Simple Language 属性文件中的值提供自定义补全。
通过子类 [`CompletionContributor`](upsource:///platform/analysis-api/src/com/intellij/codeInsight/completion/CompletionContributor.java) 创建一个贡献者。
这个基本的补全贡献者总是将“Hello”添加到补全变量结果集，无论上下文如何:

```java
package org.intellij.sdk.language;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import org.intellij.sdk.language.psi.SimpleTypes;
import org.jetbrains.annotations.NotNull;
public class SimpleCompletionContributor extends CompletionContributor {
  public SimpleCompletionContributor() {
    extend(CompletionType.BASIC, PlatformPatterns.psiElement(SimpleTypes.VALUE),
            new CompletionProvider<>() {
              public void addCompletions(@NotNull CompletionParameters parameters,
                                         @NotNull ProcessingContext context,
                                         @NotNull CompletionResultSet resultSet) {
                resultSet.addElement(LookupElementBuilder.create("Hello"));
              }
            }
    );
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleCompletionContributor.java"}

## 注册自动补全
`SimpleCompletionContributor` 是通过注册使用`com.intellij.completion.contributor`扩展点注册插件配置文件实现的。
```xml
  <extensions defaultExtensionNs="com.intellij">
    <completion.contributor language="Simple"
                            implementationClass="org.intellij.sdk.language.SimpleCompletionContributor"/>
  </extensions>
```

## 运行项目
使用Gralde任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行项目。

打开文件 [`test.simple`](lexer_and_parser_definition.md#run-the-project) 。
删除属性 "English" ，会调用 [Basic Code Completion](https://www.jetbrains.com/help/idea/auto-completing-code.html#invoke-basic-completion).
"Hello" 选项会显示出来：

![Completion](../../../images/tutorials/custom_language_support/img/completion.png)
