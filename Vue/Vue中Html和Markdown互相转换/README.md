# Vue中Html和Markdown互相转换

## 前言

最近想实现的一个功能，就是将系统中的博客进行导出成Markdown格式，后面经过了调研发现有两种方法能够满足需求，一个是Java后台中将HTML转换成Markdown， 然后导出。第二种方式是在客户端将Html转换成Markdown

## 前台处理

### MarkdownToHtml

#### 安装

前台处理Markdown转换成Html，使用的是一款Vue组件 `showdown`：[点我传送](https://github.com/showdownjs/showdown)

前端处理的好处是不需要占用后端的计算资源，因此首选是让客户端做这种处理的事情

首先我们需要安装依赖

```
npm install showdown --save
```

或者使用CDN

```
 https://unpkg.com/showdown/dist/showdown.min.js
```

#### Markdown 转成 Html

```
var showdown  = require('showdown'),
    converter = new showdown.Converter(),
    text      = '# hello, markdown!',
    html      = converter.makeHtml(text);
```

### HtmlToMarkdown

#### 安装

前台处理Html转换成Markdown，使用的是一款Vue组件 `turndown`：[点我传送](https://github.com/domchristie/turndown)

首先安装依赖

```
npm install turndown --save
```

或使用CDN加速

```
<script src="https://unpkg.com/turndown/dist/turndown.js"></script>
```

#### 使用

```
// For Node.js
var TurndownService = require('turndown')
var turndownService = new TurndownService()
var markdown = turndownService.turndown('<h1>Hello world!</h1>')
```

## 后端处理

后端处理使用的是 `flexmark-java` ：[点我传送](https://github.com/vsch/flexmark-java)

### 引入依赖

```
<!--Markdown 转 Html-->
<dependency>
    <groupId>com.vladsch.flexmark</groupId>
    <artifactId>flexmark-all</artifactId>
    <version>${flexmark.version}</version>
</dependency>
```

### MarkdownToHtml

```
    /**
     * Markdown转Html
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdown);
        String html = renderer.render(document);
        return html;
    }
```

### HtmlToMarkdown

```
    /**
     * Html 转 Markdown
     * @param html
     * @return
     */
    public static String htmlToMarkdown(String html) {
        MutableDataSet options = new MutableDataSet();
        String markdown = FlexmarkHtmlConverter.builder(options).build().convert(html);
        System.out.println(markdown);
        return markdown;
    }
```

