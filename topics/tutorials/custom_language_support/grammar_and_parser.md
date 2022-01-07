[//]: # (title: 3. Grammar and Parser)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

为了让IntelliJ平台解析一个简单语言文件，标记和元素必须基于 [IElementType](upsource:///platform/core-api/src/com/intellij/psi/tree/IElementType.java) 来定义。
还必须定义Simple Language语法来生成解析器。



**Reference**:
- [implementing_lexer](implementing_lexer.md)
- [implementing_parser_and_psi](implementing_parser_and_psi.md)

## 定义 Token Type
创建 `SimpleTokenType` 类，放在`org.intellij.sdk.language.psi` 包下 (参看 `simple_language_plugin` 代码示例) ，实现 `IElementType` 接口.

```java
package org.intellij.sdk.language.psi;
import com.intellij.psi.tree.IElementType;
import org.intellij.sdk.language.SimpleLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
public class SimpleTokenType extends IElementType {
  public SimpleTokenType(@NotNull @NonNls String debugName) {
    super(debugName, SimpleLanguage.INSTANCE);
  }
  @Override
  public String toString() {
    return "SimpleTokenType." + super.toString();
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/SimpleTokenType.java"}

## 定义 Element Type
Create the `SimpleElementType` in the `org.intellij.sdk.language.psi` package by subclassing `IElementType`.

```java
package org.intellij.sdk.language.psi;
import com.intellij.psi.tree.IElementType;
import org.intellij.sdk.language.SimpleLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
public class SimpleElementType extends IElementType {
  public SimpleElementType(@NotNull @NonNls String debugName) {
    super(debugName, SimpleLanguage.INSTANCE);
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/SimpleElementType.java"}

## 定义 Grammar
为Simple Language定义语法，在 `com/intellij/sdk/language/Simple.bnf` 文件中定义.

```properties
{
  parserClass="org.intellij.sdk.language.parser.SimpleParser"

  extends="com.intellij.extapi.psi.ASTWrapperPsiElement"

  psiClassPrefix="Simple"
  psiImplClassSuffix="Impl"
  psiPackage="org.intellij.sdk.language.psi"
  psiImplPackage="org.intellij.sdk.language.psi.impl"

  elementTypeHolderClass="org.intellij.sdk.language.psi.SimpleTypes"
  elementTypeClass="org.intellij.sdk.language.psi.SimpleElementType"
  tokenTypeClass="org.intellij.sdk.language.psi.SimpleTokenType"
}

simpleFile ::= item_*

private item_ ::= (property|COMMENT|CRLF)

property ::= (KEY? SEPARATOR VALUE?) | KEY
```

如同所示， Simple Language 文件包括属性，注释和换行符。

请看 [Grammar Kit](https://github.com/JetBrains/Grammar-Kit) 文档查看更多关于 [BNF]() 语法信息。

语法定义了对语言支持的灵活性。
上面的语法指定一个属性可以有键和值，也可以没有。
这种灵活性允许IntelliJ平台识别不正确定义的属性，并提供相应的代码分析和快速修复。

注意 `SimpleTypes` 类存在于 `elementTypeHolderClass` 属性定义的值，
它指定的从这个语法中生成的类的名称；现在这个类还是不存在的。

## 生成 Parser
既然已经定义了语法，就用PSI类生成一个解析器，通过右键点击 *Simple.bnf* 文件， **Generate Parser Code** 项.
这一步生成一个解析器和PSI元素，生成目录为 `/src/main/gen`。
将此文件夹标记为 *Generated Sources Root*，并确保所有内容的编译都没有错误。

 >  Gradle 插件 [gradle-grammarkit-plugin](https://github.com/JetBrains/gradle-grammar-kit-plugin) 可替换使用。
 >
 {type="tip"}

![Parser](../../../images/tutorials/custom_language_support/img/generated_parser.png){width="800"}
