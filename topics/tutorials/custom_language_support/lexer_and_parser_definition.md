[//]: # (title: 4. Lexer and Parser Definition)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

The lexical analyzer defines how the contents of a file are broken into tokens, which is the basis for supporting custom language features.
The easiest way to create a lexer is to use [JFlex](https://jflex.de/).

**Reference**: [](implementing_lexer.md)

## Required Project Configuration Change
The previous tutorial step [Grammar and Parser](grammar_and_parser.md), and this page, generate source files in the directory `src/main/gen`.
To include those files, the project's `sourceSets` must be expanded by inserting the following line in the project's `build.gradle` file:

```groovy
  sourceSets.main.java.srcDirs 'src/main/gen'
```

Or the following line in the project's `build.gradle.kts` file:
```kotlin
  sourceSets["main"].java.srcDirs("src/main/gen")
```

Reload the Gradle project for changes to take effect.

## 定义 Lexer
定义一个 `Simple.flex` 文件，包括Simple Language的词法解析规则，就放在 `org.intellij.sdk.language.Simple.flex` 包下。

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/Simple.flex"}

## 生成 Lexer 类
现在通过 **JFlex Generator** 生成一个词法解析类，点击 `Simple.flex` 文件的右键就可以看到此项.

 Grammar-Kit 插件使用 JFlex 词法解析生成器.
第一次运行的时候，JFlex提示要下载JFlex库和框架的目标文件夹。
 选择项目根目录，例如 `code_samples/simple_language_plugin`.

After that, the IDE generates the lexer under the `gen` directory, for example in `simple_language_plugin/src/main/gen/org/intellij/sdk/language/SimpleLexer`.

 >  Gradle plugin [gradle-grammarkit-plugin](https://github.com/JetBrains/gradle-grammar-kit-plugin) can be used alternatively.
 >
 {type="tip"}

See [Implementing Lexer](implementing_lexer.md) for more information about using _JFlex_ with the IntelliJ Platform.

## Define a Lexer Adapter
The JFlex lexer needs to be adapted to the IntelliJ Platform Lexer API.
This is done by subclassing [`FlexAdapter`](upsource:///platform/core-api/src/com/intellij/lexer/FlexAdapter.java).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleLexerAdapter.java"}

## Define a Root File
The `SimpleFile` implementation is the top-level node of the [tree of `PsiElements`](implementing_parser_and_psi.md) for a Simple Language file.

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/psi/SimpleFile.java"}

## Define a Parser
The Simple Language parser is defined by subclassing [`ParserDefinition`](upsource:///platform/core-api/src/com/intellij/lang/ParserDefinition.java).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleParserDefinition.java"}

## Register the Parser Definition
Registering the parser definition in the <path>plugin.xml</path> file makes it available to the IntelliJ Platform.
Use the `com.intellij.lang.parserDefinition` extension point for registration.
For example, see `simple_language_plugin/src/main/resources/META-INF/plugin.xml`.

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.parserDefinition language="Simple"
            implementationClass="org.intellij.sdk.language.SimpleParserDefinition"/>
  </extensions>
```

## Run the Project

Run the plugin by using the Gradle [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin).

Create a `test.simple` properties file with the following content:

```text
# You are reading the ".properties" entry.
! The exclamation mark can also mark text as comments.
website = https://en.wikipedia.org/
language = English
# The backslash below tells the application to continue reading
# the value onto the next line.
message = Welcome to \
          Wikipedia!
# Add spaces to the key
key\ with\ spaces = This is the value that could be looked up with the key "key with spaces".
# Unicode
tab : \u0009
```

Now open the *PsiViewer* tool window and check how the lexer breaks the content of the file into tokens, and the parser parsed the tokens into PSI elements.

![PSI Elements](../../../images/tutorials/custom_language_support/img/psi_elements.png)
