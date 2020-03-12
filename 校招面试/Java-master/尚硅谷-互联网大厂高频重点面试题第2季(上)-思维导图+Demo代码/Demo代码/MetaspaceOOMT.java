
/*
* Java8之后的版本使用Metaspace来替代永久代
* Metaspace是方法区在HotSpot中的实现，它与持久带最大的区别在于：Metaspace并不在虚拟机内存中而是使用
* 本地内存，也即在java8中，class metaspace（the virtual machines internal presentation of java class）
* ,被存储在叫做Metaspace的native memory
*
* 永久代（Metaspace）存放以下信息：
* 虚拟机加载的类信息
* 常量池
* 静态变量
* 即时编译后的代码
* */

public class MetaspaceOOMT {
    static class OOMTest{

    }
    public static void main(String[] args){
        int i=0;//模拟多少次后发生异常

        try{
            while (true){
                i++;
                
            }
        }catch (Throwable e){
            System.out.println("********多少次后发生了异常："+i);
            e.printStackTrace();
        }
    }
}
