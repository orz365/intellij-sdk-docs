[//]: # (title: 8. Line Marker Provider)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

[comment]: <> (Line markers help annotate code with icons on the gutter.)
行标记帮助在窗口边栏处添加图标来帮助注释代码。
这些标记可以为相关代码提供导航目标。

## 定义行标记提供器
行标记提供程序在Java代码中注释Simple Language属性的用法，并提供到这些属性定义的导航。
可视标记是“编辑器”窗口边栏中的 Simple Language 图标。

Simple Language 标记提供子类[`RelatedItemLineMarkerProvider`](upsource:///platform/lang-api/src/com/intellij/codeInsight/daemon/RelatedItemLineMarkerProvider.java)。
例如，重写`collectNavigationMarkers()`方法来收集 Simple Language [key and separators](language_and_filetype.md#define-the-language) 的使用情况：

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleLineMarkerProvider.java"}

Extending from [`GutterIconDescriptor`](upsource:///platform/lang-api/src/com/intellij/codeInsight/daemon/GutterIconDescriptor.java) allows configuring gutter icons to be shown via <menupath>Settings/Preferences | Editor | General | Gutter Icons</menupath>.

## 实现行标记提供程序的最佳实践 Best Practices for Implementing Line Marker Providers
本节讨论有关实现标记提供程序的重要细节。

 `collectNavigationMarkers()` 方法应该：
* 只返回与传入方法的元素一致的行标记信息。
  例如，如果 `getLineMarkerInfo()` 使用与方法对应的元素调用，不会返回 _class_ 标记。
* 在PSI树的正确范围内返回适当元素的行标记信息。
  只返回叶元素，也就是，可能的最小元素。
  例如，不返回 [PsiMethod](upsource:///java/java-psi-api/src/com/intellij/psi/PsiMethod.java) 的方法标记。
相反，会返回包含方法名称的 [PsiIdentifier](upsource:///java/java-psi-api/src/com/intellij/psi/PsiIdentifier.java)。

![Line Marker Location](../../../images/tutorials/custom_language_support/img/line_marker_location.png){width="900"}

当 `LineMarkerProvider` 返回 `PsiElement` 的标记信息，它是PSI树中的较高节点时，会发生什么？
例如，如果 `MyWrongLineMarkerProvider()` 错误地返回 `PsiMethod` 而不是 `PsiIdentifier` 元素:

```java
public class MyWrongLineMarkerProvider implements LineMarkerProvider {
  public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {
    if (element instanceof PsiMethod) return new LineMarkerInfo(element, ...);
    return null;
  }
}
```

`MyWrongLineMarkerProvider()` 实现的结果与IntelliJ平台如何执行检查有关。
由于性能的原因, 检查， 特别是 [`LineMarkersPass`](upsource:///platform/lang-impl/src/com/intellij/codeInsight/daemon/impl/LineMarkersPass.java) 查询所有 [`LineMarkerProviders`](upsource:///platform/lang-api/src/com/intellij/codeInsight/daemon/LineMarkerProviders.java) 分两个阶段:
* 第一次传递的是编辑器窗口中可见的所有元素，
* 第二次传递是针对文件中的其他元素。

如果提供程序没有为这两个区域返回任何内容，则行标记将被清除。
不管怎样, if a method like `actionPerformed()` is not completely visible in the Editor window (as shown in the image above,) and `MyWrongLineMarkerProvider()` returns marker info for the `PsiMethod` instead of `PsiIdentifier`, then:
如果一个方法，像是 `actionPerformed()` 没有完全在编辑窗口（如上图所示）中可见，`MyWrongLineMarkerProvider()` 返回标记信息`PsiMethod` 替代 `PsiIdentifier`，然后：
* 第一次传递删除行标记信息，因为整个 `PsiMethod` 是不可见的。
* 第二轮尝试添加行标记，因为 `PsiMethod` 调用了 `MyWrongLineMarkerProvider()`。

结果，行标记图标会令人讨厌地闪烁。
为了解决这个问题，重写 `MyWrongLineMarkerProvider` 返回 `PsiIdentifier` 而不是 `PsiMethod` 的信息，如下所示:

```java
public class MyCorrectLineMarkerProvider implements LineMarkerProvider {
  public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {
    if (element instanceof PsiIdentifier && element.getParent() instanceof PsiMethod) return new LineMarkerInfo(element, ...);
    return null;
  }
}
```

## 注册行标记提供器 Register the Line Marker Provider
`SimpleLineMarkerProvider` 是使用IntelliJ平台插件配置文件的 `com.intellij.codeInsight.lineMarkerProvider` 扩展点来实现的。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <codeInsight.lineMarkerProvider language="JAVA"
            implementationClass="org.intellij.sdk.language.SimpleLineMarkerProvider"/>
  </extensions>
```

## 运行项目
使用Gralde任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行插件。

打开Java文件 [Test file](annotator.md#run-the-project).
现在图标会显示在第三行的边栏片。
用户可以单击图标导航到属性定义。

![Line Marker](../../../images/tutorials/custom_language_support/img/line_marker.png)
