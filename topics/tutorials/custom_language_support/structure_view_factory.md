[//]: # (title: 14. Structure View Factory)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

可以为特定的文件类型定制结构视图。
创建结构视图工厂允许在 _结构_ 工具窗口中显示任何文件的结构，以便在当前编辑器的项目之间轻松导航。

**Reference**: [structure_view](structure_view.md)

## 定义一个结构视图工厂
The structure view factory implements [`PsiStructureViewFactory`](upsource:///platform/editor-ui-api/src/com/intellij/lang/PsiStructureViewFactory.java).
The `getStructureViewBuilder()` implementation reuses the IntelliJ Platform class [`TreeBasedStructureViewBuilder`](upsource:///platform/editor-ui-api/src/com/intellij/ide/structureView/TreeBasedStructureViewBuilder.java).
At this point the project will not compile until `SimpleStructureViewModel` is [implemented below](#define-a-structure-view-model).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleStructureViewFactory.java"}

## 定义一个结构视图模型
The `SimpleStructureViewModel` is created by implementing [`StructureViewModel`](upsource:///platform/editor-ui-api/src/com/intellij/ide/structureView/StructureViewModel.java), which defines the model for data displayed in the standard structure view.
It also extends [`StructureViewModelBase`](upsource:///platform/editor-ui-api/src/com/intellij/ide/structureView/StructureViewModelBase.java), an implementation that links the model to a text editor.

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleStructureViewModel.java"}

## 定义一个结构视图元素
The `SimpleStructureViewElement` implements [`StructureViewTreeElement`](upsource:///platform/editor-ui-api/src/com/intellij/ide/structureView/StructureViewTreeElement.java) and [`SortableTreeElement`](upsource:///platform/editor-ui-api/src/com/intellij/ide/util/treeView/smartTree/SortableTreeElement.java).
The `StructureViewTreeElement` represents an element in the Structure View tree model.
The `SortableTreeElement` represents an item in a smart tree that allows using text other than the presentable text as a key for alphabetic sorting.

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleStructureViewElement.java"}

## 注册结构视图工厂
The `SimpleStructureViewFactory` implementation is registered with the IntelliJ Platform in the plugin configuration file using the `com.intellij.lang.psiStructureViewFactory` extension point.

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.psiStructureViewFactory language="Simple"
            implementationClass="org.intellij.sdk.language.SimpleStructureViewFactory"/>
  </extensions>
```

## 运行该项目
Run the project by using the Gradle [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin).

Open the `test.simple` file and choose **View \| Tool Windows \| Structure**.
The IDE now supports a structure view of the Simple Language:

![Structure View](../../../images/tutorials/custom_language_support/img/structure_view.png)
