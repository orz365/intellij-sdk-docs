[//]: # (title: 5. Syntax Highlighter and Color Settings Page)

<!-- Copyright 2000-2021 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

<include src="language_and_filetype.md" include-id="custom_language_tutorial_header"></include>

第一级语法高亮显示基于lexer输出，由 `SyntaxHighlighter` 提供。
一个插件也可以定义基于 `ColorSettingPage` 的颜色设置，这样用户就可以配置高亮颜色。
`SimpleSyntaxHighlighter`，`SimpleSyntaxHighlighterFactory` 和 `SimpleColorSettingsPage` 在`simple_language_plugin` 代码示例中进行了演示。

**引用**: [syntax_highlighting_and_error_highlighting](syntax_highlighting_and_error_highlighting.md)

## 定义 Syntax Highlighter（语法高亮）
Simple Language 语法高亮类继承 [`SyntaxHighlighterBase`](upsource:///platform/editor-ui-api/src/com/intellij/openapi/fileTypes/SyntaxHighlighterBase.java).
推荐在 [Color Scheme Management](color_scheme_management.md#text-attribute-key-dependency),
Simple Language 高亮文本属性被指定为依赖于一个标准的Intellij平台 Key 值。
对于 Simple Language，只定义一个方案。

```java
package org.intellij.sdk.language;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.intellij.sdk.language.psi.SimpleTypes;
import org.jetbrains.annotations.NotNull;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
public class SimpleSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey SEPARATOR =
          createTextAttributesKey("SIMPLE_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
  public static final TextAttributesKey KEY =
          createTextAttributesKey("SIMPLE_KEY", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey VALUE =
          createTextAttributesKey("SIMPLE_VALUE", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey COMMENT =
          createTextAttributesKey("SIMPLE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BAD_CHARACTER =
          createTextAttributesKey("SIMPLE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
  private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
  private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
  private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new SimpleLexerAdapter();
  }
  @Override
  public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(SimpleTypes.SEPARATOR)) {
      return SEPARATOR_KEYS;
    }
    if (tokenType.equals(SimpleTypes.KEY)) {
      return KEY_KEYS;
    }
    if (tokenType.equals(SimpleTypes.VALUE)) {
      return VALUE_KEYS;
    }
    if (tokenType.equals(SimpleTypes.COMMENT)) {
      return COMMENT_KEYS;
    }
    if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    }
    return EMPTY_KEYS;
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleSyntaxHighlighter.java"}

### 定义 Syntax Highlighter Factory（语法高亮工厂）
工厂为IntelliJ平台提供了一种标准的方式来实例化Simple Language文件的语法高亮器。
这里， `SimpleSyntaxHighlighterFactory` 子类 [`SyntaxHighlighterFactory`](upsource:///platform/editor-ui-api/src/com/intellij/openapi/fileTypes/SyntaxHighlighterFactory.java).

```java
package org.intellij.sdk.language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
public class SimpleSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
  @NotNull
  @Override
  public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
    return new SimpleSyntaxHighlighter();
  }
}
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleSyntaxHighlighterFactory.java"}

### 注册 Syntax Highlighter Factory（语法高亮工厂）
使用 `com.intellij.lang.syntaxHighlighterFactory` 扩展点，在IntelliJ平台的插件配置文件中向注册工厂。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <lang.syntaxHighlighterFactory language="Simple"
                implementationClass="org.intellij.sdk.language.SimpleSyntaxHighlighterFactory"/>
  </extensions>
```

### 使用默认颜色运行项目
打开示例 Simple Language [properties file ](lexer_and_parser_definition.md) (`test.simple`) .

[comment]: <> (The colors for Simple Language Key, Separator, and Value highlighting default to the IDE _Language Defaults_ for Keyword, Braces, Operators, and Strings, respectively.)

Simple Language 键、分隔符和突出显示值的颜色分别默认为IDE _Language Defaults_ 值，用于关键字、花括号、操作符和字符串。

![Syntax highlighter](../../../images/tutorials/custom_language_support/img/syntax_highlighter.png)

## 定义一个颜色设置页面 （Define a Color Settings Page ）

[comment]: <> (The color settings page adds the ability for users to customize color settings for the highlighting in Simple Language files.)
颜色设置页面增加了用户   自定义 Simple Language 文件中高亮显示颜色设置  的能力。
 `SimpleColorSettingsPage` 实现 [`ColorSettingsPage`](upsource:///platform/platform-api/src/com/intellij/openapi/options/colors/ColorSettingsPage.java).

```java
```
{src="simple_language_plugin/src/main/java/org/intellij/sdk/language/SimpleColorSettingsPage.java"}

### 注册颜色设置页面（Register the Color Settings Page）
在IntelliJ平台的插件配置文件中使用  `com.intellij.colorSettingsPage` 扩展点来注册Simple Language 的颜色设置页面。

```xml
  <extensions defaultExtensionNs="com.intellij">
    <colorSettingsPage implementation="org.intellij.sdk.language.SimpleColorSettingsPage"/>
  </extensions>
```

### 运行项目
使用Gradle任务 [runIde task](gradle_prerequisites.md#running-a-simple-gradle-based-intellij-platform-plugin).

在IDE开发实例中， 打开示例 Simple Language 的高亮设置页面: <menupath>Settings/Preferences | Editor | Color Scheme | Simple</menupath>.
每一个颜色初始化值都继承了 <control>Language Defaults</control> 的值。

![Color Settings Page](color_settings_page.png)
