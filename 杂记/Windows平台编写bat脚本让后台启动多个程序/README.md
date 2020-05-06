前言
--

最近在开发蘑菇博客的时候，需要启动的服务比较多，比如nginx，solr，redis，mysql等，我就想着在Windows平台能不能编写个脚本，让它们一键启动

当然小伙伴可以把它们注册成系统服务，然后设置开机自启，更加省时省力，现在做的是将他们写在bat脚本上

启动脚本格式
------

    # /b 代表后台启动
    start /b  server

编写脚本
----

我们首先创建一个  startup.bat文件，然后输出下面的内容

    start /d  java -jar zipkin.jar

这样他就会帮我们启动zipikin服务了，我们设置多个，即可完成项目的一键启动

    start  java -jar E:/Software/Zipkin/zipkin.jar
    start  E:/Software/nginx/nginx.exe
    start  E:/Software/redis/Redis-x64-3.2.100/redis-server.exe
    start  E:/Software/xampp/tomcat/bin/startup.bat
    start  E:/Software/xampp/mysql/bin/mysqld.exe