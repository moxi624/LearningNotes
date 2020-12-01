# vue打包后的dist文件如何启动测试

## 前言

我们vue项目进行webpack打包操作 `npm run build` 后，会生成dist静态文件夹，这个文件我们是不能直接运行的，如果我们想要进行测试的话，一般是有两种方式

## nginx静态代理

首先我们可以通过nginx做静态资源映射，我们首先修改nginx的配置 nginx.conf

```bash
# 蘑菇博客WEB
server {
	listen       9527;
	server_name  localhost;	
	add_header Access-Control-Allow-Origin *;
	add_header Access-Control-Allow-Methods *;
	add_header Access-Control-Allow-Headers *;
	if ($request_method = 'OPTIONS') {
	 return 204;
	}
	location / {
		root   D:\mogu_blog\data\vue_mogu_web\dist;
		index  index.html index.htm;
	}
}
```

然后添加录下内容，最后把我们的 文件夹放在如下目录

```bash
D:\mogu_blog\data\vue_mogu_web\dist
```

最后启动nginx，然后访问如下ip地址

```bash
http://localhost:9527
```

即可看到我们的页面

## server方式启动

同时npm还提供了 `serve` 命令，来给我们进行测试，这种方式比nginx更加方便，可以直接进行测试，但是存在的问题就是，没有办法指定端口号，而只能开启 5000端口

```bash
# 安装 serve模块
npm install -g serve
# 启动
serve -s dist
```

启动后，访问如下地址

```bash
http://localhost:5000
```

