# Docker操作系统之Alpine

## 前言

这阵子我发现只要带着alpine前缀的镜像，相比于其它的镜像，体积都相对较小，例如下面这些

```bash
java:alpine
nginx:alpine
```

后面通过了解，发现了其实这些java镜像，或者nginx镜像都依赖于某个linux操作系统，我们常见的操作系统有  ubuntu、centos、debian。而这个alpine其实也是一个新的操作系统。但是它比其它的操作系统而言，体积更小，所以在他们的基础之上做的镜像，体积也会更小，常见的linux操作系统体积大小，如下所示

```bash
REPOSITORY          TAG           IMAGE ID          VIRTUAL SIZE
alpine              latest        4e38e38c8ce0      4.799 MB
debian              latest        4d6ce913b130      84.98 MB
ubuntu              latest        b39b81afc8ca      188.3 MB
centos              latest        8efe422e6104      210 MB
```

我们也就发现了 alpine的大小远远小于 其它的操作系统，因此制作出来的镜像大小也远远小于其它的

## Alpine操作系统

Alpine操作系统主要是面向安全的轻量级Linux发行版，它和其它的发行版不同之处在于，Alpine采用了musllibc 和 busybox以减少系统体积和运行时资源消耗，但功能上比busybox又完善的多 ，因此越来越得到开源社区的青睐。在保持瘦身的同时，Alpine还提供了自己的包管理工具【CentOS是yum，ubuntu是 apt-get】，可以通过 [Alpine包查询网站](https://pkgs.alpinelinux.org/packages) ，来进行查看，例如下图所示，搜索自己需要安装的包进行查看

![image-20201206093447554](images/image-20201206093447554.png)

然后通过 apk add vim 来进行安装即可。

Alpine Docker镜像也继承了Alpine Linux发行版的优势，相比于其它的Docker镜像，它的容量体积非常小，仅仅只有5MB，我们通过打开 DockerHub中 [Alpine的官网](https://registry.hub.docker.com/_/alpine)

![image-20201206093922397](images/image-20201206093922397.png)

可以发现，它提供了只有5MB的系统镜像可供我们进行下载使用

```bash
# 下载alpine镜像
docker pull alpine
```

同时，它还列举了一个例子 【通过制作一个mysql镜像】

![image-20201206094302242](images/image-20201206094302242.png)

使用Alpine 和 Ubuntu 制作出来的镜像一个是 36.8MB 一个是 145MB，相差4倍多

目前Docker官方已经开始推荐Alpine替代之前的Ubuntu作为基础镜像环境，这样所带来的好处包括：镜像下载速度更快、镜像安全性提高、主机之间的切换更方便、占用内存更少等特点。

## 使用Alpine镜像

我们通过下面命令，能够非常快的运行一个Alpine容器【本地不存在会去官方下载】，并输出 hello alpine

```bash
docker run alpine echo "hello alpine"
```

![image-20201206094817959](images/image-20201206094817959.png)

## 迁移至Alpine

目前，大部分Docker官方镜像，都已经提供了Alpine版本镜像的支持，我们非常容易镜像迁移

例如，通过Nginx的 [官方DockerHub地址](https://registry.hub.docker.com/_/nginx)，我们可以看到，也专门有 alpine稳定版本

![image-20201206095346672](images/image-20201206095346672.png)

还有其它一些官方镜像也都提供了alpine版本，我们可以在

```bash
ubuntu/debian -> alpine
python:2.7 -> python:2.7-alpine
ruby:2.3 -> ruby:2.3-alpine
```

另外，如果我们想要在alpine的基础上进行一些软件的安装，可以使用下面的命令

```bash
apk add --no-cache <package>
```

## 思考

本段来源于V2EX上的 [提问](https://www.v2ex.com/t/581888)

上面其实我们已经提到了很多关于alpine的优势，比如体积小，并且很多官方的Docker镜像都提供了基于alpine的版本。那如果alpine版本没有任何坑的话，从体积小，并且能满足使用正常使用的话，这相比于CentOS、Ubuntu和Debian的镜像，就拥有非常大的优势了，那么以后这些发行版的进行也就没有存在的必要，真实是这样的么？

下面是针对上述问题，最后的总结

- 首先在基于alpine的操作系统上编写Dockerfile制作新镜像，并不会像其他操作系统一样方便，甚至会出现alpine中不存在的情况。
- 虽然每个单个的基于alpine的软件镜像是明显少于其他操作系统，但是如果多个镜像【包括每个镜像运行的多个容器】，使用 了同一个基础镜像，是不会花费额外的空间【归结于docker的Overlay文件系统】
- 有些软件没办法在Alpine中运行，因为alpine不像其它发行版那样使用CGLIBC【MySQL没有Alpine镜像】

## 参考

- `Alpine` 官网：http://alpinelinux.org/
- `Alpine` 官方仓库：https://github.com/alpinelinux
- `Alpine` 官方镜像：https://hub.docker.com/_/alpine/
- `Alpine` 官方镜像仓库：https://github.com/gliderlabs/docker-alpine

