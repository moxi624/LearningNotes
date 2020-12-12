本文整理在 Ubuntu 16.04.x LTS 操作系统上容器相关部署手册



容器平台

- Rancher
- Kubernetes
- Helm
- Docker

容器服务

- prometheus
- grafana
- postgresql



# 1.Docker

## 1.1.apt source

更换apt源大多数情况下可以加快软件下载速度。

```
cp /etc/apt/sources.list /etc/apt/sources.list.bak

# 阿里源
tee > /etc/apt/sources.list << EOF
deb http://mirrors.aliyun.com/ubuntu/ xenial main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ xenial-backports main restricted universe multiverse
#deb-src http://mirrors.aliyun.com/ubuntu/ xenial main restricted universe multiverse
#deb-src http://mirrors.aliyun.com/ubuntu/ xenial-security main restricted universe multiverse
#deb-src http://mirrors.aliyun.com/ubuntu/ xenial-updates main restricted universe multiverse
#deb-src http://mirrors.aliyun.com/ubuntu/ xenial-proposed main restricted universe multiverse
#deb-src http://mirrors.aliyun.com/ubuntu/ xenial-backports main restricted universe multiverse
EOF

# 清华源
tee > /etc/apt/sources.list << EOF
#deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
#deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
#deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
#deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
#deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
EOF

# 中科大源
tee > /etc/apt/sources.list << EOF
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic main restricted universe multiverse
#deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
#deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
#deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
#deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
#deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
EOF

# 网易源
tee > /etc/apt/sources.list << EOF
deb http://mirrors.163.com/ubuntu/ bionic main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-security main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-updates main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb http://mirrors.163.com/ubuntu/ bionic-backports main restricted universe multiverse
#deb-src http://mirrors.163.com/ubuntu/ bionic main restricted universe multiverse
#deb-src http://mirrors.163.com/ubuntu/ bionic-security main restricted universe multiverse
#deb-src http://mirrors.163.com/ubuntu/ bionic-updates main restricted universe multiverse
#deb-src http://mirrors.163.com/ubuntu/ bionic-proposed main restricted universe multiverse
#deb-src http://mirrors.163.com/ubuntu/ bionic-backports main restricted universe multiverse
EOF

apt update
# apt upgrade
```

备注：

“tee”可以替换成“cat”，有些操作系统可以用“tee”但是没有“cat”。

“>”表示覆写文件，“>>”表示在文件尾部添加。“tee”可以省略“>”来覆写，“cat”不能省略。

“deb”是指deb包目录；“deb-src”是源码目录，一般注释掉。

## 1.2.install docker

官网

https://docs.docker.com/engine/install/ubuntu/

```
sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common


curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -


sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"


sudo apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io
```

指定版本

```
apt-cache madison docker-ce
sudo apt-get install docker-ce=<VERSION_STRING> docker-ce-cli=<VERSION_STRING> containerd.io
```

## 1.3.docker source

配置镜像加速器

https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors

```
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://aykfevb9.mirror.aliyuncs.com"],
  "insecure-registries":["https://10.22.224.66"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```



## 1.4.postgresql

docker hub

https://hub.docker.com/_/postgres

```
docker run -d \
  --name postgres \
  -p 5432:5432 -it -e POSTGRES_PASSWORD=password \
  postgres
```

备注：

“--rm”表示 Automatically remove the container when it exits。表示临时运行容器，和“-d”后台运行“冲突”。-“-rm”等价于当容器在前台运行，退出后执行了 docker rm -v

“-e”表示环境变量

“-i”表示以“交互模式”运行容器，让容器的标准输入保持打开

“-t”表示容器启动后会进入其命令行，让Docker分配一个伪终端（pseudo-tty）并绑定到容器的标准输入上

```
docker exec -it postgres bash
psql -U postgres

postgres=# create table test (id int primary key not null, value text not null);
CREATE TABLE
postgres=# insert into test values (1, 'value1');
INSERT 0 1
postgres=# select * from test;
 id | value
----+--------
  1 | value1
(1 row)

\q
exit
```

### 1.4.1.pgagent

制作镜像

```
mkdir /dockerfile
tee > Dockerfile << EOF
FROM postgres
RUN apt-get update
RUN apt-get install -y pgagent
EOF
docker build -t pg-pgagent .
```

绑定插件

```
docker run --name mypgpgagent -e POSTGRES_PASSWORD=123456 -p 5432:5432 -d pg-pgagent

docker exec -it mypgpgagent /bin/bash
psql -U postgres
CREATE EXTENSION pgagent;
\q
/usr/bin/pgagent hostaddr=127.0.0.1 dbname=postgres user=postgres password=123456 -s ./pgagent.log
exit
```

