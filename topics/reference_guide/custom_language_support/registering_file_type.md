[//]: # (title: Registering a File Type)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

The first step in developing a custom language plugin is registering a file type associated with the language.
开发自定义语言插件的第一步是注册与该语言相关的文件类型。

The IDE typically determines the type of a file by looking at its filename or extension.
IDE 通常是根据文件名和扩展名来决定文件的类型。

自定义语言文件类型是一个继承 [`LanguageFileType`](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java) 的类，并将子类实例传入构造函数当作参数。

### 注册
<tabs>

<tab title="2019.2 and later">

2019.2或以后的版本，使用`com.intellij.fileType`扩展点 通过`implementationClass` 和 `fieldName`属性 来注册 `LanguageFileType` 实现和实例。
同时, `name` 和 `language` 必须声明匹配 `FileType.getName()` ， 语言的ID通过 `LanguageFileType.getLanguage()` 获取。

要在IDE中关联文件类型，请指定一个或多个关联，如下表所示。

| Association type        | Attribute                                   | Attribute value                                                 |
|-------------------------|---------------------------------------------|-----------------------------------------------------------------|
| Filename extension(s)   | `extensions`                                | Semicolon-separated list of extensions, without `.` prefix      |
| Hard coded file name(s) | `fileNames`/<br/>`fileNamesCaseInsensitive` | Semicolon-separated list of exact (case-insensitive) file names |
| Filename pattern(s)     | `patterns`                                  | Semicolon-separated list of patterns (`*` and `?`)              |
| Hashbang _(2020.2+)_    | `hashBangs`                                 | Semicolon-separated list of hash bang patterns                  |

</tab>

<tab title="Pre-2019.2">

注册一个文件类型，插件开发者提供了一个子类 [`FileTypeFactory`](upsource:///platform/ide-core/src/com/intellij/openapi/fileTypes/FileTypeFactory.java)
它是通过`com.intellij.fileTypeFactory`扩展点来注册的。

</tab>
</tabs>

**示例**
- [自定义语言支持教程：语言和文件类型](language_and_filetype.md)
- [`LanguageFileType`](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java) 子类在 [Properties language plugin](upsource:///plugins/properties/properties-psi-api/src/com/intellij/lang/properties/PropertiesFileType.java)

为了验证文件类型是否成功注册，你可以实现 [`LanguageFileType.getIcon()`](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/LanguageFileType.java) 方法来验证图标是否在相应的文件类型中显示。
(请看 [Working with Icons and Images](work_with_icons_and_images.md))



### 附加特性

如果你想让ide显示一个提示，提示用户你的插件支持特定的文件类型， 请看 [Plugin Recommendations](https://plugins.jetbrains.com/docs/marketplace/intellij-plugin-recommendations.html).

在操作系统中控制与IDE的文件类型关联, 实现 [`OSFileIdeAssociation`](upsource:///platform/core-api/src/com/intellij/openapi/fileTypes/OSFileIdeAssociation.java) (2020.3).
