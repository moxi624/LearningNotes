# 代码块

普通代码块：在方法或语句中出现的{}，就被称为代码块

静态代码块：静态代码块有且仅加载一次，也就是在这个类被加载至内存的时候

普通代码块和一般语句执行顺序由他们在代码中出现的次序决定，先出现先执行

## 代码一

```
/**
 * 代码块
 * 普通代码块：在方法或语句中出现的{}，就被称为代码块
 * 普通代码块和一般语句执行顺序由他们在代码中出现的次序决定，先出现先执行
 *
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */
public class CodeBlock {
    public static void main(String[] args) {
        {
            int x = 11;
            System.out.println("普通代码块中的变量X=" + x);
        }

        {
            int y = 13;
            System.out.println("普通代码块中的变量y=" + y);
        }

        int x = 12;
        System.out.println("主方法中的变量x=" + x);
    }
}
```

对于上述方法，我们一下就能看出它的输出结果

```
普通代码块中的变量X=11
普通代码块中的变量y=13
主方法中的变量x=12
```



## 代码二

而对于下面的代码，我们调用了类的初始化，同时在类里也写了两个代码块

```
/**
 * 代码块
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */
public class CodeBlock02 {
    {
        System.out.println("第二构造块33333");
    }

    public  CodeBlock02() {
        System.out.println("构造方法2222");
    }

    {
        System.out.println("第一构造块33333");
    }

    public static void main(String[] args) {
        new CodeBlock02();
        System.out.println("==========");
        new CodeBlock02();
    }
}
```

运行结果如下

```
第二构造块33333
第一构造块33333
构造方法2222
==========
第二构造块33333
第一构造块33333
构造方法2222
```

这里需要谈的就是，类加载机制了，因为我们都知道，当我们实例化一个类的时候

```
new CodeBlock02();
```

启动调用的就是这个类的构造方法，但是比构造方法更上一级的是跟本构造方法在同一个类的代码块，因此

> 代码块的优先级比构造方法高
>
> 构造代码块在每次创建对象的时候都会被调用，并且构造代码块的执行次序优先于构造方法



## 代码三

```
/**
 * 代码块
 *
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */

/**
 * 随从类
 */
class Code {
    public Code() {
        System.out.println("Code的构造方法1111");
    }

    {
        System.out.println("Code的构造代码块22222");
    }

    static {
        System.out.println("Code的静态代码块33333");
    }
}
public class CodeBlock03 {

    {
        System.out.println("CodeBlock03的构造代码块22222");
    }

    static {
        System.out.println("CodeBlock03的静态代码块33333");
    }

    public CodeBlock03() {
        System.out.println("CodeBlock03的构造方法33333");
    }

    public static void main(String[] args) {

        System.out.println("我是主类======");
        new Code();
        System.out.println("======");
        new Code();
        System.out.println("======");
        new CodeBlock03();
    }
}
```

输出结果

```
CodeBlock03的静态代码块33333
我是主类======
Code的静态代码块33333
Code的构造代码块22222
Code的构造方法1111
======
Code的构造代码块22222
Code的构造方法1111
======
CodeBlock03的构造代码块22222
CodeBlock03的构造方法33333
```

从上面的结果可以看出，但这个类被加载到内存的时候，首先需要执行的是静态方法，也就是static方法在类被实例化之前，就已经完成了，和以后的实例化都没有关系了，因此我们能够看到，被第一个输出，同时静态代码块有且仅加载一次，但我们需要运行main方法的时候，就需要等CodeBlock03加载好，因此能够看到下面的输出了

```
CodeBlock03的静态代码块33333
```

同时但我们实例化 Code类的时候，这个类也会首先被加载到内存中，然后也是首先运行静态代码块

```
Code的静态代码块33333
Code的构造代码块22222
Code的构造方法1111
```

再次实例化的时候，因此该类已经在内存中，所以不再运行静态代码块了

```
Code的构造代码块22222
Code的构造方法1111
```

最后在实例化CodeBlock03，因为CodeBlock03也已经被加载内存中

```
CodeBlock03的构造代码块22222
CodeBlock03的构造方法33333
```

## 代码四

```
/**
 * 代码块
 *
 * @author: 陌溪
 * @create: 2020-04-03-9:51
 */
class Father {
    {
        System.out.println("我是父亲代码块");
    }
    public Father() {
        System.out.println("我是父亲构造");
    }
    static {
        System.out.println("我是父亲静态代码块");
    }
}
class Son extends Father{
    public Son() {
        System.out.println("我是儿子构造");
    }
    {
        System.out.println("我是儿子代码块");
    }

    static {
        System.out.println("我是儿子静态代码块");
    }
}
public class CodeBlock04 {

    public static void main(String[] args) {

        System.out.println("我是主类======");
        new Son();
        System.out.println("======");
        new Son();
        System.out.println("======");
        new Father();
    }
}
```

输出结果

```
我是主类======
我是父亲静态代码块
我是儿子静态代码块
我是父亲代码块
我是父亲构造
我是儿子代码块
我是儿子构造
======
我是父亲代码块
我是父亲构造
我是儿子代码块
我是儿子构造
======
我是父亲代码块
我是父亲构造
```

> 任何一个类被加载，必须加载这个类的静态代码块
>
> 同时如果存在父子关系的时候，调用子类的构造方法，同时子类的构造方法，在最顶部会调用super()也就是父类的构造方法，一般这个是被省略的
>
> ```
> public Son() {
>         super();
>         System.out.println("我是儿子构造");
>     }
> ```
>
> 所以在子类初始化之前，还需要调用父类构造，所以父类需要加载进内存，也就是从父到子，静态执行，并且只加载一次
>
> ```
> 我是父亲静态代码块
> 我是儿子静态代码块
> ```
>
> 然后父类在进行实例化，在调用构造方法之前，需要调用本类的代码块
>
> ```
> 我是父亲代码块
> 我是父亲构造
> ```
>
> 最后父类初始化成功后，在调用子类的

> ```
> 我是儿子代码块
> 我是儿子构造
> ```



>在执行第二次的 new Son()的时候，因为该类已经被装载在内存中了，因此静态代码块不需要执行，我们只需要从父到子执行即可
>
>```
>我是父亲代码块
>我是父亲构造
>我是儿子代码块
>我是儿子构造
>```
>
>同理在执行new Father()的时候也是一样的，只需要执行Father的实例化
>
>```
>我是父亲代码块
>我是父亲构造
>```
>
>