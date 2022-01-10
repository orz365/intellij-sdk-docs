[//]: # (title: 2. Language and File Type)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<chunk id="custom_language_tutorial_header">

 > This page is part of multi step [](custom_language_support_tutorial.md).
 {type="tip"}

</chunk>

IntelliJ平台通过检查文件的名称来确定文件类型。
每种语言都有  [Language](upsource:///platform/core-api/src/com/intellij/lang/Language.java)  和 [LanguageFileType](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java)  对象来定义语言。
在插件配置文件中注册 `LanguageFileType` 到IntelliJ平台。

**引用**: [注册文件类型](../../reference_guide/custom_language_support/registering_file_type.md)

## 定义 Language
本教程中实现的语言名为“Simple”——注意名称的大小写。
`SimpleLanguage` 类定义在代码示例 `simple_language_plugin` 中的 `org.intellij.sdk.language` 包里面。

```java
package org.intellij.sdk.language;
import com.intellij.lang.Language;
public class SimpleLanguage extends Language {
  public static final SimpleLanguage INSTANCE = new SimpleLanguage();
  private SimpleLanguage() {
    super("Simple");
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleLanguage.java"}

## 定义 Icon
图标 [icon](https://github.com/JetBrains/intellij-sdk-code-samples/blob/main/simple_language_plugin/src/main/resources/icons/jar-gray.png) 被定义在 `SimpleIcons` 类中.
请参照 [使用图标和图像](../../reference_guide/work_with_icons_and_images.md) 更多关于定义和使用图标细节。

```java
package org.intellij.sdk.language;
import com.intellij.openapi.util.IconLoader;
import javax.swing.*;
public class SimpleIcons {
  public static final Icon FILE = IconLoader.getIcon("/icons/jar-gray.png", SimpleIcons.class);
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleIcons.java"}

## 定义 FileType
Simple Language 文件类型是通过子类化 [`LanguageFileType`](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java) 定义的。

```java
package org.intellij.sdk.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
public class SimpleFileType extends LanguageFileType {
  public static final SimpleFileType INSTANCE = new SimpleFileType();
  private SimpleFileType() {
    super(SimpleLanguage.INSTANCE);
  }
  @NotNull
  @Override
  public String getName() {
    return "Simple File";
  }
  @NotNull
  @Override
  public String getDescription() {
    return "Simple language file";
  }
  @NotNull
  @Override
  public String getDefaultExtension() {
    return "simple";
  }
  @Nullable
  @Override
  public Icon getIcon() {
    return SimpleIcons.FILE;
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFileType.java"}

## 注册 FileType

<tabs>

<tab title="2019.2 and later">

直接注册是可能的 - 没有 `FileTypeFactory` 是必需的。

文件类型是通过在 <path>plugin.xml</path>中通过`com.intellij.fileType` 扩展点注册的，注册<path>*.simple</path>扩展：

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

### 定义 FileType Factory
First, define `SimpleFileTypeFactory` as a subclass of [`FileTypeFactory`](upsource:///platform/ide-core/src/com/intellij/openapi/fileTypes/FileTypeFactory.java).

```java
package org.intellij.sdk.language;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;
/**
 * Note: This class is only used with the fileTypeFactory extension point
 * for versions of the IntelliJ Platform prior to v2019.2.
 */
@SuppressWarnings("deprecation")
public class SimpleFileTypeFactory extends FileTypeFactory {
  @Override
  public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
    fileTypeConsumer.consume(SimpleFileType.INSTANCE);
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFileTypeFactory.java"}

### 注册 FileType Factory
The `SimpleFileTypeFactory` is registered using the `com.intellij.openapi.fileTypes.FileTypeFactory` extension point in <path>plugin.xml</path>.

```xml
  <extensions defaultExtensionNs="com.intellij">
    <fileTypeFactory implementation="org.intellij.sdk.language.SimpleFileTypeFactory"/>
  </extensions>
```

</tab>

</tabs>

## 运行项目

Run the plugin by using the Gradle [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin).

Create an empty file with the extension `*.simple`, and IntelliJ IDEA automatically associates it with our language.
Note the appearance of the Simple Language file icon next to the `test.simple` file in the **Project Tool Window**, and the editor tab for the file.

![File Type Factory](../../../images/tutorials/custom_language_support/img/file_type_factory.png){width="800"}
