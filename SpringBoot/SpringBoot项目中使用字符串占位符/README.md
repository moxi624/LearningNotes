前言
--

前阵子群里很多小伙伴在咨询，怎么更改蘑菇博客项目的版权这部分代码

![](http://image.moguit.cn/822593737ee24ab497e5b911bd0241bd)

因为最开始为了偷懒，就直接写死在了代码里面，后面发现如果要更改的话，得找到对应的代码，一般在找对应的接口的时候，还是比较麻烦的

因此就想着将它单独写在application.yml的配置文件中，然后使用java占位符的方式填充我们需要的更改的地方

    #博客相关配置
    BLOG:
    
      # 原创模板
      ORIGINAL_TEMPLATE: 本文为蘑菇博客原创文章，转载无需和我联系，但请注明来自蘑菇博客 http://www.moguit.cn
      # 转载模板
      REPRINTED_TEMPLATE: 本着开源共享、共同学习的精神，本文转载自 %S ，版权归 %S 所有，如果侵权之处，请联系博主进行删除，谢谢~

然后到后端，我们就需要进行替换了

    String reprintedTemplate = REPRINTED_TEMPLATE;
    String [] variable = {blog.getArticlesPart(), blog.getAuthor()};
    String str = String.format(reprintedTemplate, variable);
    blog.setCopyright(str);

这里使用了String.format，里面有两个参数，一个是给定的模板，另一个参数就是我们需要替换的列表

他会找到对应的占位符，然后将数组中的变量进行逐个替换我们之前模板中的占位符%s