在pgadmin中选择远程连接要执行定时任务的host, port, dbname, user, password,...

测试时pgagent.log中没有写入

### 1.4.2.pgadmin

```
docker run -p 5050:80 \
  -e "PGADMIN_DEFAULT_EMAIL=user@domain.com" \
  -e "PGADMIN_DEFAULT_PASSWORD=SuperSecret" \
  -d dpage/pgadmin4
```

## 1.5.prometheus

*可以在rancher中快速配置

“prometheus.yml”中定义了要监控的对象，以“job_name”进行分类。

```
vim /path/prometheus.yml

docker run  -d \
  --name prometheus \
  -p 9090:9090 \
  -v /path/prometheus.yml:/etc/prometheus/prometheus.yml  \
  prom/prometheus
```

备注：

“-p”表示端口映射，“:”左边的端口理解为目标端口，在这里是主机的端口；右边的端口是容器内 prometheus 程序暴露的端口。一般修改左边的端口，用以暴露服务。

“-v”表示挂载，“:”左边是本机路径；右边是容器内路径。



prometheus.yml 示例

```
scrape_configs:
  - job_name: prometheus
    static_configs:
      - targets: ['10.22.224.71:9090']

  - job_name: postgres_exporter
    static_configs:
      - targets: ['10.22.224.71:9187']

  - job_name: node_exporter
    static_configs:
      - targets: ['10.22.224.71:9100']
```

kubernetes_sd_config

https://prometheus.io/docs/prometheus/latest/configuration/configuration/#kubernetes_sd_config



使用容器运行 prometheus 不能动态刷新 prometheus.yml

--web.enable-lifecycle

https://prometheus.io/docs/prometheus/latest/management_api/

https://www.cnblogs.com/wurijie/p/11795711.html

kill -HUP pid

curl -X POST http://10.22.224.71:3001/-/reload





### 1.5.1.postgres_exporter

官网

https://github.com/wrouesnel/postgres_exporter

```
docker run -d \
  --name postgres_exporter \
  -p 9187:9187 \
  -e DATA_SOURCE_NAME="postgresql://postgres:password@192.168.0.100:5432/postgres?sslmode=disable" \
  wrouesnel/postgres_exporter
```

### 1.5.2.node_exporter

```
docker run -d \
  --name node_exporter \
  --net="host" \  
  -p 9100:9100 \
  -v "/proc:/host/proc:ro" \
  -v "/sys:/host/sys:ro" \
  -v "/:/rootfs:ro" \
  prom/node-exporter
```

## 1.6.grafana

*可以在rancher中快速配置

```
docker run -d \
  --name=grafana \
  -p 3000:3000 \
  grafana/grafana
```

默认用户名密码 admin/admin

### 1.6.1.alert - dingding

钉钉开发文档

https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq

1. 仅在pc端dingding客户端上能创建webhook
   在左上角处选择“机器人管理”-->“自定义”，“安全设置”选择“自定义关键词”，即包含该关键词才能发送报警，记住最终生成的webhook
   ![image](images/1587988512682-beed08e0-a1f2-4809-bfab-98b5253610af.png)
   ![image](images/1587988610421-81bd3819-5a3d-4f14-8f80-f1c8e5b90329.png)
   ![image](images/1587988702278-175f3247-b5bd-4a40-834e-bfdfe4fba5d3.png)
   ![image](images/1587988437050-b19bcf36-2577-45eb-96d2-d88fd65507fa.png)
2. grafana配置报警

1. 1. 配置全局的dingding报警，输入webhook
   2. 在具体的图表中配置报警选择已配置的dingding报警
      ![image](images/1587989954726-3f5b6a7f-15cf-434a-b526-66522eaca98b.png)![image](images/1587990143702-1cefd6df-0f83-4e68-846e-cf8e4ebdb123.png)![image](images/1587990196040-4e320a97-10e1-4362-8dcf-efbf2ef6c3ff.png)![image](images/1587991013837-715ba47b-c043-4236-906b-6d1e406a162f.png)

### 1.6.2.dashboard template

https://grafana.com/grafana/dashboards?orderBy=name&direction=asc

https://grafana.com/grafana/dashboards/11376

![image](images/1587985751427-8e176c22-6bf1-410e-af31-4d36f2462c44.png)





基于docker 搭建Prometheus+Grafana

https://www.cnblogs.com/xiao987334176/p/9930517.html

Grafana + Prometheus 监控PostgreSQL

https://www.cnblogs.com/ilifeilong/p/10543876.html



# 2.rancher

官网

https://rancher.com/docs/rancher/v2.x/en/installation/other-installation-methods/single-node-docker/

```
docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher:stable
```

如果下载慢，可以换源

