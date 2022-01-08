[//]: # (title: 7. Annotator)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

 `注解器Annotator` 帮助基于特定规则突出显示和注解任何代码。
 本节将在Java代码上下文中添加注释功能以支持简单语言。

**引用**: [Annotator](syntax_highlighting_and_error_highlighting.md#annotator)

## 所需的项目配置更改 Required Project Configuration Changes
本教程这一步中定义的类依赖于 `com.intellij.psi.PsiLiteralExpression` (Java代码中字符串字面值的PSI表示) 运行时.
使用 `PsiLiteralExpression` [introduces a dependency](plugin_compatibility.md#modules-specific-to-functionality) 在 `com.intellij.java`.

从2019.2版开始， 依赖于Java插件 [must be declared explicitly](https://blog.jetbrains.com/platform/2019/06/java-functionality-extracted-as-a-plugin/).
首先，在Gradle构建文件中添加一个对Java插件的依赖:

<tabs>
<tab title="build.gradle">

```groovy
intellij {
  plugins = ['com.intellij.java']
}
```

</tab>

<tab title="build.gradle.kts">

```kotlin
intellij {
  plugins.set(listOf("com.intellij.java"))
}
```

</tab>
</tabs>

然后，在 <path>plugin.xml</path> 中声明依赖项 (use code insight)

```xml
  <depends>com.intellij.java</depends>
```

## 定义注解器 Define an Annotator
 `SimpleAnnotator` 子类 [`Annotator`](upsource:///platform/analysis-api/src/com/intellij/lang/annotation/Annotator.java).
 考虑一个以“simple:”开头的字面量字符串作为Simple Language键的前缀。
 它不是 Simple Language 的一部分，但它是一种有用的约定，用于检测在其他语言(如Java)中作为字符串字面值嵌入的简单语言键。
 注释 `simple:key` 字面表达式，并区分格式良好的属性和未解析的属性。

 > 从2020.2开始使用新的 `AnnotationHolder` 语法，它使用构建器格式。
 >
 {type="note"}

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleAnnotator.java"}

[comment]: <> ( > If the above code is copied at this stage of the tutorial, then remove the line below the comment "** Tutorial step 18.3 …")
 > 如果复制了当前教程的上述代码，然后删除注释行中带有 "** Tutorial step 18.3 …" 的代码行。
 >
 > 这一行中的 `快速修复` 类直到本教程的后面才定义。
 >
 {type="tip"}

## 注册注解器 Register the Annotator
在插件配置文件中，使用 `com.intellij.annotator` 扩展点，
使用 IntelliJ 平台  注册 Simple Language 注解器类：

```xml
  <extensions defaultExtensionNs="com.intellij">
    <annotator language="JAVA" implementationClass="org.intellij.sdk.language.SimpleAnnotator"/>
  </extensions>
```

## 运行项目 Run the Project

使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行插件。

As a test, define the following Java file containing a Simple Language `prefix:value` pair:
作为测试，定义下面的Java文件包含一个 Simple Language 的 `prefix:value` 键值对：

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("simple:website");
    }
}
```

使用IDE开发实例打开这个java文件，运行 `simple_language_plugin` 来检查IDE是否包含属性：
Open this Java file in an IDE Development Instance running the `simple_language_plugin` to check if the IDE resolves a property:

![Annotator](annotator.png){width="800"}

If the property is an undefined name, the annotator flags the code with an error.

![Unresolved property](unresolved_property.png){width="800"}

Try changing the Simple Language [color settings](syntax_highlighter_and_color_settings_page.md#run-the-project) to differentiate the annotation from the default language color settings.
