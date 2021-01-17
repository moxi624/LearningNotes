# 使用kubeadm-ha脚本一键安装K8S

## 前情提示

以前安装k8s集群的时候使用的是k8s官网的教程 使用的镜像源都是国外的 速度慢就不说了 还有一些根本就下载不动 导致安装失败 最后在群里小伙伴(蘑菇博客交流群/@你钉钉响了)的建议下使用一个开源的一键安装k8s的脚本就好了起来了
Github地址：https://github.com/TimeBye/kubeadm-ha

## 环境准备

官网的安装说明也很简单但是还有些细节还是没有提到 所以我自己照着官网的教程 补充了一些细节

### 硬件系统要求

- Master节点：2C4G +
- Worker节点：2C4G +

使用centos7.7安装请按上面配置准备好3台centos,1台作为Master节点,2台Worker节点

本方式为1主2worker的配置

这是我的各个节点的配置

| 主机名     | ip              | 配置 |
| ---------- | --------------- | ---- |
| k8s-master | 192.168.177.130 | 2C4G |
| k8s-node1  | 192.168.177.131 | 2C2G |
| k8s-node2  | 192.168.177.132 | 2C2G |

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

#### CentOS软件准备

用 **ssh** 连接到 **Master** 节点上安装 Git

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
192.168.177.130 ansible_port=22 ansible_user="root" ansible_ssh_pass="moxi"
192.168.177.131 ansible_port=22 ansible_user="root" ansible_ssh_pass="moxi"
192.168.177.132 ansible_port=22 ansible_user="root" ansible_ssh_pass="moxi"

; 单 master 节点不需要进行负载均衡，lb节点组留空。
[lb]

; 注意etcd集群必须是1,3,5,7...奇数个节点
[etcd]
192.168.177.130
192.168.177.131
192.168.177.132

[kube-master]
192.168.177.130

[kube-worker]
192.168.177.130
192.168.177.131
192.168.177.132

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
skip_verify_node=true
; kubernetes版本
kube_version="1.18.14"
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
192.168.28.128   Ready    etcd,worker          2m57s   v1.18.14
192.168.28.80    Ready    etcd,master,worker   3m29s   v1.18.14
192.168.28.89    Ready    etcd,worker          2m57s   v1.18.14
```

### 集群重置

如果部署失败了，想要重置整个集群【包括数据】，执行下面脚本

```bash
ansible-playbook -i example/hosts.s-master.ip.ini 99-reset-cluster.yml
```

## 部署kuboard

### 安装Docker

因为我们需要拉取镜像，所以需要在服务器提前安装好Docker，首先配置一下Docker的阿里yum源

```bash
cat >/etc/yum.repos.d/docker.repo<<EOF
[docker-ce-edge]
name=Docker CE Edge - \$basearch
baseurl=https://mirrors.aliyun.com/docker-ce/linux/centos/7/\$basearch/edge
enabled=1
gpgcheck=1
gpgkey=https://mirrors.aliyun.com/docker-ce/linux/centos/gpg
EOF
```

然后yum方式安装docker

```bash
# yum安装
yum -y install docker-ce
# 查看docker版本
docker --version  
# 开机自启
systemctl enable docker
# 启动docker
systemctl start docker
```

配置docker的镜像源

```bash
cat >> /etc/docker/daemon.json << EOF
{
  "registry-mirrors": ["https://b9pmyelo.mirror.aliyuncs.com"]
}
EOF
```

然后重启docker

```bash
systemctl restart docker
```

## 安装Kuboard【可选】

### 简介

**Kuboard** 是一款免费的 **Kubernetes** 图形化管理工具，力图帮助用户快速在 **Kubernetes** 上落地微服务。

Kuboard文档：https://kuboard.cn/

### 安装

`在master节点执行`

```sh
kubectl apply -f https://kuboard.cn/install-script/kuboard.yaml
kubectl apply -f https://addons.kuboard.cn/metrics-server/0.3.7/metrics-server.yaml
```

查看 Kuboard 运行状态

```
kubectl get pods -l k8s.kuboard.cn/name=kuboard -n kube-system

