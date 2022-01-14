[//]: # (title: Implementing Lexer)

<!-- Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file. -->

词法分析程序 [lexical analyzer](https://en.wikipedia.org/wiki/Lexical_analysis)， 定义如何将文件内容分解为标记。
lexer几乎是自定义语言插件的所有特性的基础，从基本的语法高亮显示到高级代码分析特性。
lexer的API由 [`Lexer`](upsource:///platform/core-api/src/com/intellij/lexer/Lexer.java) 接口定义.

IDE在三个主要的上下文中调用lexer，插件可以为这些上下文中提供不同的lexer实现:

*  **语法高亮**： lexer是通过
   [`SyntaxHighlighterFactory`](upsource:///platform/editor-ui-api/src/com/intellij/openapi/fileTypes/SyntaxHighlighterFactory.java)
   的接口 实现返回，并在 `com.intellij.lang.syntaxHighlighterFactory` 扩展点中注册。
*  **构建文件的语法树**： lexer 需要从
   [`ParserDefinition.createLexer()`](upsource:///platform/core-api/src/com/intellij/lang/ParserDefinition.java) 返回，
   [`ParserDefinition`](upsource:///platform/core-api/src/com/intellij/lang/ParserDefinition.java)
   接口 在 `com.intellij.lang.parserDefinition` 扩展点注册

*  **构建文件中包含的单词的索引**：
   如果使用了基于词法分析器的单词扫描程序实现，则将该词法分析器传递给
   [`DefaultWordsScanner`](upsource:///platform/indexing-api/src/com/intellij/lang/cacheBuilder/DefaultWordsScanner.java)
   构造函数。

用于语法高亮显示的lexer可以增量调用，只处理文件的更改部分。
相反，在其他上下文中使用的词法分析器总是被调用来处理嵌入在不同语言文件中的整个文件或完整的语言构造。

一个可以递增使用的lexer可能需要返回它的*state*，这意味着对应于文件中每个位置的上下文。
例如， [Java lexer](upsource:///java/java-psi-impl/src/com/intellij/lang/java/lexer/JavaLexer.java) could have separate states for top-level context, comment context, and string literal context.
An essential requirement for a syntax highlighting lexer is that its state must be represented by a single integer number returned from [`Lexer.getState()`](upsource:///platform/core-api/src/com/intellij/lexer/Lexer.java).
That state will be passed to the [`Lexer.start()`](upsource:///platform/core-api/src/com/intellij/lexer/Lexer.java) method, along with the start offset of the fragment to process, when lexing is resumed from the middle of a file.
Lexers used in other contexts can always return `0` from the `getState()` method.

创建自定义语言插件的lexer，最简单的方法就是使用 [JFlex](https://jflex.de).
类 [`FlexLexer`](upsource:///platform/core-api/src/com/intellij/lexer/FlexLexer.java) 和 [`FlexAdapter`](upsource:///platform/core-api/src/com/intellij/lexer/FlexAdapter.java) 将 JFlex lexers 适配到  IntelliJ 平台 Lexer API.
We have a [patched version of JFlex](https://github.com/JetBrains/intellij-deps-jflex) that can be used with the lexer skeleton file located at *tools/lexer/idea-flex.skeleton* in the [IntelliJ IDEA Community Edition](https://github.com/JetBrains/intellij-community) source to create lexers compatible with [`FlexAdapter`](upsource:///platform/core-api/src/com/intellij/lexer/FlexAdapter.java).
The patched version of JFlex provides a new command-line option `--charat` that changes the JFlex generated code to work with the IntelliJ Platform skeleton.
Enabling `--charat` option passes the source data for lexing as a [`CharSequence`](https://docs.oracle.com/javase/8/docs/api/java/lang/CharSequence.html) and not as an array of characters.

对于使用JFlex开发lexer来说， [GrammarKit plugin](https://plugins.jetbrains.com/plugin/6606-grammar-kit) 会很有用。
It provides syntax highlighting and other useful features for editing JFlex files.
它提供了语法高亮和其他有用的功能来编辑JFlex文件。

 >  Lexers, and in particular JFlex-based lexers, need to be created so that they always match the entire contents of the file, without any gaps between tokens, and generate special tokens for characters which are not valid at their location.
> Lexers must never abort prematurely because of an invalid character.
 >
 {type="note"}

**示例**:
- [`Lexer`](upsource:///plugins/properties/src/com/intellij/lang/properties/parsing/Properties.flex) definition for [Properties language plugin](upsource:///plugins/properties)
- [Custom Language Support Tutorial: Lexer](lexer_and_parser_definition.md)

Types of tokens for lexers are defined by instances of [`IElementType`](upsource:///platform/core-api/src/com/intellij/psi/tree/IElementType.java).
Many token types common for all languages are defined in the [`TokenType`](upsource:///platform/core-api/src/com/intellij/psi/TokenType.java) interface.
Custom language plugins should reuse these token types wherever applicable.
For all other token types, the plugin needs to create new [`IElementType`](upsource:///platform/core-api/src/com/intellij/psi/tree/IElementType.java) instances and associate with the language in which the token type is used.
The same [`IElementType`](upsource:///platform/core-api/src/com/intellij/psi/tree/IElementType.java) instance should be returned every time a particular token type is encountered by the lexer.

**示例:**
[Token types](upsource:///plugins/properties/properties-psi-api/src/com/intellij/lang/properties/parsing/PropertiesTokenTypes.java) for [Properties language plugin](upsource:///plugins/properties)

An important feature that can be implemented at the lexer level is mixing languages within a file, such as embedding fragments of Java code in some template language.
Suppose a language supports embedding its fragments in another language.
In that case, it needs to define the chameleon token types for different types of fragments that can be embedded, and these token types need to implement the [`ILazyParseableElementType`](upsource:///platform/core-api/src/com/intellij/psi/tree/ILazyParseableElementType.java) interface.
The enclosing language's lexer needs to return the entire fragment of the embedded language as a single chameleon token, of the type defined by the embedded language.
To parse the contents of the chameleon token, the IDE will call the parser of the embedded language through a call to [`ILazyParseableElementType.parseContents()`](upsource:///platform/core-api/src/com/intellij/psi/tree/ILazyParseableElementType.java).
