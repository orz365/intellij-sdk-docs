[//]: # (title: 16. Code Style Settings)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

代码样式设置允许定义格式选项。
代码样式设置提供程序创建一个设置实例，并在设置/首选项中创建一个选项页面。
此示例创建一个使用默认语言代码样式设置的设置/首选项页面，该设置由语言代码样式设置提供程序自定义。

**引用**: [Code Style Settings](code_formatting.md#code-style-settings)

## 定义代码样式设置
通过子类化定义 [`CustomCodeStyleSettings`](upsource:///platform/code-style-api/src/com/intellij/psi/codeStyle/CustomCodeStyleSettings.java) Simple Language 的代码样式设置。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleCodeStyleSettings.java"}

## 定义代码样式设置提供程序
代码样式设置提供给IntelliJ平台一个标准的方式来实例化 Simple Language 的 `CustomCodeStyleSettings`。
通过子类化  [`CodeStyleSettingsProvider`](upsource:///platform/lang-api/src/com/intellij/psi/codeStyle/CodeStyleSettingsProvider.java) 定义 Simple Language 的代码样式设置提供程序。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleCodeStyleSettingsProvider.java"}

## 注册代码样式设置提供程序
`SimpleCodeStyleSettingsProvider` 实现使用 `com.intellij.codeStyleSettingsProvider` 扩展点在插件配置文件中注册给IntelliJ平台。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <codeStyleSettingsProvider implementation="org.intellij.sdk.language.SimpleCodeStyleSettingsProvider"/>
  </extensions>
```

## 定义语言代码样式设置提供程序
通过子类化 [`LanguageCodeStyleSettingsProvider`](upsource:///platform/lang-api/src/com/intellij/psi/codeStyle/LanguageCodeStyleSettingsProvider.java)
为Simple Language 定义一个代码样式设置提供器，用来为特定语言提供通用代码样式设置。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleLanguageCodeStyleSettingsProvider.java"}

## 注册语言代码样式设置提供程序
`SimpleLanguageCodeStyleSettingsProvider` 的实现使用`com.intellij.langCodeStyleSettingsProvider` 扩展点在插件配置文件中注册给IntelliJ 平台。

```xml
 <extensions defaultExtensionNs="com.intellij">
    <langCodeStyleSettingsProvider
            implementation="org.intellij.sdk.language.SimpleLanguageCodeStyleSettingsProvider"/>
 </extensions>
```

## 运行该项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行该项目。

在IDE开发实例中， 打开 Simple Language 代码格式化页面 : <menupath>Settings/Preferences | Editor | Code Style | Simple</menupath>.

![Code Style Settings](../../../images/tutorials/custom_language_support/img/code_style_settings.png)
