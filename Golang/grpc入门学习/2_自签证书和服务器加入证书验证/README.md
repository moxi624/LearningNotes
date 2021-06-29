## 前言

在真实的环境下，我们一般会使用证书来确保通信的安全

如果是window环境，使用下面地址，进行自签证书

```bash
http://slproweb.com/products/Win32OpenSSL.html
```

![image-20210621221350145](images/image-20210621221350145.png)

下载完成后，开始进行证书的生成

## 生成步骤

首先进入 bin 目录下，执行 openssl

然后执行 genrsa-des3 -out server.key 2048  (会生成 servver.key 私钥文件)

创建证书请求：