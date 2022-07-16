

它是一款基于文字界面的邮件客户端，非常小巧，但功能强大，可以用它来读写，回复保存和删除你的邮件，能在linux 命令行模式下收发邮件附件。

> 官网：http://www.mutt.org

安装邮件发送命令

```bash
yum -y install sendmail
# 需要安装sendmail并开启防火墙的25端口，如果你需要收邮件110端口也要开
yum -y install mutt

# 寻找 Muttrc 所在目录
find / -name Muttrc 

# 拷贝到root下
cp /etc/Muttrc /root/.muttrc
```



发送邮件

```bash
mutt moxi0624@gmail.com -s "mogu_blog备份脚本" -a mogu_blog_business.sql 
```





```bash
# 安装git
yum install git

# 创建一个README文件
echo "# MoguSQLBak" >> README.md

# 将全部信息添加到暂存区
git add *

#关联远程仓库
git remote add origin https://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@github.com/moxi624/MoguSQLBak.git

# 设置用户信息
git config --global user.email "xzx19950624@qq.com"
git config --global user.name "moxi624"

# 提交
git commit -m "first commit"

# push到远程层库
git push -u origin main
```

此时，即可看到我们的备份脚本已经成功被成功上传上来了

![image-20211201092929686](images/image-20211201092929686.png)





https://my.oschina.net/u/4005872/blog/3035997