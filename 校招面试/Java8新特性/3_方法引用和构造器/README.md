# 方法引用与构造器引用

## 方法引用

### 概念

若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”，可以理解为方法引用是Lambda表达式的另外一种表现形式

### 格式

- 对象::实例方法名
- 类::静态方法名
- 类::实例方法名

### 注意

- Lambda体中，调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致。
- 若Lambda参数列表中，第一个参数是实例方法的调用者，第二个参数是实例方法的参数时，可以使用ClassName::method

### 对象::实例方法名

```
     /**
     * 格式： 对象::实例方法名
     */
    public static void test() {
        Consumer<String> consumer = (x) -> System.out.println(x);

        // 使用方法引用完成（也就是上述的方法体中，已经有Lambda实现了，那就可以用方法引用的方式实现）
        // 同时使用方法引用的时候，需要保证：参数列表和返回值类型相同
        PrintStream ps = System.out;
        Consumer<String> consumer2 = ps::println;
    }

    /**
     * 格式： 对象::实例方法名
     */
    public static void test2() {
        Employee employee = new Employee("张三", 12, 5555);
        Supplier<String> supplier = () -> employee.getName();
        System.out.println(supplier.get());

        // 使用方法引用
        Supplier<String> supplier1 = employee::getName;
        System.out.println(supplier1.get());
    }
```

### 类::静态方法名

```
/**
 * 类::静态方法名
 */
public static void test3() {
    Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

    // 使用方法引用
    Comparator<Integer> comparator2 = Integer::compare;
}
```

### 类::实例方法名

```
    /**
     * 类::实例方法名
     */
    public static void test4() {
        // 比较
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abc", "abc"));

        // 使用方法引用
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));

    }
```



## 构造器引用

### 格式

- ClassName::new

### 注意

需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致

### 代码

```
    /**
     * 构造器引用
     */
    public static void test() {
        Supplier<Employee> supplier = () -> new Employee("张三", 18, 13);

        // 构造器引用（调用的无参构造器）
        Supplier<Employee> supplier1 = Employee::new;
        Employee employee = supplier1.get();

        // 构造器引用（调用有参构造器，一个参数的）
        Function<Integer, Employee> function = Employee::new;
        Employee employee1 = function.apply(10);
        System.out.println(employee1.getAge());
    }
```



## 数组引用

### 格式

- Type::new

```
    public static void test() {
        Function<Integer, String[]> function = (x) -> new String[x];
        function.apply(20);

        // 数组引用
        Function<Integer, String[]> function1 = String[]::new;
        String[] strArray = function1.apply(20);
        System.out.println(strArray.length);
    }
```

