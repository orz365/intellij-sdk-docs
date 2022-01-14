[//]: # (title: PSI Files)

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

PSI(程序结构接口)文件是将文件内容表示为特定编程语言中元素层次结构的结构的根。

[`PsiFile`](upsource:///platform/core-api/src/com/intellij/psi/PsiFile.java) 类是所有PSI文件的公共基类，而特定语言中的文件通常由其子类表示。
例如， the [`PsiJavaFile`](upsource:///java/java-psi-api/src/com/intellij/psi/PsiJavaFile.java) 类表示一个Java文件， 而 [`XmlFile`](upsource:///xml/xml-psi-api/src/com/intellij/psi/xml/XmlFile.java) 类则表示一个XML文件。

不像 `VirtualFile` 和 `Document`，它们有Application Scope (即使多个项目被打开, 每个文件都是同样的 `VirtualFile` 实例)，
PSI 有Project Scope：如果文件被多个项目同时打开，每个文件都表示不同的 `PsiFile` 实例。

## 怎么得到了个PSI文件？

* 从Action: `e.getData(CommonDataKeys.PSI_FILE)`.
* 从VirtualFile: `PsiManager.getInstance(project).findFile()`
* 从Document: `PsiDocumentManager.getInstance(project).getPsiFile()`
* 从文件中的一个元素: `PsiElement.getContainingFile()`
* 在项目中的任何位置查找具有特定名称的文件，使用 `FilenameIndex.getFilesByName(project, name, scope)`

## 我能用PSI文件做什么?

大多数有趣的修改操作都是在单个PSI元素的层面上执行的，而不是作为一个整体的文件。

要迭代文件中的元素，请使用

```java
  psiFile.accept(new PsiRecursiveElementWalkingVisitor() {
    // visitor implementation ...
  });
```

另请参阅 [Navigating the PSI](navigating_psi.md).

## PSI文件是从哪里来的?

因为PSI依赖于语言，PSI文件是使用 [`Language`](upsource:///platform/core-api/src/com/intellij/lang/Language.java) 实例创造的：

```java
  LanguageParserDefinitions.INSTANCE
        .forLanguage(MyLanguage.INSTANCE)
        .createFile(fileViewProvider);
```

与文档一样，当针对特定文件访问PSI时，也会按需创建PSI文件。

## PSI文件能保存多久？

像文档一样，PSI文件被相应的`“`VirtualFile`”`实例弱引用，如果没有被任何人引用，就可以被垃圾收集。

## 如何创建PSI文件?

[`PsiFileFactory`](upsource:///platform/core-api/src/com/intellij/psi/PsiFileFactory.java) `createFileFromText()` 方法创建具有指定内容的内存PSI文件。

要将PSI文件保存到磁盘，使用 [`PsiDirectory`](upsource:///platform/core-api/src/com/intellij/psi/PsiDirectory.java) `add()` 方法.

## 当PSI文件发生变化时，我如何得到通知?

`PsiManager.getInstance(project).addPsiTreeChangeListener()` 允许您接收关于项目PSI树的所有更改的通知。
或者， 注册 [`PsiTreeChangeListener`](upsource:///platform/core-api/src/com/intellij/psi/PsiTreeChangeListener.java) 在 `com.intellij.psi.treeChangeListener` 扩展点中。

 > 请看 [`PsiTreeChangeEvent`](upsource:///platform/core-api/src/com/intellij/psi/PsiTreeChangeEvent.java) Java文档 处理PSI事件时常见的问题。
  >
  {type="note"}

## 如何扩展PSI?

PSI可以通过自定义语言插件进行扩展，以支持其他语言。
有关开发自定义语言插件的详细信息，请看 [Custom Language Support](custom_language_support.md) 参考指南。

## PSI的工作规则是什么?

对PSI文件内容所做的任何更改都反映在文件中，所以所有的 [处理文档的规则](documents.md#what-are-the-rules-of-working-with-documents) (读/写 actions, commands, read-only status handling) 都是生效的。
