# 一键脚本部署k8s

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

| 主机名 | ip          | 配置  | 角色                  |
| ------ | ----------- | ----- | --------------------- |
| node1  | 10.168.1.11 | 4c16g | etcd,master,worker    |
| node2  | 10.168.1.12 | 4c16g | etcd,lb,master,worker |
| node3  | 10.168.1.13 | 4c16g | etcd,lb,master,worker |
| node4  | 10.168.1.14 | 4c16g | worker                |

#### 角色说明：

master:  k8s管理节点

worker：k8s工作节点

etcd：k8s数据存储etcd部署节点

lb: 负载均衡节点，基于负载均衡器 + keepalived实现，可实现一台lb主节点挂掉后，ip自动漂移到其他节点

#### 其余准备工作

除以上四台主机使用的ip外，另外准备一个ip，供vip漂移使用，本人使用的ip是：`10.168.1.15`

---

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

由于我部署的集群是三个master节点，所以我们需要修改`example/hosts.m-master.hostname.ini` 文件

```sh
vi example/hosts.m-master.hostname.ini
```

修改的内容如下：

```ini
; 将所有节点的信息在这里填写
;    第一个字段                  为 kubernetes 节点 nodeName，注意必须由小写字母、数字，“-”或“.”组成，并且必须以小写字母或数字开头和结尾
;    第二个字段 ansible_host     为节点内网IP
;    第三个字段 ansible_port     为节点 sshd 监听端口
;    第四个字段 ansible_user     为节点远程登录用户名
;    第五个字段 ansible_ssh_pass 为节点远程登录用户密码
[all]
node1 ansible_host=10.168.1.11 ansible_port=22 ansible_user="root" ansible_ssh_pass="111111"
node2 ansible_host=10.168.1.12 ansible_port=22 ansible_user="root" ansible_ssh_pass="111111"
node3 ansible_host=10.168.1.13 ansible_port=22 ansible_user="root" ansible_ssh_pass="111111"
node4 ansible_host=10.168.1.14 ansible_port=22 ansible_user="root" ansible_ssh_pass="111111"
; lb节点，这里使用node2和node3
[lb]
node2
node3
; 注意etcd集群必须是1,3,5,7...奇数个节点
[etcd]
node1
node2
node3
; 集群master节点
[kube-master]
node1
node2
node3
; 集群worker节点，一个节点可有多个角色
[kube-worker]
node1
node2
node3
node4

; 负载均衡后的apiserver ip和端口，部署后可以看到kubeconfig文件中server地址为：https://10.168.1.15:8443
; 使用负载均衡后集群 apiserver ip，设置 lb_kube_apiserver_ip 变量，则启用负载均衡器 + keepalived
lb_kube_apiserver_ip="10.168.1.15"
; 使用负载均衡后集群 apiserver port
lb_kube_apiserver_port="8443"
```



`hosts.m-master.hostname.ini`完整内容如下

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

#### 修改`variables.yaml`文件

##### 该文件说明：

高级配置，**注意：** 如果安装集群时使用高级配置则以后所有操作都需将 `-e @example/variables.yaml` 参数添加在 `ansible-playbook` 命令中

> 1. 本项目所有可配置项都在 `example/variables.yaml` 文件中体现，需自定义配置时删除配置项前注释符即可。
> 2. 若 `example/hosts.m-master.ip.ini` 文件中与 `example/variables.yaml` 变量值冲突时， `example/variables.yaml` 文件中的变量值优先级最高。

##### 修改内容：

```yaml
# 国内镜像加速(若不需要加速，请将值删去，键保留)
docker_mirror:
  - "https://lj88h5zm.mirror.aliyuncs.com"

# lb已在hosts.m-master.hostname.ini中配置，可以注释掉
# lb_mode: nginx
# 使用负载均衡后集群 apiserver ip
# lb_kube_apiserver_ip: "10.168.1.15"
# lb_kube_apiserver_port: 8443
# 是否启用kubernetes-dashboard，默认为false，本人使用其他监控软件，所以修改为false
kubernetesui_dashboard_enabled: false
# 是否启用cert-manager，默认为false，cert-manager用于管理k8s集群中的证书，安装rancher时需要，修改为true
cert_manager_enabled: true
```

##### 完整内容如下：

