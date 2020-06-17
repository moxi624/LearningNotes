# SpringBoot拦截器无法注入Bean

## 前言

今天在给mogu_picture模块添加拦截器，用于拦截用户传递过来的token，并获取到用户信息，这里需要注入RedisUtil工具类

```java
@Autowrite
RedisUtil redisUtil;
```

但是我在使用的时候，一直报空指针异常，开始以为是代码有问题，后面经过排查是因为没有注入RedisUtil。

首先我们先了解 @Autowired的原理，.当 Spring 容器启动时，AutowiredAnnotationBeanPostProcessor (继承InstantiationAwareBeanPostProcessorAdapter)将扫描 Spring 容器中所有 Bean，当发现 Bean 中拥有@Autowired 注解时就找到和其匹配（默认按类型匹配）的 Bean，并注入到对应的地方中去。那为什么这里的注解没有生效呢?

注册拦截器时直接通过new LoggerInterceptor(),并没有触发Spring去管理bean,所以@Autowired没有生效，如下所示，我们的拦截器是通过new TokenInterceptor()，进行创建的，并没有触发Spring去管理Bean

```
/**
 * @author: 陌溪
 * @create: 2020-06-14-21:55
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
    }
}
```

## 解决办法

我们可以通过ApplicationContent容器去获取到我们需要的bean，这里提供一个Spring工具类

```
package com.moxi.mogublog.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * SpringUtils
 *
 * @author: 陌溪
 * @create: 2020-03-05-9:30
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static HttpServletRequest getCurrentReq() {
        ServletRequestAttributes requestAttrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttrs == null) {
            return null;
        }
        return requestAttrs.getRequest();
    }

    public static String getMessage(String code, Object... args) {
        LocaleResolver localeResolver = getBean(LocaleResolver.class);
        Locale locale = localeResolver.resolveLocale(getCurrentReq());
        return applicationContext.getMessage(code, args, locale);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }
}
```

然后在具体的拦截器中，我们就可以直接获取到我们之前想要注入的bean，例如我这里获取RedisUtil

```
RedisUtil redisUtil = SpringUtils.getBean(RedisUtil.class);
```



