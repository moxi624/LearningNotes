## Erlang与RabbitMQ卸载清理

windows下Erlang与RabbitMQ重新安装时，由于卸载不干净导致各类错误

### Erlang与RabbitMQ卸载

先卸载RabbitMQ，后卸载Erlang

RabbitMQ卸载，选择uninstall.exe进行卸载

![image-20220302213432471](images\image-20220302213432471.png)

Erlang卸载，进入按照目录选择Uninstall.exe使用管理员权限运行进行卸载，卸载完成后将erl目录删除，如提示删除不了重启机器之后再进行删除。

![image-20220302213447329](images\image-20220302213447329.png)

### RabbitMQ服务移除

1、先停止RabbitMQ服务

![image-20220302213457728](images\image-20220302213457728.png)

2、利用管理员权限进入dos命令窗口，执行 sc delete RabbitMQ，移除服务

3、清除注册表

利用regedit命令进入注册表编辑器。

![image-20220302213504966](images\image-20220302213504966.png)

在此路径HKEY_LOCAL_MACHINE\SOFTWARE\Ericsson\下，将Erlang全部清除。

![image-20220302213510453](images\image-20220302213510453.png)



### 其余残余文件删除

利用everything工具进行文件查找

首先查询RabbitMQ，如下图所示，剩余文件可能会比这多，选择进行清理

![image-20220302214036828](images\image-20220302214036828.png)

其次，清理Erlang，先搜索.erlang.cookie进行删除，再次搜索Erlang进行删除

![image-20220302214137120](images\image-20220302214137120.png)

### 重装系统

按照Erlang，RabbitMQ的安装步骤重新安装。
