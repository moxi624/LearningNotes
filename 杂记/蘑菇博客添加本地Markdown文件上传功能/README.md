# 蘑菇博客添加本地Markdown文件上传功能

## 前言

因为我写博客的时候，一般都会在本地使用Typora软件，提前写好Markdown格式的博客，然后在复制粘贴到蘑菇博客的博客管理中，但是因为我使用的图片都存储在本地，所以在复制Markdown写的博客的时候，是无法将图片也同时复制到Ckeditor中，因此需要一张一张把本地图片替换，重新在Ckeditor上传图片到服务器，如果遇到图片比较多的情况，一个个替换是非常麻烦的。

所以为了解决这个问题，就特意写了一个本地上传的功能，目前支持Markdown文件上传到博客管理中，后期可以考虑扩展word或者普通的txt文档，下面说一下上传的主要步骤：

首先先看最终的效果动画图：

![111.gif](images/111.gif)

## 步骤

本地博客上传目前主要分为三步

### 上传图片服务器

首先需要将图片上传到我们的图片服务器

![image-20200411111859920](images/image-20200411111859920.png)

这步的主要操作就是，将我们选中的图片都批量上传到我们的图片服务器中，然后返回我们的图片信息

因为我写的博客图片都存储在本地，没有使用图床，所以我们需要先提交到服务器，然后下一步在进行图片的替换

### 上传本地markdown文件

这一步就需要将我们的markdown选中，然后提交到后台，因为我们的博客是使用ckeditor富文本编辑器的，因此我们首先就需要将我们的markdown文件，转换成html文件

### markdown转html

这里用到的是github上的一个开源库：[flexmark-java](https://github.com/vsch/flexmark-java)

首先引入依赖

```
<!--Markdown 转 Html-->
<dependency>
    <groupId>com.vladsch.flexmark</groupId>
    <artifactId>flexmark-all</artifactId>
    <version>0.61.0</version>
</dependency>
```

然后创建方法

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

### 替换图片

替换图片的步骤就是，首先我们通过正则表达式，提取出我们转换的html里面的<img />标签

```
List<String> matchList = RegexUtils.match(html, "<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)>");
```

然后在取出里面的src标签

```
String [] splitList = matchStr.split("\"");
String pictureUrl = splitList[1];
```

因为我们的图片是本地，可能格式是这样的形式

```
<img alt="" src="image/1586532809883.png" alt="1586532809883.png" />
```

或许我们在获取到src中的内容时，还需要做的，就是和我们的图片名称进行匹配，因为刚刚图片上传后，返回来的格式是：1586532809883.png，和  image/1586532809883.png 是无法直接替换的，

我们需要做的是，拿出它的key去和我们的src匹配，匹配成功后，即存储在map中，方便下次进行替换

替换过程

```
List<String> matchList = RegexUtils.match(blogContent, "<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)>");
for (String matchStr : matchList) {
    String [] splitList = matchStr.split("\"");
    // 取出中间的图片
    if(splitList.length >= 5) {
        // alt 和 src的先后顺序
        // 得到具体的图片路径
        String pictureUrl = "";
        if(matchStr.indexOf("alt") > matchStr.indexOf("src")) {
            pictureUrl = splitList[1];
        } else {
            pictureUrl = splitList[3];
        }

        // 判断是网络图片还是本地图片
        if(!pictureUrl.startsWith(SysConf.HTTP)) {
            // 那么就需要遍历全部的map和他匹配
            for(Map.Entry<String, String> map : pictureMap.entrySet()){
                // 查看Map中的图片是否在需要替换的key中
                if(pictureUrl.indexOf(map.getKey()) > -1) {
                    if(EOpenStatus.OPEN.equals(systemConfig.getPicturePriority())) {
                        matchUrlMap.put(pictureUrl, systemConfig.getQiNiuPictureBaseUrl() + map.getValue());
                    } else {
                        matchUrlMap.put(pictureUrl, systemConfig.getLocalPictureBaseUrl() + map.getValue());
                    }
                    break;
                }
            }
        }
    }
}
```

