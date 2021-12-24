[//]: # (title: The IntelliJ Platform)

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

IntelliJ平台本身并不是一个产品，而是为构建IDE提供一个平台。它被用作推动JetBrains产品，例如：[IntelliJ IDEA](https://www.jetbrains.com/idea/) 。
它是开源的，可以被第三方构建IDE使用，例如谷歌的 [Android Studio](https://developer.android.com/studio/index.html) 。

IntelliJ平台提供所有IDE需要提供的语言工具支持。
它是组件驱动、跨平台的基于JVM应用程序，带有高级用户界面工具包，可用来创建工具窗口，树视图和列表视图（支持快速搜索），还有弹出菜单和对话框。


IntelliJ平台拥有完整的富文本编辑器实现，包括语法高亮，代码折叠，代码自动完，还有图片编辑器等等。

而且，它提供了开放的api接口来构建标准的IDE功能，如项目模型和构建系统。
它还为丰富的调试体验提供了基础设施，包括与语言无关的高级断点支持、调用堆栈、监视窗口和表达式求值。

但是IntelliJ平台的真正力量来自于程序结构接口(PSI)。
它是一组用于解析文件、构建丰富的代码语法和语义模型的功能，并从这些数据构建索引。
PSI提供了很多功能，从快速导航到文件、类型和符号，到代码完成窗口的内容，以及查找使用、代码检查和代码重写，以快速修复或重构，以及许多其他特性。

IntelliJ平台包括许多语言的解析器和PSI模型，它的可扩展特性意味着它可以添加对其他语言的支持。

## 插件

构建在IntelliJ平台上的产品是可扩展的应用程序，平台负责创建组件和将依赖注入到类中。
IntelliJ平台支持插件，JetBrains提供的[插件仓库](https://plugins.jetbrains.com) 用来分发支持一个或多个产品的插件。

插件可以以多种方式扩展平台，从添加一个简单的菜单项到添加对完整语言、构建系统和调试器的支持。
IntelliJ平台中的许多现有功能都是作为插件编写的，可以根据最终产品的需要来包含或排除这些插件。
详情请查看[快速开始](basics.md)

IntelliJ平台是一个JVM应用程序，大部分是使用Java和[Kotlin](https://kotlinlang.org)语言编写的。
您应该熟悉这些语言、用它们编写的大型库、相关工具，以及为基于IntelliJ平台的产品编写插件的大型开源项目。
目前，在非jvm语言中扩展IntelliJ平台是不可能的。

## 开源

The IntelliJ Platform is Open Source, under the [Apache License](upsource:///LICENSE.txt), and [hosted on GitHub](https://github.com/JetBrains/intellij-community).

While this guide refers to the IntelliJ Platform as a separate entity, there is no "IntelliJ Platform" GitHub repository.
Instead, the platform is considered to be an almost complete overlap with the IntelliJ IDEA Community Edition, which is a free and Open Source version of IntelliJ IDEA Ultimate (the GitHub repository linked above is the [JetBrains/intellij-community](https://github.com/JetBrains/intellij-community) repository).
Please note: starting with 2021.1 release, some plugins bundled with IntelliJ IDEA Community Edition are not open-source.

The version of the IntelliJ Platform is defined by the version of the corresponding release of IntelliJ IDEA Community Edition.
For example, to build a plugin against IntelliJ IDEA (2019.1.1), build #191.6707.61 means specifying the same build number tag to get the correct Intellij Platform files from the `intellij-community` repository.
See the [Build Number Ranges](build_number_ranges.md) page for more information about build numbers corresponding to version numbering.

Typically, an IDE that is based on the IntelliJ Platform will include the `intellij-community` repository as a Git submodule and provide configuration to describe which plugins from the `intellij-community`, and which custom plugins will make up the product.
This is how the IDEA Ultimate team works, and they contribute code to both the custom plugins and the IntelliJ Platform itself.

## IDEs Based on the IntelliJ Platform
The IntelliJ Platform underlies many JetBrains IDEs.
IntelliJ IDEA Ultimate is a superset of the IntelliJ IDEA Community Edition but includes closed source plugins ([see this feature comparison](https://www.jetbrains.com/idea/features/editions_comparison_matrix.html)).
Similarly, other products such as WebStorm and DataGrip are based on the IntelliJ IDEA Community Edition, but with a different set of plugins included and excluding other default plugins.
This allows plugins to target multiple products, as each product will include base functionality and a selection of plugins from the IntelliJ IDEA Community Edition repository.

 >  Qualifying Open Source projects can [apply for free licenses](https://www.jetbrains.com/community/opensource/) of JetBrains products.
 >
 {type="tip"}

The following IDEs are based on the IntelliJ Platform:
* JetBrains IDEs
  * [AppCode](https://www.jetbrains.com/objc/)
  * [CLion](https://www.jetbrains.com/clion/)
  * [DataGrip](https://www.jetbrains.com/datagrip/)
  * [GoLand](https://www.jetbrains.com/go/)
  * [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  * [MPS](https://www.jetbrains.com/mps/)
  * [PhpStorm](https://www.jetbrains.com/phpstorm/)
  * [PyCharm](https://www.jetbrains.com/pycharm/)
  * [Rider](#rider)
  * [RubyMine](https://www.jetbrains.com/ruby/)
  * [WebStorm](https://www.jetbrains.com/webstorm/)
* [Android Studio](https://developer.android.com/studio/index.html) IDE from Google.
* [Comma](https://commaide.com/) IDE for Raku (formerly known as Perl 6)
* [CUBA Studio](https://www.cuba-platform.com/)

#### Rider
JetBrains [Rider](https://www.jetbrains.com/rider/) uses the IntelliJ Platform differently than other IntelliJ based IDEs.
It uses the IntelliJ Platform to provide the user interface for a C# and .NET IDE, with the standard IntelliJ editors, tool windows, debugging experience, etc.
It also integrates into the standard Find Usages and Search Everywhere UI and uses code completion, syntax highlighting, and so on.

However, Rider doesn't create a full [PSI](psi.md) (syntactic and semantic) model for C# files.
Instead, it reuses [ReSharper](https://www.jetbrains.com/resharper/) to provide language functionality.
All of the C# PSI model, inspections, code rewritings, such as quick fixes and refactorings are run out of the process, in a command-line version of ReSharper.
This means that creating a plugin for Rider involves two parts - a plugin that lives in the IntelliJ "front end" to show user interface, and a plugin that lives in the ReSharper "back end" to analyze and work with the C# PSI.

Fortunately, many plugins can simply work with the ReSharper backend.
The Rider takes care of displaying the results of inspections and code completion, and many plugins can be written that don't require an IntelliJ UI component.
More details can be found in the *Part VIII - Product Specific* section.