```

输出结果如下所示。注意：如果是 `ContainerCreating` 那么需要等待一会

```
NAME                       READY   STATUS    RESTARTS   AGE
kuboard-74c645f5df-cmrbc   1/1     Running   0          80s

```

### 访问Kuboard

Kuboard Service 使用了 NodePort 的方式暴露服务，NodePort 为 32567；您可以按如下方式访问 Kuboard。

```bash
# 格式
http://任意一个Worker节点的IP地址:32567/

# 例如，我的访问地址如下所示
http://192.168.177.130:32567/
```

页面如下所示：

![image-20210107211525789](images/image-20210107211525789.png)

第一次访问需要输入token 我们获取一下 **token**， `在master节点执行`

```sh
echo $(kubectl -n kube-system get secret $(kubectl -n kube-system get secret | grep kuboard-user | awk '{print $1}') -o go-template='{{.data.token}}' | base64 -d)

```

获取到的 **token**，然后粘贴到框中，我的 **token** 格式如下：

```bash
eyJhbGciOiJSUzI1NiIsImtpZCI6ImY1eUZlc0RwUlZha0E3LWZhWXUzUGljNDM3SE0zU0Q4dzd5R3JTdXM2WEUifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJvYXJkLXVzZXItdG9rZW4tMmJsamsiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoia3Vib2FyZC11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiYzhlZDRmNDktNzM0Zi00MjU1LTljODUtMWI5MGI4MzU4ZWMzIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50Omt1YmUtc3lzdGVtOmt1Ym9hcmQtdXNlciJ9.MujbwGnkL_qa3H14oKDT1zZ5Fzt16pWoaY52nT7fV5B2nNIRsB3Esd18S8ztHUJZLRGxAhBwu-utToi2YBb8pH9RfIeSXMezFZ6QhBbp0n5xYWeYETQYKJmes2FRcW-6jrbpvXlfUuPXqsbRX8qrnmSVEbcAms22CSSVhUbTz1kz8C7b1C4lpSGGuvdpNxgslNFZTFrcImpelpGSaIGEMUk1qdjKMROw8bV83pga4Y41Y6rJYE3hdnCkUA8w2SZOYuF2kT1DuZuKq3A53iLsvJ6Ps-gpli2HcoiB0NkeI_fJORXmYfcj5N2Csw6uGUDiBOr1T4Dto-i8SaApqmdcXg
```

最后即可进入 **kuboard** 的 **dashboard** 界面

![image-20210107211713726](images/image-20210107211713726.png)

### 卸载Kuboard

当我们 **kuboard** 不想使用的时候，我们就可以直接卸载

```bash
kubectl delete -f https://kuboard.cn/install-script/kuboard.yaml
kubectl delete -f https://addons.kuboard.cn/metrics-server/0.3.7/metrics-server.yaml
```

## Rancher部署【可选】

> kuboard和rancher建议部署其中一个

### helm安装

使用helm部署rancher会方便很多，所以需要安装helm

```bash
curl -O http://rancher-mirror.cnrancher.com/helm/v3.2.4/helm-v3.2.4-linux-amd64.tar.gz
tar -zxvf helm-v3.2.4-linux-amd64.tar.gz
mv linux-amd64/helm /usr/local/bin
```

#### 验证

```bash
helm version
```

输入以下内容说明helm安装成功

```bash
version.BuildInfo{Version:"v3.2.4", GitCommit:"0ad800ef43d3b826f31a5ad8dfbb4fe05d143688", GitTreeState:"clean", GoVersion:"go1.13.12"}
```

### 添加rancher chart仓库

```bash
helm repo add rancher-stable http://rancher-mirror.oss-cn-beijing.aliyuncs.com/server-charts/stable
helm repo update
```

### 安装rancher

```bash
helm install rancher rancher-stable/rancher \
 --create-namespace	\
 --namespace cattle-system \
 --set hostname=rancher.local.com
```

##### 等待 Rancher 运行：

```bash
kubectl -n cattle-system rollout status deploy/rancher
```

输出信息：

```bash
Waiting for deployment "rancher" rollout to finish: 0 of 3 updated replicas are available...
deployment "rancher" successfully rolled out
```



