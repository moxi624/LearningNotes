前言
--

今天有个热心的小伙伴成功连接了蘑菇博客的数据库，然后增加管理员账号，在蘑菇博客后台转悠了一圈，最后进群和我反馈了这个问题，在这里首先感谢这位小伙伴的不删库之恩~ 

问题原因
----

首先问题出现的原因是因为远程登录的密码使用的默认密码而造成的，也就是说使用 mysql -u root -p的密码

![](http://image.moguit.cn/3eb7ca28be7a4cae9ef3fb9632f7d532)

和 sqlyog连接的密码不一样，但是都能够成功连接上服务器

![](http://image.moguit.cn/c1baf8e7362d4a58bbaf28cc661ed4ad)

解决方案
----

首先这个问题出现的原因，是因为远程连接的密码 和 本地连接的密码，我只改了一个而导致的

    # 登录mysql
    mysql -u root -p
    
    # 使用mysql数据库
    use mysql
    
    # 查询mysql用户
    select user, host from mysql.user;

我们能够发现有4个用户

![](http://image.moguit.cn/3dacdadbcbbb4abaa467130fdc0e12dc)

其中 host为%的表示是远程连接用户，而localhost是本地用户

我们要做的是删除%，也就是远程连接用户

    # 方法1
    delete from mysql.user  where user = 'root' and host = '%';
    
    # 方法2
    drop user 'root'@'%';

或者是修改%的密码

    # 修改密码
    GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'IDENTIFIED BY 'your_mysql_password' WITH GRANT OPTION;
    
    # 刷新
    flush privileges;

也就是说，之前修改密码的时候，只修改了localhost的，而没有修改%的，而导致这个问题的出现，最后再次感谢那位热心的小伙伴发现这个问题~