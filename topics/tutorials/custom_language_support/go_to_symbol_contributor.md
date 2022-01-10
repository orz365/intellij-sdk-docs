[//]: # (title: 13. Go To Symbol Contributor)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

_Go to Symbol Contributor_ 帮助用户导航到任何PSI元素的名称。

**Reference**: [go_to_class_and_go_to_symbol](go_to_class_and_go_to_symbol.md)

## 为生成的PSI元素定义一个辅助方法 Define a Helper Method for Generated PSI Elements
来指定 PSI element 外观， 在 <menupath>Navigate | Symbol</menupath> 弹出窗口, <control>Structure</control> 工具窗口, 或其他组件, 应该实现 `getPresentation()`.
该方法定义在工具类 `SimplePsiImplUtil`，解析器和PSI类必须重新生成。
将下面的方法添加到 `SimplePsiImplUtil`:

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/impl/SimplePsiImplUtil.java" include-symbol="getPresentation"}

## 更新语法并重新生成解析器 Update Grammar and Regenerate the Parser
Now add the `SimplePsiImplUtil.getPresentation()` to the `property` methods definition in the <path>Simple.bnf</path> grammar file
by replacing the `property` definition with the lines below.
更新完文件后，别忘了重新生成解析器。
右键 <path>Simple.bnf</path> ，选择 <control>Generate Parser Code</control>.

```java
property ::= (KEY? SEPARATOR VALUE?) | KEY {
  mixin="org.intellij.sdk.language.psi.impl.SimpleNamedElementImpl"
  implements="org.intellij.sdk.language.psi.SimpleNamedElement"
  methods=[getKey getValue getName setName getNameIdentifier getPresentation]
}
```

## 定义一个转向符号贡献者 Define a Go To Symbol Contributor
提供 items 给 <menupath>Navigate | Symbol</menupath> 结果集, 子类 [`ChooseByNameContributor`](upsource:///platform/lang-api/src/com/intellij/navigation/ChooseByNameContributor.java)创建 `SimpleChooseByNameContributor`:

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleChooseByNameContributor.java"}

## 注册转向符号贡献者 Register the Go To Symbol Contributor
`SimpleChooseByNameContributor`实现是`com.intellij.gotoSymbolContributor`扩展点在插件配置文件中注册给IntelliJ平台的。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <gotoSymbolContributor
            implementation="org.intellij.sdk.language.SimpleChooseByNameContributor"/>
  </extensions>
```

## 运行项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行项目。

现在IDE支持 导航到属性定义 通过名称匹配 <menupath>Navigate | Symbol</menupath> 行为.

![Go To Symbol](../../../images/tutorials/custom_language_support/img/go_to_symbol.png){width="800"}
