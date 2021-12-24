[//]: # (title: 2. Language and File Type)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<chunk id="custom_language_tutorial_header">

 > This page is part of multi step [](custom_language_support_tutorial.md).
 {type="tip"}

</chunk>

IntelliJ平台通过检查文件的名称来确定文件类型。
每种语言都有  [Language](upsource:///platform/core-api/src/com/intellij/lang/Language.java)  和 [LanguageFileType](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java)  对象来定义语言。
在插件配置文件中注册 `LanguageFileType` 到IntelliJ平台。

**引用**: [注册文件类型](registering_file_type.md)

## 定义语言
本教程中实现的语言名为“Simple”——注意名称的大小写。
`SimpleLanguage` 类定义在代码示例 `simple_language_plugin` 中的 `org.intellij.sdk.language` 包里面。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleLanguage.java"}

## 定义一个图标
图标 [icon](https://github.com/JetBrains/intellij-sdk-code-samples/blob/main/simple_language_plugin/src/main/resources/icons/jar-gray.png) 被定义在 `SimpleIcons` 类中.
请参照 [使用图标和图像](work_with_icons_and_images.md) 更多关于定义和使用图标细节。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleIcons.java"}

## Define a FileType
The Simple Language file type is defined by subclassing [`LanguageFileType`](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java):

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFileType.java"}

## Register the FileType

<tabs>

<tab title="2019.2 and later">

Direct registration is possible - no `FileTypeFactory` is required.

Instead, the file type is registered via the `com.intellij.fileType` extension point in <path>plugin.xml</path> and registered with <path>*.simple</path> extension:

```xml
  <extensions defaultExtensionNs="com.intellij">
    <fileType name="Simple File"
              implementationClass="org.intellij.sdk.language.SimpleFileType"
              fieldName="INSTANCE"
              language="Simple"
              extensions="simple"/>
  </extensions>
```

</tab>

<tab title="Pre-2019.2">

### Define a FileType Factory
First, define `SimpleFileTypeFactory` as a subclass of [`FileTypeFactory`](upsource:///platform/ide-core/src/com/intellij/openapi/fileTypes/FileTypeFactory.java).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFileTypeFactory.java"}

### Register the FileType Factory
The `SimpleFileTypeFactory` is registered using the `com.intellij.openapi.fileTypes.FileTypeFactory` extension point in <path>plugin.xml</path>.

```xml
  <extensions defaultExtensionNs="com.intellij">
    <fileTypeFactory implementation="org.intellij.sdk.language.SimpleFileTypeFactory"/>
  </extensions>
```

</tab>

</tabs>

## Run the Project

Run the plugin by using the Gradle [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin).

Create an empty file with the extension `*.simple`, and IntelliJ IDEA automatically associates it with our language.
Note the appearance of the Simple Language file icon next to the `test.simple` file in the **Project Tool Window**, and the editor tab for the file.

![File Type Factory](file_type_factory.png){width="800"}
