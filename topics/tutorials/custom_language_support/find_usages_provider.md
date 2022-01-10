[//]: # (title: 11. Find Usages Provider)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

`FindUsagesProvider` 使用单词扫描器在每个文件中建立单词的索引。
扫描器将文本分解成单词，并为每个单词定义上下文。

**引用**: [find_usages](find_usages.md)

## 定义查找引用提供器 Define a Find Usages Provider
`SimpleFindUsagesProvider` 实现了 [`FindUsagesProvider`](upsource:///platform/indexing-api/src/com/intellij/lang/findUsages/FindUsagesProvider.java)。
使用 [`DefaultWordsScanner`](upsource:///platform/indexing-api/src/com/intellij/lang/cacheBuilder/DefaultWordsScanner.java) 确保扫描器实现是线程安全的。
请参阅下面代码 `FindUsagesProvider` 的注释来获取更多信息。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleFindUsagesProvider.java"}

## 注册查找引用提供器 Register the Find Usages Provider
`SimpleFindUsagesProvider` 实现是通过 `com.intellij.lang.findUsagesProvider` 扩展点在插件配置文件中注册到IntelliJ 平台的。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.findUsagesProvider language="Simple"
            implementationClass="org.intellij.sdk.language.SimpleFindUsagesProvider"/>
  </extensions>
```

## 运行项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin) 运行项目。

IDE现在支持 [Find Usages](https://www.jetbrains.com/help/idea/find-highlight-usages.html) 对于任意引用属性:

![Find Usages](../../../images/tutorials/custom_language_support/img/find_usages.png)
