前言
--

今天对蘑菇博客的springboot和springcloud的版本进行升级，在升级后发现挺多地方需要更改的

首先是yml配置文件里面的security已经更改了，由之前的配置

    security:
      basic:
        enabled: true
      user:
        name: user
        password: password123

更改成下面这样的配置

    spring:
      security:
        user:
          name: user
          password: password123

然后，我们需要引入的eureka依赖，也由原来的

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-eureka-server</artifactId>
    </dependency>

变成了下面的

    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>

在启动eureka之前，我们还需要添加一个配置文件：WebSecurityConfig

![](http://image.moguit.cn/1576929701250.png)

    package com.moxi.mogublog.eureka.config;
    
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    import org.springframework.security.config.http.SessionCreationPolicy;
    
    /**
     * WebSecurityConfig
     *
     * @author: 陌溪
     * @create: 2019-12-21-19:20
     */
    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
        /**
         *
         * @param http
         * @throws Exception
         */
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // Configure HttpSecurity as needed (e.g. enable http basic).
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
            http.csrf().disable();
            // 如果是form方式,不能使用url格式登录
            http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        }
    
    }
    

因为springboot 2.x版本，将下列的yml配置给废弃了

    #高版本的丢弃了
    security:
     basic:
      enabled: true
    

所以我们需要通过配置类的方式，来启动安全验证，如果不配置的话，就会出现下面的问题

    javax.ws.rs.WebApplicationException: com.fasterxml.jackson.databind.exc.MismatchedInputException: Root name 'timestamp' does not match expected ('instance') for type [simple type, class com.netflix.appinfo.InstanceInfo]