```
docker run -d --restart=unless-stopped -p 80:80 -p 443:443 registry.cn-hangzhou.aliyuncs.com/rancher/rancher:v2.4.2
```

在rancher中可以快速创建k8s集群，rancher会生成一段命令，把命令复制到目标节点就可以快速创建集群。

# 3.Kubernetes

本地运行 k8s https://github.com/kubernetes/community/blob/master/contributors/devel/running-locally.md

## 3.1.prepare env

port

```
vim /etc/ssh/sshd_config
service ssh restart
sudo reboot
```

hostname

```
hostnamectl set-hostname master01
sudo reboot
```

hosts

```
cat > /etc/hosts << EOF
10.22.243.114 master01
10.22.243.124 node01
10.22.243.125 node02
EOF
```

scp 分发

```
scp -P 5022 /etc/hosts root@node01:/etc/hosts
```

确认已经关闭 firewall, swap, selinux, iptables

## 3.2.docker

见1.2

## 3.3.tools

https://v1-16.docs.kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/

```
apt-get update && apt-get install -y apt-transport-https curl

sudo curl -s https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | sudo apt-key add -

cat > /etc/apt/sources.list.d/kubernetes.list <<EOF
deb https://mirrors.aliyun.com/kubernetes/apt kubernetes-xenial main
EOF

apt-get update && apt-get install -y kubelet kubeadm kubectl

apt-mark hold kubelet kubeadm kubectl
```

## 3.4.init cluster

```
kubeadm init --apiserver-advertise-address 10.22.224.113 \
  --pod-network-cidr=10.244.0.0/16 \
  --image-repository registry.cn-hangzhou.aliyuncs.com/google_containers
```

> Your Kubernetes control-plane has initialized successfully!
>
> To start using your cluster, you need to run the following as a regular user:
>
> mkdir -p $HOME/.kube
>
>  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
>
>  sudo chown $(id -u):$(id -g) $HOME/.kube/config
>
> You should now deploy a pod network to the cluster.
>
> Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
>
> https://kubernetes.io/docs/concepts/cluster-administration/addons/
>
> Then you can join any number of worker nodes by running the following on each as root:
>
> kubeadm join 10.22.224.113:6443 --token peyc01.8mgmnj6k6zntq84s \
>
>   --discovery-token-ca-cert-hash sha256:ff2b928d246d5fdd8c27c152ab013df5fd665bb13f01184d8fb498f3d50fb554

```
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

## 3.5.flannel

https://v1-16.docs.kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/

https://raw.githubusercontent.com/coreos/flannel/2140ac876ef134e0ed5af15c65e414cf26827915/Documentation/kube-flannel.yml

```
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```

flannel镜像下载慢可以在一个节点下载然后分发，见4.2

## 3.6.join cluster

```
 kubeadm join 10.22.224.113:6443 --token peyc01.8mgmnj6k6zntq84s \
   --discovery-token-ca-cert-hash sha256:ff2b928d246d5fdd8c27c152ab013df5fd665bb13f01184d8fb498f3d50fb554
```

## 3.7.dashboard

无证书dashboard火狐可以访问

```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta5/aio/deploy/recommended.yaml
kubectl patch svc -n kubernetes-dashboard kubernetes-dashboard -p '{"spec":{"type":"NodePort"}}'
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep admin-user | awk '{print $1}')
kubectl get svc -n kubernetes-dashboard
```

有证书dashboard

https://github.com/kubernetes/dashboard/releases

https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-rc1/aio/deploy/recommended.yaml

```
mkdir keys
cd keys

# 创建空白证书
openssl genrsa -out ca.key 2048
openssl req -new -x509 -key ca.key -out ca.crt -days 3650 -subj "/C=CN/ST=HB/L=WH/O=DM/OU=YPT/CN=CA"

# 创建 dashboard 证书
(umask 077; openssl genrsa -out dashboard.key 2048)
openssl req -new -key dashboard.key  -out dashboard.csr -subj "/O=zhixin/CN=dashboard"
openssl  x509 -req -in dashboard.csr -CA ca.crt -CAkey ca.key  -CAcreateserial -out dashboard.crt -days 3650


kubectl create namespace kubernetes-dashboard

kubectl create secret generic kubernetes-dashboard-certs -n kubernetes-dashboard --from-file=dashboard.crt=./dashboard.crt  --from-file=dashboard.key=./dashboard.key
kubectl get secret -n kubernetes-dashboard |grep dashboard

kubectl create serviceaccount kubernetes-dashboard -n kubernetes-dashboard
kubectl get sa -n kubernetes-dashboard |grep kubernetes-dashboard

kubectl create clusterrolebinding kubernetes-dashboard --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:kubernetes-dashboard

