# 一键脚本部署k8s以及后续kuboard的安装

### 前情提示

`以前安装k8s集群的时候使用的是k8s官网的教程 使用的镜像源都是国外的 速度慢就不说了 还有一些根本就下载不动 导致安装失败 最后在群里小伙伴(蘑菇博客交流群/@你钉钉响了)的建议下使用一个开源的一键安装k8s的脚本就好了起来了`  
git地址  https://github.com/TimeBye/kubeadm-ha

## 环境准备

`官网的安装说明也很简单但是还有些细节还是没有提到 所以我自己照着官网的教程 补充了一些细节`

### 硬件系统要求

- Master节点：2c2g+
- Worker节点：2c4g+

使用centos7.7安装请按上面配置准备好3台centos,1台作为Master节点,2台Worker节点

本方式为1主2worker的配置

这是我的各个节点的配置

| 主机名     | ip             | 配置 |
| ---------- | -------------- | ---- |
| k8s-master | 192.168.28.80  | 2c8g |
| k8s-master | 192.168.28.128 | 2c8g |
| k8s-master | 192.168.28.89  | 2c8g |

### centos准备

`在安装之前需要准备一些基础的软件环境用于下载一键安装k8s的脚本和编辑配置`

#### centos网络准备

安装时需要连接互联网下载各种软件 所以需要保证每个节点都可以访问外网

```sh
ping baidu.com
```

建议关闭centos的防火墙

```sh
systemctl stop firewalld  && systemctl disable firewalld && systemctl status firewalld 
```

同时需要保证各个节点间可以相互ping通

```sh
ping 其他节点ip
```

#### centos软件准备

用ssh连接到Master节点上安装git

```sh
yum install git -y
```

## 部署k8s前配置

#### 下载部署脚本

