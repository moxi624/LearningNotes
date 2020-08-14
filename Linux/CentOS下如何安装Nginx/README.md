1、下载nginx
---------

官方网站  http://nginx.org

下载链接：[http://nginx.org/download/](http://nginx.org/download/)

下载完成后的安装包：

![](http://image.moguit.cn/1556796571439.png)

2、使用解压命令进行解压
------------

    tar -zxvf nginx-1.13.7.tar.gz

3、在安装所需的安装环境
------------

### 安装gcc环境

    yum install gcc-c++

### 安装第三方开发包

\- **PCRE**(Perl Compatible Regular Expressions)是一个Perl库，包括 perl 兼容的正则表达式库。nginx的http模块使用pcre来解析正则表达式，所以需要在linux上安装pcre库

注：pcre-devel是使用pcre开发的一个二次开发库。nginx也需要此库。

    yum install -y pcre pcre-devel

\- **zlib**库提供了很多种压缩和解压缩的方式，nginx使用zlib对http包的内容进行gzip，所以需要在linux上安装zlib库。

    yum install -y zlib zlib-devel

\- **OpenSSL** 是一个强大的安全套接字层密码库，囊括主要的密码算法、常用的密钥和证书封装管理功能及SSL协议，并提供丰富的应用程序供测试或其它目的使用。nginx不仅支持http协议，还支持https（即在ssl协议上传输http），所以需要在linux安装openssl库。

    yum install -y openssl openssl-devel

安装好上面的依赖后，进入解压的好的nginx目录中
-------------------------

![](http://image.moguit.cn/1556797681396.png)

然后执行下面的代码，使用configure命令创建makeFile文件

    ./configure \
    --prefix=/soft/nginx \
    --pid-path=/var/run/nginx/nginx.pid \
    --lock-path=/var/lock/nginx.lock \
    --error-log-path=/var/log/nginx/error.log \
    --http-log-path=/var/log/nginx/access.log \
    --with-http_gzip_static_module \
    --http-client-body-temp-path=/var/temp/nginx/client \
    --http-proxy-temp-path=/var/temp/nginx/proxy \
    --http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
    --http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
    --http-scgi-temp-path=/var/temp/nginx/scgi
    

如果小伙伴需要使用Nginx配置https的话，那么这里需要安装SSL模块哦，在上面加上这句话（如果不需要，请忽略）

    ./configure \
    --prefix=/soft/nginx \
    --pid-path=/var/run/nginx/nginx.pid \
    --lock-path=/var/lock/nginx.lock \
    --error-log-path=/var/log/nginx/error.log \
    --http-log-path=/var/log/nginx/access.log \
    --with-http_gzip_static_module \
    --http-client-body-temp-path=/var/temp/nginx/client \
    --http-proxy-temp-path=/var/temp/nginx/proxy \
    --http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
    --http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
    --http-scgi-temp-path=/var/temp/nginx/scgi \
    --with-http_ssl_module 

在安装完上面的模块后，然后nginx.conf这样进行配置

tip：不需要配置 https的小伙伴，下面的代码也不需要修改

        server {
            listen       443 ssl;
            server_name  admin.moguit.cn;
            ssl on;
    
            ssl_certificate /home/ssl/admin/2181043_admin.moguit.cn.pem;
            ssl_certificate_key /home/ssl/admin/2181043_admin.moguit.cn.key;
    
            ssl_session_timeout  5m;
            ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    
            ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
            ssl_prefer_server_ciphers  on;
            root   /home/mogu_blog/vue_mogu_admin/dist;
    
        }
    

注意：启动nginx之前，上边将临时文件目录指定为/var/temp/nginx，需要在/var下创建temp及nginx目录

    mkdir /var/temp/nginx/client -p

然后执行make命令已经编译和安装

    #编译
    make
    #安装
    make install
    

启动Nginx
-------

进入 /soft/nginx/sbin目录下，使用下面命令进行启动

     ./nginx

我们在查看端口号，发现nginx已经成功启动了

![](http://image.moguit.cn/1556797881757.png)

nginx常用命令
---------

    启动nginx
    ./nginx
    # 关闭nginx
    ./nginx -s stop
    #退出nginx
    ./nginx -s quit
    # 重启nginx（重启用户基本感觉不到）
    ./nginx -s reload