kubectl get secret -n kubernetes-dashboard |grep dashboard
kubectl describe secret kubernetes-dashboard-token-tptw4 -n kubernetes-dashboard

kubectl apply -f recommended.yaml
kubectl get svc -n kubernetes-dashboard
```



删除dashboard

```
kubectl delete ns kubernetes-dashboard
```

## 3.8.helm

https://helm.sh/docs/intro/install/

```
sudo snap install helm --classic
helm repo add stable https://kubernetes-charts.storage.googleapis.com/
# helm pull stable/stolon
```

## 3.9.stolon - helm

https://hub.helm.sh/charts/stable/stolon

在rancher中提前配置好“storageClassName”和“PV”

```
helm pull stable/stolon

vim value.yaml
#########################
image:
  repository: sorintlab/stolon
  tag: v0.16.0-pg10
  
​````````````````````````
  
persistence:
  enabled: true
  storageClassName: "local-storage-class"
  accessModes:
  - ReadWriteOnce
  size: 50Gi 
  
​````````````````````````  
  
superuserUsername: "stolon"
superuserPassword: "admin163"
replicationUsername: "repluser"
replicationPassword: "admin163"  
#############################

helm install -n stolon my-stolon ./stolon
```

备注：

stolon的“--dry-run”可以“simulate an install”生成yml文件

helm install xxx-release-name ./stolon --dry-run



appendix

手动生成“StorageClass”和“PersistentVolume”

```
---
kind: StorageClass
apiVersion: storage.k8s.io/v1
metadata:
  name: local-storage
provisioner: kubernetes.io/no-provisioner
volumeBindingMode: WaitForFirstConsumer
---
apiVersion: v1
kind: PersistentVolume
metadata:
  name: stolonpv1
spec:
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  persistentVolumeReclaimPolicy: Retain
  storageClassName: sc
  local:
    path: /stolon
  nodeAffinity:
    required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - node01
```

*通过helm安装不需要手动同步？？？？？？？？？？？？？？？

待定，需要测试一下



*如果按照stolon的示例，仅使用yml文件部署，需要初始化stolon，否则数据库不会同步

https://github.com/sorintlab/stolon/tree/master/examples/kubernetes

```
kubectl exec -it release-name-stolon-keeper-0 -- /bin/bash
stolonctl  --cluster-name=release-name-stolon --store-backend=kubernetes --kube-resource-kind=configmap init
```



“--cluster-name”要去configmap里看一下，要对应上



stolon init 之后，pg的默认user，dbname和密码都重置了，重置为helm install时的配置，

数据的数据？



手动改掉pg的密码之后，stolon不能识别了！怎么办？



## 3.10 manage k8s

- windows



- ubuntu

```
sudo curl -s https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | sudo apt-key add -

cat > /etc/apt/sources.list.d/kubernetes.list <<EOF
deb https://mirrors.aliyun.com/kubernetes/apt kubernetes-xenial main
EOF

apt-get update && apt-get install -y kubectl
```



# 4.command

## 4.1.linux

查看端口使用

netstat -ntpa

netstat -ntpl 



高性能模式

apt-get install cpufrequtils

cpufreq-info

cpufreq-set -g performance



apt-get install sysfsutils

echo  -e 'devices/system/cpu/cpu0/cpufreq/scaling_governor = performance' /etc/sysfs.conf



修改端口

vim /etc/ssh/sshd_config

service ssh restart

## 4.2.docker

docker 运维笔记

https://www.cnblogs.com/kevingrace/p/5715326.html



进入容器

docker exec -it [ID] /bin/bash



查看容器完整 command

docker ps -a --no-trunc



停止删除所有容器

docker stop $(docker ps -aq)

docker rm $(docker ps -aq)



批量删除镜像

docker image rm $(docker image ls -a -q)

docker rmi $(docker image ls -a -q)



导出导入镜像

docker save -o flannel.tar quay.io/coreos/flannel:v0.11.0-amd64

docker load < flannel.tar



修改容器为一直启动

docker container update --restart=always 容器名字

## 4.3.kubernetes

kubectl scale --current-replicas=2 --replicas=3 statefulset/ironman-stolon-keeper -n njupt-stolon

kubectl scale --current-replicas=2 --replicas=3 -n njupt-stolon deployment ironman-stolon-proxy



kubectl patch svc -n njupt-stolon ironman-stolon-proxy -p '{"spec":{"type":"NodePort"}}'

or https://www.jianshu.com/p/75af95641c91

kubectl expose deployment [xxxxxxxx] --type=NodePort

![image.png](images/1591921126800-ab6f34cf-78ec-40ce-a88f-9d6d2040b395.png)





pv状态转换

kubectl edit pv pgv3

删去spec.clainRef

released-->available



# stolon