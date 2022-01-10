[//]: # (title: 10. Reference Contributor)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

引用功能是实现自定义语言支持中最重要的部分之一。
解析引用意味着能够从元素的使用过渡到元素的声明、补全、重命名重构、查找用法等。

 >  每一个 PSI 元素能够重命名或引用需要实现  [`PsiNamedElement`](upsource:///platform/core-api/src/com/intellij/psi/PsiNamedElement.java) 接口。
 >
 {type="note"}

**引用**: [引用和解析](references_and_resolve.md), [](psi_references.md)

## 定义一个命名元素类 Define a Named Element Class
下面的类展示了 Simple Language 是如何实现 `PsiNamedElement` 的。

 `SimpleNamedElement` 接口是  [`PsiNameIdentifierOwner`](upsource:///platform/core-api/src/com/intellij/psi/PsiNameIdentifierOwner.java) 子集。

```java
package org.intellij.sdk.language.psi;
import com.intellij.psi.PsiNameIdentifierOwner;
public interface SimpleNamedElement extends PsiNameIdentifierOwner {
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/SimpleNamedElement.java"}

 `SimpleNamedElementImpl` 类实现了 `SimpleNamedElement` 接口 并 继承 [`ASTWrapperPsiElement`](upsource:///platform/core-impl/src/com/intellij/extapi/psi/ASTWrapperPsiElement.java).

```java
package org.intellij.sdk.language.psi.impl;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.intellij.sdk.language.psi.SimpleNamedElement;
import org.jetbrains.annotations.NotNull;
public abstract class SimpleNamedElementImpl extends ASTWrapperPsiElement implements SimpleNamedElement {
  public SimpleNamedElementImpl(@NotNull ASTNode node) {
    super(node);
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/impl/SimpleNamedElementImpl.java"}

## 为生成的PSI元素定义辅助方法 Define Helper Methods for Generated PSI Elements
修改 `SimplePsiImplUtil` 以支持添加到 Simple Language PSI类的新方法。
注意，`SimpleElementFactory` 直到 [下一步](#define-an-element-factory) 之前还是未定义的， 所以现在还是错误的。

```java
public class SimplePsiImplUtil {

  // ...

  public static String getName(SimpleProperty element) {
      return getKey(element);
  }

  public static PsiElement setName(SimpleProperty element, String newName) {
      ASTNode keyNode = element.getNode().findChildByType(SimpleTypes.KEY);
      if (keyNode != null) {

          SimpleProperty property = SimpleElementFactory.createProperty(element.getProject(), newName);
          ASTNode newKeyNode = property.getFirstChild().getNode();
          element.getNode().replaceChild(keyNode, newKeyNode);
      }
      return element;
  }

  public static PsiElement getNameIdentifier(SimpleProperty element) {
      ASTNode keyNode = element.getNode().findChildByType(SimpleTypes.KEY);
      if (keyNode != null) {
          return keyNode.getPsi();
      } else {
          return null;
      }
  }

  // ...
}
```

## 定义一个元素工厂 Define an Element Factory
`SimpleElementFactory` 提供了一个创建 `SimpleFile` 的方法。

```java
package org.intellij.sdk.language.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.intellij.sdk.language.SimpleFileType;

public class SimpleElementFactory {
  public static SimpleProperty createProperty(Project project, String name) {
    final SimpleFile file = createFile(project, name);
    return (SimpleProperty) file.getFirstChild();
  }

  public static SimpleFile createFile(Project project, String text) {
    String name = "dummy.simple";
    return (SimpleFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, SimpleFileType.INSTANCE, text);
  }
}
```

## 更新语法并重新生成解析器 Update Grammar and Regenerate the Parser
现在对 `Simple.bnf` 语法文件进行相应的更改。通过下面的内容替换 `属性` 定义。
改完文件后，不要忘记重新生成解析程序。
右键点击 `Simple.bnf` 文件，选择 **Generate Parser Code** 。

```java
property ::= (KEY? SEPARATOR VALUE?) | KEY {
  mixin="org.intellij.sdk.language.psi.impl.SimpleNamedElementImpl"
  implements="org.intellij.sdk.language.psi.SimpleNamedElement"
  methods=[getKey getValue getName setName getNameIdentifier]
}
```

## 定义引用 Define a Reference
现在定义一个引用类来解析其用法中的属性。
这需要扩展 [`PsiReferenceBase`](upsource:///platform/core-api/src/com/intellij/psi/PsiReferenceBase.java) 并 实现 [`PsiPolyVariantReference`](upsource:///platform/core-api/src/com/intellij/psi/PsiPolyVariantReference.java).
后者使引用能够解析到一个以上的元素，或者解析有效解析用例的超集的结果。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleReference.java"}

## 定义引用贡献者 Define a Reference Contributor
引用贡献者允许 `simple_language_plugin` 从其他语言(如Java)的元素中提供对 Simple Language 的引用。
创建 `SimpleReferenceContributor` 通过子类化 [`PsiReferenceContributor`](upsource:///platform/core-api/src/com/intellij/psi/PsiReferenceContributor.java).
为属性的每次使用贡献一个引用:

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleReferenceContributor.java"}

## 注册引用贡献者 Register the Reference Contributor
`SimpleReferenceContributor` 是通过使用`com.intellij.psi.referenceContributor`扩展来注册IntelliJ平台实现的。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <psi.referenceContributor implementation="org.intellij.sdk.language.SimpleReferenceContributor"/>
  </extensions>
```

## 使用引用贡献者运行项目 Run the Project with the Reference Contributor
通过使用Gralde任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 来运行项目。

现在IDE解析属性 and 提供 [补全](https://www.jetbrains.com/help/idea/auto-completing-code.html#basic_completion) 建义：

![引用贡献者](../../../images/tutorials/custom_language_support/img/reference_contributor.png){width="800"}

 [重命名重构](https://www.jetbrains.com/help/idea/rename-refactorings.html#invoke-rename-refactoring) 从定义和用法功能可用.

![Rename](../../../images/tutorials/custom_language_support/img/rename.png){width="800"}

## 定义一个重构支持提供者 Define a Refactoring Support Provider
Support for in-place refactoring is specified explicitly in a refactoring support provider.
对就地重构的支持在重构支持提供程序中明确指定。
创建 `SimpleRefactoringSupportProvider` 通过子类化 [`RefactoringSupportProvider`](upsource:///platform/lang-api/src/com/intellij/lang/refactoring/RefactoringSupportProvider.java)
只要一个元素是一个 `SimpleProperty` ，它就可以被重构:

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleRefactoringSupportProvider.java"}

## 注册重构支持提供者 Register the Refactoring Support Provider
`SimpleRefactoringSupportProvider` 是使用`com.intellij.lang.refactoringSupport`扩展点在插件配置文件中注册实现的。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.refactoringSupport language="Simple"
            implementationClass="org.intellij.sdk.language.SimpleRefactoringSupportProvider"/>
  </extensions>
```

## 运行项目
使用Gralde任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行项目。

IDE现在支持 [refactoring](https://www.jetbrains.com/help/idea/rename-refactorings.html)：

![In Place Rename](../../../images/tutorials/custom_language_support/img/in_place_rename.png){width="800"}
