# NIO深入

## BIO

概念

在提到NIO之前，我们说先看看BIO，也就是Blocking IO，阻塞IO，我们首先实现一个最基本的网络通信

```
/**
 * QQ客户端
 *
 * @author: 陌溪
 * @create: 2020-03-28-11:09
 */
public class QQClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        socket.getOutputStream().write("我来发送".getBytes());
    }
}

```

```
/**
 * QQ客户端
 *
 * @author: 陌溪
 * @create: 2020-03-28-11:09
 */
public class QQServer {
    static byte[] bytes = new byte[1024];

    public static void main(String[] args) throws IOException {
        while(true) {
            ServerSocket serverSocket = new ServerSocket();

            // 绑定IP地址
            serverSocket.bind(new InetSocketAddress(8080));

            System.out.println("服务器等待连接....");

            // 阻塞
            Socket socket = serverSocket.accept();

            System.out.println("服务器连接....");

            // 阻塞
            System.out.println("等待发送客户端数据");
            socket.getInputStream().read(bytes);

            System.out.println("数据接收成功:" + new String(bytes));
        }
    }
}
```

首先运行Server端，然后在运行Client端

```
服务器等待连接....
服务器连接....
等待发送客户端数据
数据接收成功:我来发送 
```

通过运行结果我们能发现，服务端线程会进行两次阻塞，首先第一次就是在等待连接的时候会阻塞，然后是等待数据的时候又会阻塞。

因此在服务器端，不活跃的线程，比较多，所以我们考虑单线程



## BIO怎么改成非阻塞

我们从上面的阻塞开始说起

```
while(true) {
ServerSocket serverSocket = new ServerSocket();

    // 绑定IP地址
    serverSocket.bind(new InetSocketAddress(8080));

    // 阻塞
    Socket socket = serverSocket.accept();

    // 阻塞
    socket.getInputStream().read(bytes);

}
```

假设我们现在访问淘宝网页，就相当于我们本机与淘宝的服务器建立了连接，然后淘宝服务器就会等待我的请求，但是假设我只是打开了网页，什么事情都不做，如果我的线程被一直阻塞的话，那就不能为服务进行接收了，这样就会卡在这里，但是我也不能为了这个不活跃的用户单独开启线程，因为他非常消耗我们的CPU资源，这样是会，非阻塞IO的用处就来了

在提到NIO之前，我们在将刚刚的QQServer改成非阻塞版本的伪代码，也就是我们通过一个关键字，设置他不阻塞，但是因为BIO里面没有这个方法，因此他就会一直阻塞着，所以把它称为阻塞的

```
// 设置非阻塞
serverSocket.setConfig();
```

伪代码如下：

```
/**
 * 单线程版服务器，NIO的伪代码
 *
 * @author: 陌溪
 * @create: 2020-03-28-12:04
 */
public class OneThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));

        // 套接字列表，用于存储
        List<Socket> socketList = new ArrayList<>();
        // 缓冲区
        byte[] bytes = new byte[1024];

        // 设置非阻塞
//        serverSocket.setConfig();

        while(true) {
            // 获取连接
            Socket socket = serverSocket.accept();

            // 如果没人连接
            if(socket == null) {
                System.out.println("没人连接");

                // 遍历循环socketList，套接字list
                for(Socket item : socketList) {
                    int read = socket.getInputStream().read(bytes);
                    // 表示有人发送东西
                    if(read != 0) {
                        // 打印出内容
                        System.out.println(new String(bytes));
                    }
                }
            } else {
                // 如果有人连接，把套接字放入到列表中
                socketList.add(socket);

                // 设置非阻塞
//                serverSocket.setConfig();
                // 遍历循环socketList，套接字list

                // 遍历循环socketList，套接字list
                for(Socket item : socketList) {
                    int read = socket.getInputStream().read(bytes);
                    // 表示有人发送东西
                    if(read != 0) {
                        // 打印出内容
                        System.out.println(new String(bytes));
                    }
                }

            }
        }
    }
}
```

上述也提到了，因为BIO里面不提供不阻塞的方法，因此无法将其改成非阻塞的

## NIO

但是在NIO里面，就提供了让其不阻塞的方法

在之前我们需要创建通信，BIO的方法如下所示：

```
ServerSocket serverSocket = new ServerSocket();
serverSocket.bind(new InetSocketAddress(8080));
```

在NIO里面提出了通道的概念，其实代码和上面类似，只不过上面是创建了一个Socker连接，而下面是创建了一个通道

```
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
serverSocketChannel.bind(new InetSocketAddress(8080));
```

我们创建的通道，默认是阻塞的，但是我们可以通过下面的方式，将其设置成非阻塞的

```
// 设置非阻塞
serverSocketChannel.configureBlocking(false);
```

下面我们进入while(true)的方法里面，因为原来是通过Socket获取到一个连接

```
// 获取连接
Socket socket = serverSocket.accept();
```

但是我们都知道，上述的连接是阻塞的，也就是说如果没有连接过来，它会一直阻塞的，因此Java提出了一个新的类，SockerChannel，它里面 提供了非阻塞的方法

```
// 设置非阻塞
serverSocketChannel.configureBlocking(false);
```

完整代码：

```
/**
 * NIO版QQ服务器
 *
 * @author: 陌溪
 * @create: 2020-03-28-12:16
 */
public class QQServerByNIO {
    public static void main(String[] args) throws IOException {
        // 创建一个通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));

        // 定义list用于存储SocketChannel，也就是非阻塞的连接
        List<SocketChannel> socketChannelList = new ArrayList<>();
        byte [] bytes = new byte[1024];
        // 缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        while(true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            // 但无人连接的时候
            if(socketChannel == null) {

                // 睡眠一秒
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("无人连接");
                for(SocketChannel item: socketChannelList) {
                    int len = item.read(byteBuffer);
                    if(len > 0) {
                        // 切换成读模式
                        byteBuffer.flip();
                        // 打印出结果
                        System.out.println("读取到的数据" + new String(byteBuffer.array(), 0, len));
                    }
                    byteBuffer.clear();
                }

            } else {
                // 但有人连接的时候

                // 设置成非阻塞
                socketChannel.configureBlocking(false);

                // 将该通道存入到List中
                socketChannelList.add(socketChannel);

                for(SocketChannel item: socketChannelList) {
                    int len = item.read(byteBuffer);
                    if(len > 0) {
                        // 切换成读模式
                        byteBuffer.flip();
                        // 打印出结果
                        System.out.println("读取到的数据" + new String(byteBuffer.array(), 0, len));
                    }
                    byteBuffer.clear();
                }
            }
        }
    }
}
```

