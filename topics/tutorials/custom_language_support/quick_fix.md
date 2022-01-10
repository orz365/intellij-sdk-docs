[//]: # (title: 18. Quick Fix)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

一个自定义语言的快速修复支持IntelliJ基于平台的IDE特性 [Intention Actions](https://www.jetbrains.com/help/idea/intention-actions.html#apply-intention-actions).
对于 Simple Language 来说，本教程添加了一个快速修复，帮助从使用中定义未解析的属性。

**引用**: [code_inspections_and_intentions](code_inspections_and_intentions.md)

## 更新元素工厂
 `SimpleElementFactory` 被更新，包括两个新方法，以支持用户为简单语言快速修复创建新属性的选择。
新的 `createCRLF()` 方法支持支持添加新属性之前，用新行来结束 [`test.simple`](lexer_and_parser_definition.md#run-the-project) 文件。
A new overload of `createProperty()` creates a new `key`-`value` pair for Simple Language.
 `createProperty()` 的新重载为 Simple Language 创建了一个新的 `key` -`value` 对。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/SimpleElementFactory.java"}

## 定义意图动作
`SimpleCreatePropertyQuickFix` 由用户在文件中创建的属性 - 在这种情况下，Java文件包括 `prefix:key` - 创建后导航到该属性。
在后台, `SimpleCreatePropertyQuickFix` 就是意图动作.
关于意图动作的更深入的例子，查看 [`conditional_operator_intention`](https://github.com/JetBrains/intellij-sdk-code-samples/tree/main/conditional_operator_intention).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleCreatePropertyQuickFix.java"}

## 更新注解器
当创建`badProperty`注释时，会调用`badProperty. registerfix()`方法。
这个方法调用注册了 `SimpleCreatePropertyQuickFix` 作为Intellij平台的意图动作来纠正这个问题。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleAnnotator.java"}

## 运行该项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 来运行该项目。
打开测试 [Java file](annotator.md#run-the-project).

去测试 `SimpleCreatePropertyQuickFix`, 改变 `simple:website` 为 `simple:website.url`.
关键字 `website.url` 通过 `SimpleAnnotator` 被高亮，作为一个错误的键值，显示如下。
选择 "Create Property".

![Quick Fix](../../../images/tutorials/custom_language_support/img/quick_fix.png){width="800"}

IDE打开 `test.simple` 文件并添加 `website.url` 作为新的键。
添加新的值 `jetbrains.com`  `website.url` 键的值.

![New Property](../../../images/tutorials/custom_language_support/img/new_property.png)

现在转换回Java文件；新的键作为有效被高亮。