在Master节点clone安装脚本 [脚本地址](https://github.com/TimeBye/kubeadm-ha)

```
git clone --depth 1 https://github.com/TimeBye/kubeadm-ha
```

进入到下载的部署脚本的目录

```
cd kubeadm-ha
```

#### 安装 Ansible 运行环境

在master节点安装Ansible环境

```sh
sudo ./install-ansible.sh
```

#### 修改安装的配置文件

由于我是一个master两个node的方式构建的centos所以我们需要修改example/hosts.s-master.ip.ini 文件

```sh
vi example/hosts.s-master.ip.ini 
```

具体要修改的就是ip 和密码 其他的保持默认

我的hosts.s-master.ip.ini 文件预览

```ini
; 将所有节点信息在这里填写
;    第一个字段                  为远程服务器内网IP
;    第二个字段 ansible_port     为节点 sshd 监听端口
;    第三个字段 ansible_user     为节点远程登录用户名
;    第四个字段 ansible_ssh_pass 为节点远程登录用户密码
[all]
192.168.28.80 ansible_port=22 ansible_user="root" ansible_ssh_pass="cheng"
192.168.28.128 ansible_port=22 ansible_user="root" ansible_ssh_pass="cheng"
192.168.28.89 ansible_port=22 ansible_user="root" ansible_ssh_pass="cheng"

; 单 master 节点不需要进行负载均衡，lb节点组留空。
[lb]

; 注意etcd集群必须是1,3,5,7...奇数个节点
[etcd]
192.168.28.80
192.168.28.128
192.168.28.89

[kube-master]
192.168.28.80

[kube-worker]
192.168.28.80
192.168.28.128
192.168.28.89

; 预留组，后续添加master节点使用
[new-master]

; 预留组，后续添加worker节点使用
[new-worker]

; 预留组，后续添加etcd节点使用
[new-etcd]

; 预留组，后续删除worker角色使用
[del-worker]

; 预留组，后续删除master角色使用
[del-master]

; 预留组，后续删除etcd角色使用
[del-etcd]

; 预留组，后续删除节点使用
[del-node]

;-------------------------------------- 以下为基础信息配置 ------------------------------------;
[all:vars]
; 是否跳过节点物理资源校验，Master节点要求2c2g以上，Worker节点要求2c4g以上
skip_verify_node=false
; kubernetes版本
kube_version="1.19.4"
; 负载均衡器
;   有 nginx、openresty、haproxy、envoy  和 slb 可选，默认使用 nginx
;   为什么单 master 集群 apiserver 也使用了负载均衡请参与此讨论： https://github.com/TimeBye/kubeadm-ha/issues/8
lb_mode="nginx"
; 使用负载均衡后集群 apiserver ip，设置 lb_kube_apiserver_ip 变量，则启用负载均衡器 + keepalived
; lb_kube_apiserver_ip="192.168.56.15"
; 使用负载均衡后集群 apiserver port
lb_kube_apiserver_port="8443"

; 网段选择：pod 和 service 的网段不能与服务器网段重叠，
; 若有重叠请配置 `kube_pod_subnet` 和 `kube_service_subnet` 变量设置 pod 和 service 的网段，示例参考：
;    如果服务器网段为：10.0.0.1/8
;       pod 网段可设置为：192.168.0.0/18
;       service 网段可设置为 192.168.64.0/18
;    如果服务器网段为：172.16.0.1/12
;       pod 网段可设置为：10.244.0.0/18
;       service 网段可设置为 10.244.64.0/18
;    如果服务器网段为：192.168.0.1/16
;       pod 网段可设置为：10.244.0.0/18
;       service 网段可设置为 10.244.64.0/18
; 集群pod ip段，默认掩码位 18 即 16384 个ip
kube_pod_subnet="10.244.0.0/18"
; 集群service ip段
kube_service_subnet="10.244.64.0/18"
; 分配给节点的 pod 子网掩码位，默认为 24 即 256 个ip，故使用这些默认值可以纳管 16384/256=64 个节点。
kube_network_node_prefix="24"

; node节点最大 pod 数。数量与分配给节点的 pod 子网有关，ip 数应大于 pod 数。
; https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr
kube_max_pods="110"

; 集群网络插件，目前支持flannel,calico
network_plugin="calico"

; 若服务器磁盘分为系统盘与数据盘，请修改以下路径至数据盘自定义的目录。
; Kubelet 根目录
kubelet_root_dir="/var/lib/kubelet"
; docker容器存储目录
docker_storage_dir="/var/lib/docker"
; Etcd 数据根目录
etcd_data_dir="/var/lib/etcd"

```

#### 升级内核

修改完配置文件后建议升级内核

```sh
ansible-playbook -i example/hosts.s-master.ip.ini 00-kernel.yml
```

内核升级完毕后重启所有节点 在master node1 node2上执行

```sh
reboot
```

## 开始部署k8s

等待所有的节点重启完成后进入脚本目录

```
cd kubeadm-ha
```

### 执行一键部署命令

```sh
ansible-playbook -i example/hosts.s-master.ip.ini 90-init-cluster.yml
```

### 查看节点运行情况

```sh
kubectl get nodes
```

等待所有节点ready 即为创建成功	

```
NAME             STATUS   ROLES                AGE     VERSION
192.168.28.128   Ready    etcd,worker          2m57s   v1.19.4
192.168.28.80    Ready    etcd,master,worker   3m29s   v1.19.4
192.168.28.89    Ready    etcd,worker          2m57s   v1.19.4
```

## 部署kuboard

### 切换docker的镜像加速为阿里云

在所有节点执行

```sh
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://uz6p4blc.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload && systemctl restart docker
```

### 安装 Kuboard

`在master节点执行`

```sh
kubectl apply -f https://kuboard.cn/install-script/kuboard.yaml
kubectl apply -f https://addons.kuboard.cn/metrics-server/0.3.7/metrics-server.yaml

```

查看 Kuboard 运行状态

```
kubectl get pods -l k8s.kuboard.cn/name=kuboard -n kube-system

```

输出结果如下所示

```
NAME                       READY   STATUS    RESTARTS   AGE
kuboard-74c645f5df-cmrbc   1/1     Running   0          80s

```

### 访问Kuboard

Kuboard Service 使用了 NodePort 的方式暴露服务，NodePort 为 32567；您可以按如下方式访问 Kuboard。

```
http://任意一个Worker节点的IP地址:32567/
```

我的

```
http://192.168.28.128:32567/
```

第一次访问需要输入token 我们获取一下

### 获取token 

`在master节点执行`

```sh
echo $(kubectl -n kube-system get secret $(kubectl -n kube-system get secret | grep kuboard-user | awk '{print $1}') -o go-template='{{.data.token}}' | base64 -d)

```

我获取到的token (全部复制完全)

```
eyJhbGciOiJSUzI1NiIsImtpZCI6ImY1eUZlc0RwUlZha0E3LWZhWXUzUGljNDM3SE0zU0Q4dzd5R3JTdXM2WEUifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJvYXJkLXVzZXItdG9rZW4tMmJsamsiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoia3Vib2FyZC11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiYzhlZDRmNDktNzM0Zi00MjU1LTljODUtMWI5MGI4MzU4ZWMzIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmt1Ym9hcmQtdXNlciJ9.MujbwGnkL_qa3H14oKDT1zZ5Fzt16pWoaY52nT7fV5B2nNIRsB3Esd18S8ztHUJZLRGxAhBwu-utToi2YBb8pH9RfIeSXMezFZ6QhBbp0n5xYWeYETQYKJmes2FRcW-6jrbpvXlfUuPXqsbRX8qrnmSVEbcAms22CSSVhUbTz1kz8C7b1C4lpSGGuvdpNxgslNFZTFrcImpelpGSaIGEMUk1qdjKMROw8bV83pga4Y41Y6rJYE3hdnCkUA8w2SZOYuF2kT1DuZuKq3A53iLsvJ6Ps-gpli2HcoiB0NkeI_fJORXmYfcj5N2Csw6uGUDiBOr1T4Dto-i8SaApqmdcXg
```

将token输入到kuboard

最后即可进入kuboard的dashboard界面