```yaml
# ------------------------ #
# 基础信息配置
# ------------------------ #
# 是否跳过节点物理资源校验
skip_verify_node: false

  # 安装模式
  # 在线 online
  # 离线 offline
# 应用于可能没有公网的环境
install_mode: online

# 代理设置，若节点不能直接访问公网，可使用下面变量设置代理。
http_proxy:
https_proxy:
no_proxy: 192.168.0.0/16,10.0.0.0/8,172.16.0.0/12,127.0.0.1,localhost

# 节点时区
timezone: Asia/Shanghai

# CentOS yum源仓库
# 基础软件源
base_yum_repo: http://mirrors.aliyun.com/centos/$releasever/os/$basearch/
# epel软件源（ CeontOS8默认使用：https://mirrors.aliyun.com/epel/$releasever/Everything/$basearch ）
epel_yum_repo: http://mirrors.aliyun.com/epel/$releasever/$basearch
# docker-ce源 (CentOS可使用：https://mirrors.aliyun.com/docker-ce/linux/centos/$releasever/$basearch/stable )
docker_yum_repo: https://mirrors.aliyun.com/docker-ce/linux/centos/{{ ansible_distribution_major_version }}/$basearch/stable
# kubernetes源
kubernetes_yum_repo: https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-{{ ansible_architecture }}/

# Debian apt源仓库
# 基础软件源
base_apt_repo: deb http://mirrors.aliyun.com/{{ host_distribution | lower }}/ {{ host_distribution_release }} main restricted universe multiverse
# docker-ce源
docker_apt_repo: "deb [arch={{ host_architecture }}] https://mirrors.aliyun.com/docker-ce/linux/{{ host_distribution | lower }} {{ host_distribution_release }} stable"
# kubernetes源
kubernetes_apt_repo: deb https://mirrors.aliyun.com/kubernetes/apt kubernetes-xenial main

# ------------------------ #
# Docker 相关参数配置
# ------------------------ #
# Docker版本
docker_version: 19.03.13
containerd_version: 1.3.7-1

# 国内镜像加速(若不需要加速，请将值删去，键保留)
docker_mirror:
  - "https://lj88h5zm.mirror.aliyuncs.com"
  - "https://reg-mirror.qiniu.com"
  - "https://hub-mirror.c.163.com"
  - "https://docker.mirrors.ustc.edu.cn"

# 信任的不安全镜像库地址，默认为 Pod 和 Service 网段
docker_insecure_registries:
  - "{{ kube_pod_subnet }}"
  - "{{ kube_service_subnet }}"

# docker日志相关
docker_log_driver: "json-file"
docker_log_level: "warn"
docker_log_max_size: "10m"
docker_log_max_file: 3

# docker容器存储目录
docker_storage_dir: "/var/lib/docker"

# 并行镜像下载数量
docker_max_concurrent_downloads: 10

# ------------------------ #
# load-balancer 相关参数配置
# ------------------------ #
# 私有云：
#    VIP 负载模式：
#       也就是负载均衡器 + keepalived 模式，比如常用的 haproxy + keepalived。
#       本脚本中负载均衡器有 nginx、openresty、haproxy、envoy 可供选择，设置 lb_mode 即可进行任意切换。
#       设置 lb_kube_apiserver_ip 即表示启用 keepalived，请先与服务器提供部门协商保留一个IP作为 lb_kube_apiserver_ip，
#       一般 lb 节点组中有两个节点就够了，lb节点组中第一个节点为 keepalived 的 master 节点，剩下的都为 backed 节点。
#
#    节点本地负载模式：
#       只启动负载均衡器，不启用 keepalived（即不设置 lb_kube_apiserver_ip），
#       此时 kubelet 链接 apiserver 地址为 127.0.0.1:lb_kube_apiserver_port。
#       使用此模式时请将 lb 节点组置空。
#
# 公有云：
#    不推荐使用 slb 模式，建议直接使用节点本地负载模式。
#    若使用 slb 模式，请先使用节点本地负载模式进行部署，
#    部署成功后再切换至 slb 模式：
#       将 lb_mode 修改为 slb，将 lb_kube_apiserver_ip 设置为购买到的 slb 内网ip，
#       修改 lb_kube_apiserver_port 为 slb 监听端口。
#    再次运行初始化集群脚本即可切换至 slb 模式。
lb_mode: nginx

# 使用负载均衡后集群 apiserver ip
lb_kube_apiserver_ip: "10.168.1.15"

# 使用负载均衡后集群 apiserver port
lb_kube_apiserver_port: 8443

# 负载均衡器健康检查端口
lb_kube_apiserver_healthcheck_port: 8081

# 启用 ingress NodePort服务的负载均衡 (true/false)
enabel_ingress_nodeport_lb: true
# 启用 ingress tls NodePort服务的负载均衡 (true/false)
enabel_ingress_tls_nodeport_lb: true

# 使用openresty进行apiserver负载时使用的openresty镜像
lb_openresty_image: registry.aliyuncs.com/kubeadm-ha/openresty_openresty:1.17.8.2-alpine

# 使用nginx进行apiserver负载时使用的nginx镜像
lb_nginx_image: registry.aliyuncs.com/kubeadm-ha/nginx:1.18-alpine

# 使用haproxy进行apiserver负载时使用的haproxy镜像
lb_haproxy_image: registry.aliyuncs.com/kubeadm-ha/haproxy:2.1-alpine
# haproxy监控绑定端口
lb_haproxy_stats_bind_address: 9099
# haproxy监控访问路径
lb_haproxy_stats_uri: "/stats"
# haproxy监控自动刷新时间（秒）
lb_haproxy_stats_refresh: 10
# haproxy监控用户名
lb_haproxy_stats_user: "admin"
# haproxy监控用户密码
lb_haproxy_stats_password: "admin"
# haproxy负载均衡算法，常见如下：
# "roundrobin": 基于服务器权重的轮询
# "leastconn": 基于服务器最小连接数
# "source": 基于请求源IP地址
# "uri": 基于请求的URI
lb_haproxy_balance_alg: "leastconn"

# 使用haproxy进行apiserver负载时使用的haproxy镜像
lb_envoy_image: registry.aliyuncs.com/kubeadm-ha/envoyproxy_envoy:v1.16.0
lb_envoy_admin_address_port: 9099

# 使用 vip 负载时使用的 keepalived 镜像
lb_keepalived_image: registry.aliyuncs.com/kubeadm-ha/osixia_keepalived:2.0.20
# keepalived auth_type 的 password
lb_keepalived_password: "d0cker"
# 区分多个 instance 的 VRRP 组播，同网段不能重复，取值在0-255之间
lb_keepalived_router_id: 51

# ------------------------ #
# Etcd 相关参数配置
# ------------------------ #

# Etcd证书过期时间（天）
etcd_certs_expired: 3650
# Etcd根证书过期时间（天）
etcd_ca_certs_expired: 36500
# Etcd使用的镜像
etcd_image: registry.aliyuncs.com/kubeadm-ha/etcd:3.4.13-0
# Etcd 数据根目录
etcd_data_dir: "/var/lib/etcd"
# Etcd 每日备份时间，默认3，即凌晨3点，取值范围0-23
etcd_backup_hour: "3"
# Etcd 每日备份文件保留时长，默认7天
etcd_backup_expiry: "7"

# ------------------------ #
# kubernetes 相关参数配置
# ------------------------ #

# kubernetes证书过期时间（天）
kube_certs_expired: 3650
# kubernetes根证书过期时间（天）
kube_ca_certs_expired: 36500

# 加入集群token
kubeadm_token: "abcdef.0123456789abcdef"

# k8s 集群 master 节点证书配置，可以添加多个ip和域名（比如增加公网ip和域名）
kube_master_external_ip:
  - "8.8.8.8"
kube_master_external_domain:
  - "kubernetes.io"

# Pod根容器
pod_infra_container_image: registry.aliyuncs.com/kubeadm-ha/pause:3.2

# kubernetes各组件镜像仓库前缀
kube_image_repository: registry.aliyuncs.com/kubeadm-ha

# kubernetes版本
kube_version: 1.19.4

# 集群内部dns域名
kube_dns_domain: cluster.local

# 网段选择：pod 和 service 的网段不能与服务器网段重叠，
# 若有重叠请配置 `kube_pod_subnet` 和 `kube_service_subnet` 变量设置 pod 和 service 的网段，示例参考：
#    如果服务器网段为：10.0.0.1/8
#       pod 网段可设置为：192.168.0.0/18
#       service 网段可设置为 192.168.64.0/18
#    如果服务器网段为：172.16.0.1/12
#       pod 网段可设置为：10.244.0.0/18
#       service 网段可设置为 10.244.64.0/18
#    如果服务器网段为：192.168.0.1/16
#       pod 网段可设置为：10.244.0.0/18
#       service 网段可设置为 10.244.64.0/18
# 集群pod ip段，默认掩码位 18 即 16384 个ip
kube_pod_subnet: 10.244.0.0/18
# 集群service ip段
kube_service_subnet: 10.244.64.0/18
# 分配给节点的 pod 子网掩码位，默认为 24 即 256 个ip，故使用这些默认值可以纳管 16384/256=64 个节点。
kube_network_node_prefix: 24

# node节点最大 pod 数。数量与分配给节点的 pod 子网有关，ip 数应大于 pod 数。
# https://cloud.google.com/kubernetes-engine/docs/how-to/flexible-pod-cidr
kube_max_pods: 110

# NodePort端口范围
kube_service_node_port_range: 30000-32767

# 资源保留相关配置
eviction_hard_imagefs_available: 15%
eviction_hard_memory_available: 100Mi
eviction_hard_nodefs_available: 10%
eviction_hard_nodefs_inodes_free: 5%

# kubernetes组件预留资源
kube_cpu_reserved: 100m
kube_memory_reserved: 256M
kube_ephemeral_storage_reserved: 1G

# 操作系统守护进程预留资源
system_reserved_enabled: true
# 取消注释以覆盖默认值
system_cpu_reserved: 500m
system_memory_reserved: 512M
system_ephemeral_storage_reserved: 10G

# 默认使用kube-proxy的 'iptables' 模式，可选 'ipvs' 模式
kube_proxy_mode: iptables

# Kubelet 根目录
kubelet_root_dir: "/var/lib/kubelet"

## 存入 Etcd 时的 Secret 进行静态加密
# 仅支持: aescbc, secretbox 或 aesgcm
kube_encryption_algorithm: "aescbc"
# 将Secret数据加密存储到etcd中的配置文件，下面加密码由 head -c 32 /dev/urandom | base64 生成
kube_encrypt_token: "GPG4RC0Vyk7+Mz/niQPttxLIeL4HF96oRCcBRyKNpfM="

## 审计相关配置
# 是否启用审计
kubernetes_audit: false
# 保留审计日志最大天数
audit_log_maxage: 30
# 保留审计日志最大个数
audit_log_maxbackups: 10
# 保留审计日志最大容量（MB）
audit_log_maxsize: 100
# 审计日志文件挂载在主机上的目录
audit_log_hostpath: /var/log/kubernetes/audit
# 审计策略配置文件路径
audit_policy_file: /etc/kubernetes/config/apiserver-audit-policy.yaml
# 自定义审计日志规则 (替换默认的审计规则)
audit_policy_custom_rules: |
  - level: None
    users: []
    verbs: []
    resources: []

# 1.10+ admission plugins
kube_apiserver_enable_admission_plugins:
  - NodeRestriction
# - AlwaysPullImages
# - PodSecurityPolicy

# 1.10+ list of disabled admission plugins
kube_apiserver_disable_admission_plugins: [ ]

# kube-controller-manager 标记 kubelet(node) 为不健康的周期
kube_controller_node_monitor_grace_period: 40s
# kube-controller-manager 定期检查 kubelet(node) 状态周期
kube_controller_node_monitor_period: 5s
# kube-controller-manager 判定节点故障，重建 Pod 的超时时间，默认值 5m0s，这里改为了 2m0s
kube_controller_pod_eviction_timeout: 2m0s
# exit 状态的 pod 超过多少会触发 gc，默认值 12500，这里改为了 10
kube_controller_terminated_pod_gc_threshold: 10

## Extra args for k8s components passing by kubeadm
kube_kubeadm_apiserver_extra_args: { }
kube_kubeadm_controller_extra_args: { }
kube_kubeadm_scheduler_extra_args: { }

## Extra control plane host volume mounts
## Example:
#apiserver_extra_volumes:
#  - name: name
#    hostPath: /host/path
#    mountPath: /mount/path
#    readOnly: true
apiserver_extra_volumes: { }
controller_manager_extra_volumes: { }
scheduler_extra_volumes: { }

# ------------------------ #
# 集群插件相关参数配置
# ------------------------ #

# 是否等待插件运行成功
wait_plugins_ready: true

# ------------------------ #
# 集群网络插件相关参数配置
# ------------------------ #

# 是否启用网络组建
network_plugins_enabled: true

# 集群网络插件，目前支持flannel, calico
network_plugin: "calico"

# calico mtu
calico_veth_mtu: 1440
# calico 相关镜像
calico_typha_image: registry.aliyuncs.com/kubeadm-ha/calico_typha:v3.16.5
calico_cni_image: registry.aliyuncs.com/kubeadm-ha/calico_cni:v3.16.5
calico_node_image: registry.aliyuncs.com/kubeadm-ha/calico_node:v3.16.5
calico_kube_controllers_image: registry.aliyuncs.com/kubeadm-ha/calico_kube-controllers:v3.16.5
calico_pod2daemon_flexvol_image: registry.aliyuncs.com/kubeadm-ha/calico_pod2daemon-flexvol:v3.16.5
# 设置 Felix 日志级别(debug, info, warning, error)
calico_felix_log_level: "warning"
# calicoctl image 地址
calicoctl_image: registry.aliyuncs.com/kubeadm-ha/calico_ctl:v3.16.5

# 设置 flannel 后端
# flannel_backend: "host-gw"
flannel_backend: "vxlan"
# flannel 镜像地址
flannel_image: registry.aliyuncs.com/kubeadm-ha/coreos_flannel:v0.13.0

# ------------------------ #
# ingress-controller 相关参数配置
# ------------------------ #

# 是否启用ingress-controller
ingress_controller_enabled: true

# ingress-controller类型(nginx,traefik)
ingress_controller_tpye: nginx
# NodePort svc 保留客户端的源 IP 地址，可选值：Cluster、Local
#   Cluster：不保留客户端的源 IP 地址；
#   Local：保留客户端的源 IP 地址，当设置为 Local 时仅 ingress-controller pod 运行的节点才能提供服务，其余节点不提供服务
# 相关文档：https://kubernetes.io/docs/tutorials/services/source-ip/
ingress_controller_external_traffic_policy: Cluster
# NodePort svc 监听http协议端口(注意需在NodePort端口范围)
ingress_controller_http_nodeport: 30080
# NodePort svc 监听https协议端口(注意需在NodePort端口范围)
ingress_controller_https_nodeport: 30443

# nginx-ingress-controller 镜像地址
nginx_ingress_image: registry.aliyuncs.com/kubeadm-ha/ingress-nginx_controller:v0.41.0
nginx_ingress_webhook_certgen_image: registry.aliyuncs.com/kubeadm-ha/jettech_kube-webhook-certgen:v1.5.0

# traefik默认证书过期时间（天）
traefik_certs_expired: 3650
# traefik-ingress-controller 镜像地址
traefik_ingress_image: registry.aliyuncs.com/kubeadm-ha/traefik:2.3.1

# ------------------------ #
# kubernetes-dashboard 相关参数配置
# ------------------------ #

# 是否启用kubernetes-dashboard
kubernetesui_dashboard_enabled: true

# kubernetes-dashboard默认证书有效期
kubernetesui_dashboard_certs_expired: 3650

# kubernetes-dashboard 镜像地址
kubernetesui_dashboard_image: registry.aliyuncs.com/kubeadm-ha/kubernetesui_dashboard:v2.0.4
kubernetesui_metrics_scraper_image: registry.aliyuncs.com/kubeadm-ha/kubernetesui_metrics-scraper:v1.0.4

# ------------------------ #
# metrics-server 相关参数配置
# ------------------------ #

# 是否启用metrics-server
metrics_server_enabled: true

# metrics-server image地址
metrics_server_image: registry.aliyuncs.com/kubeadm-ha/metrics-server_metrics-server:v0.4.0

# ------------------------ #
# cert-manager 相关配置
# ------------------------ #

# 是否启用cert-manager
cert_manager_enabled: true

# acme相关配置
acme_email: liu15077731547@gmail.com
acme_server: https://acme-v02.api.letsencrypt.org/directory

# cert-manager 相关 image 地址
cert_manager_cainjector_image: registry.aliyuncs.com/kubeadm-ha/jetstack_cert-manager-cainjector:v1.0.4
cert_manager_webhook_image: registry.aliyuncs.com/kubeadm-ha/jetstack_cert-manager-webhook:v1.0.4
cert_manager_controller_image: registry.aliyuncs.com/kubeadm-ha/jetstack_cert-manager-controller:v1.0.4

```



#### 升级内核

修改完配置文件后建议升级内核

```sh
ansible-playbook -i example/hosts.m-master.hostname.ini -e @example/variables.yaml 00-kernel.yml
```

内核升级完毕后重启所有节点 上执行

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
ansible-playbook -i example/hosts.m-master.hostname.ini -e @example/variables.yaml 90-init-cluster.yml
```

### 查看节点运行情况

```sh
kubectl get nodes
```

等待所有节点ready 即为创建成功	

```
NAME    STATUS   ROLES                   AGE     VERSION
node1   Ready    etcd,master,worker      3m10s   v1.19.4
node2   Ready    etcd,lb,master,worker   3m9s   v1.19.4
node3   Ready    etcd,lb,master,worker   3m6s   v1.19.4
node4   Ready    worker                  3m1s   v1.19.4
```

## 部署kuboard（可选）

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
http://ip:32567/
```

如：

```
http://10.168.1.15:32567/
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

# rancher部署(可选)

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



