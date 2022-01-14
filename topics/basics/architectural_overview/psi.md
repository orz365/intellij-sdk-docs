[//]: # (title: Program Structure Interface \(PSI\))

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

The Program Structure Interface, commonly referred to as just PSI, is the layer in the IntelliJ Platform responsible for parsing files and creating the syntactic and semantic code model that powers so many of the platform's features.

程序结构接口(Program Structure Interface，简称PSI)是IntelliJ平台中的一层，负责解析文件，并创建语法和语义代码模型，从而为平台的许多功能提供支持。

* [PSI Files](psi_files.md)
* [File View Providers](file_view_providers.md)
* [PSI Elements](psi_elements.md)

 >  A useful tool for debugging the PSI implementation is the [PsiViewer plugin](https://plugins.jetbrains.com/plugin/227-psiviewer).
> It can show you the PSI tree structure, the properties of every PSI element, and highlight its text range.
