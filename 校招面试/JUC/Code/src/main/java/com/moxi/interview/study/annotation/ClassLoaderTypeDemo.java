package com.moxi.interview.study.annotation;

/**
 * 类加载器的种类
 *
 * @author: 陌溪
 * @create: 2020-03-29-11:51
 */
public class ClassLoaderTypeDemo {
    public static void main(String[] args) {

        //当前类是哪个加载器
        ClassLoader loader = ClassLoaderTypeDemo.class.getClassLoader();
        System.out.println(loader);

        // 获取系统类加载器
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        // 获取系统类加载器的父类加载器 -> 扩展类加载器
        ClassLoader parentClassLoader = classLoader.getParent();
        System.out.println(parentClassLoader);

        // 获取扩展类加载器的父类加载器 -> 根加载器（C、C++）
        ClassLoader superParentClassLoader = parentClassLoader.getParent();
        System.out.println(superParentClassLoader);

        // 测试JDK内置类是谁加载的
        ClassLoader loader2 = Object.class.getClassLoader();
        System.out.println(loader2);

        // 如何获取类加载器可以加载的路径
        System.out.println(System.getProperty("java.class.path"));

        /*
        E:\Software\JDK1.8\Java\jre\lib\charsets.jar;
        E:\Software\JDK1.8\Java\jre\lib\deploy.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\access-bridge-64.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\cldrdata.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\dnsns.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\jaccess.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\jfxrt.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\localedata.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\nashorn.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\sunec.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\sunjce_provider.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\sunmscapi.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\sunpkcs11.jar;
        E:\Software\JDK1.8\Java\jre\lib\ext\zipfs.jar;
        E:\Software\JDK1.8\Java\jre\lib\javaws.jar;
        E:\Software\JDK1.8\Java\jre\lib\jce.jar;
        E:\Software\JDK1.8\Java\jre\lib\jfr.jar;
        E:\Software\JDK1.8\Java\jre\lib\jfxswt.jar;
        E:\Software\JDK1.8\Java\jre\lib\jsse.jar;
        E:\Software\JDK1.8\Java\jre\lib\management-agent.jar;
        E:\Software\JDK1.8\Java\jre\lib\plugin.jar;
        E:\Software\JDK1.8\Java\jre\lib\resources.jar;
        E:\Software\JDK1.8\Java\jre\lib\rt.jar;
        C:\Users\Administrator\Desktop\LearningNotes\校招面试\JUC\Code\target\classes;
        C:\Users\Administrator\.m2\repository\org\projectlombok\lombok\1.18.10\lombok-1.18.10.jar;
        C:\Users\Administrator\.m2\repository\cglib\cglib\3.3.0\cglib-3.3.0.jar;
        C:\Users\Administrator\.m2\repository\org\ow2\asm\asm\7.1\asm-7.1.jar;
        E:\Software\IntelliJ IDEA\IntelliJ IDEA 2019.1.2\lib\idea_rt.jar
         */
    }
}
