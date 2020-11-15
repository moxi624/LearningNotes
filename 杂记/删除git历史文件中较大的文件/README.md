# 删除git历史文件中较大的文件

## 前言

这阵子在给 [学习笔记](https://gitee.com/moxi159753/LearningNotes) 项目提交请求的时候，突然出现了提交错误的清空，因为我是通过 git remote命令，设置了Github和Gitee同时提交，文件在提交到Gitee中是成功的，但是在Github却提示，提交失败，原因是因为Github的仓库大小不能大于500M，而我这次提交已经达到了540M，但是因为我现在提交的都是一些小文件，因此不可能一下就超过了。

## 解决方法

后面通过排查，发现是我很久之前提交了几个 zip压缩包，而这些压缩包的大小已经达到了90M，直接就接近使用很多的免费空间，因此就想着移除那些之前提交的大文件

首先我们需要找出 10个 最大文件的 id列表

```bash
git verify-pack -v .git/objects/pack/pack-*.idx | sort -k 3 -g | tail -10
```

然后通过文件的ID去查询我们的文件路径

> 这里假设我们的文件ID是：acc1f9dcef1004355d2a595d45808e99f100dc43

```bash
git rev-list --objects --all | grep acc1f9dcef1004355d2a595d45808e99f100dc43
```

然后移除我们的大文件

> 这里假设我们的大文件是：app/bigfile.dat

```BASH
git log --pretty=oneline --branches -- app/bigfile.dat
```

最后删除和这个文件相关的历史记录

> 这里删除的文件名是：app/bigfile.dat

```bash
git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch --ignore-unmatch app/bigfile.dat' --prune-empty --tag-name-filter cat -- --all
```

然后强制push

```bash
git push --force
```

最后清空本地缓存

```bash
rm -Rf .git/refs/original
rm -Rf .git/logs/
git gc
git prune
```

