[//]: # (title: 6. PSI Helpers and Utilities)

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

辅助类和实用程序可以嵌入到由Grammar-Kit生成的代码中。

## 为生成的PSI元素定义辅助方法（ Define Helper Methods for Generated PSI Elements）
PSI类中的自定义方法是单独定义的，Grammar-Kit 将它们嵌入到生成的代码中。
用以下方法定义一个实用程序类:

```java
package org.intellij.sdk.language.psi.impl;

import com.intellij.lang.ASTNode;

public class SimplePsiImplUtil {
  public static String getKey(SimpleProperty element) {
    ASTNode keyNode = element.getNode().findChildByType(SimpleTypes.KEY);
    if (keyNode != null) {
      // IMPORTANT: Convert embedded escaped spaces to simple spaces
      return keyNode.getText().replaceAll("\\\\ ", " ");
    } else {
      return null;
    }
  }

  public static String getValue(SimpleProperty element) {
    ASTNode valueNode = element.getNode().findChildByType(SimpleTypes.VALUE);
    if (valueNode != null) {
      return valueNode.getText();
    } else {
      return null;
    }
  }
}
```

解析器生成 `SimpleProperty` 接口的引用 in the code above.

## 更新语法并重新生成解析器 Update Grammar and Regenerate the Parser
Now the utility class is added to the grammar file via the `psiImplUtilClass` attribute.
Add methods for a particular rule to specify which one should be used for PSI classes.
Compare the last line of the grammar below to the [previous definition](grammar_and_parser.md#define-the-grammar).

```java
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

  psiImplUtilClass="org.intellij.sdk.language.psi.impl.SimplePsiImplUtil"
}

simpleFile ::= item_*

private item_ ::= (property|COMMENT|CRLF)

property ::= (KEY? SEPARATOR VALUE?) | KEY {methods=[getKey getValue]}
```

在修改语法之后，重新生成解析器和PSI类。

## 定义一个实用程序搜索属性 Define a Utility to Search Properties
创建一个实用程序类，用于在项目中搜索PSI元素中已定义的属性。
稍后在实现 [代码补全](https://www.jetbrains.com/help/idea/auto-completing-code.html) 时将使用此实用程序。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleUtil.java"}
