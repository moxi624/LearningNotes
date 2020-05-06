前言
--

之前就一直有个疑惑，在我修改文件夹名称的时候，将大写改成小写，但是使用 tortoiseGit进行提交，但是提交记录中，不会显示我修改的文件夹

就像下面这样，在我修改icon文件夹后，在提交页面没有任何记录

![](http://image.moguit.cn/1577001595366.png)

最开始我以为是git的一个bug，后面发现原来是因为git默认配置就是忽略文件夹的大小写，我们通过命令查看，是true，说明是忽略大小写的。

    git config --get core.ignorecase

我们将其修改成 false

    git config core.ignorecase false

然后在打开提交页面，就发现能够识别git的文件大小写了

![](http://image.moguit.cn/1577001791360.png)