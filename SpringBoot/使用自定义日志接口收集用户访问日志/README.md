前言
--

最开始蘑菇博客收集用户访问日志，是直接在请求接口里面进行编写的，比如像下面这样

![](http://image.moguit.cn/f3b9e96ea8334a9ea534c9b61aa7bee8)

很显然这种方法是非常笨的一种方法，因为它直接侵入了我们的业务代码，引入无关的操作，因此这次主要就是通过spring aop + 自定义接口，来收集用户的访问日志

编写自定义接口
-------

首先我们需要创建一个自定义接口

    package com.moxi.mogublog.web.log;
    
    import com.moxi.mougblog.base.enums.EBehavior;
    import com.moxi.mougblog.base.enums.PlatformEnum;
    
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    
    /**
     * 日志记录、自定义注解
     *
     * @author 陌溪
     * @date 2020年2月27日08:55:02
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BussinessLog {
    
        /**
         * 业务名称
         *
         * @return
         */
        String value() default "";
    
        /**
         * 用户行为
         *
         * @return
         */
        EBehavior behavior();
    
        /**
         * 平台，默认为WEB端
         */
        PlatformEnum platform() default PlatformEnum.WEB;
    
        /**
         * 是否将当前日志记录到数据库中
         */
        boolean save() default true;
    }

这里的用户行为使用了枚举类，方便扩展，目前共有15中行为

    package com.moxi.mougblog.base.enums;
    
    import com.moxi.mogublog.utils.JsonUtils;
    import com.moxi.mougblog.base.global.BaseSysConf;
    
    import java.util.HashMap;
    import java.util.Map;
    
    public enum EBehavior {
    
        BLOG_TAG("点击标签", "blog_tag"),
        BLOG_SORT("点击博客分类", "blog_sort"),
        BLOG_CONTNET("点击博客", "blog_content"),
        BLOG_PRAISE("点赞", "blog_praise"),
        FRIENDSHIP_LINK("点击友情链接", "friendship_link"),
        BLOG_SEARCH("点击搜索", "blog_search"),
        STUDY_VIDEO("点击学习视频", "study_video"),
        VISIT_PAGE("访问页面", "visit_page"),
        VISIT_SORT("点击归档", "visit_sort"),
        BLOG_AUTHOR("点击作者", "blog_author"),
        PUBLISH_COMMENT("发表评论", "publish_comment"),
        DELETE_COMMENT("删除评论", "delete_comment"),
        REPORT_COMMENT("举报评论", "report_comment"),
        VISIT_CLASSIFY("点击分类", "visit_classify");
    
    
        private String content;
        private String behavior;
    
        private EBehavior(String content, String behavior) {
            this.content = content;
            this.behavior = behavior;
        }
    
        /**
         * 根据value返回枚举类型，主要在switch中使用
         * @param value
         * @return
         */
        public static EBehavior getByValue(String value) {
            for(EBehavior behavior: values()) {
                if(behavior.getBehavior() == value) {
                    return behavior;
                }
            }
            return null;
        }
    
        public static Map<String, String> getModuleAndOtherData(EBehavior behavior, Map<String, Object> nameAndArgsMap, String bussinessName) {
            String otherData = "";
            String moduleUid = "";
            switch (behavior) {
                case BLOG_AUTHOR: {
                    // 判断是否是点击作者
                    if(nameAndArgsMap.get(BaseSysConf.AUTHOR) != null) {
                        otherData = nameAndArgsMap.get(BaseSysConf.AUTHOR).toString();
                    }
                };break;
                case BLOG_SORT: {
                    // 判断是否点击博客分类
                    if(nameAndArgsMap.get(BaseSysConf.BLOG_SORT_UID) != null) {
                        moduleUid = nameAndArgsMap.get(BaseSysConf.BLOG_SORT_UID).toString();
                    }
                };break;
                case BLOG_TAG: {
                    // 判断是否点击博客标签
                    if(nameAndArgsMap.get(BaseSysConf.TAG_UID) != null) {
                        moduleUid = nameAndArgsMap.get(BaseSysConf.TAG_UID).toString();
                    }
                };break;
                case BLOG_SEARCH: {
                    // 判断是否进行搜索
                    if(nameAndArgsMap.get(BaseSysConf.KEYWORDS) != null) {
                        otherData = nameAndArgsMap.get(BaseSysConf.KEYWORDS).toString();
                    }
                };break;
                case VISIT_CLASSIFY: {
                    // 判断是否点击分类
                    if(nameAndArgsMap.get(BaseSysConf.BLOG_SORT_UID) != null) {
                        moduleUid = nameAndArgsMap.get(BaseSysConf.BLOG_SORT_UID).toString();
                    }
                };break;
                case VISIT_SORT: {
                    // 判断是否点击归档
                    if(nameAndArgsMap.get(BaseSysConf.MONTH_DATE) != null) {
                        otherData = nameAndArgsMap.get(BaseSysConf.MONTH_DATE).toString();
                    }
                };break;
                case BLOG_CONTNET: {
                    // 判断是否博客详情
                    if(nameAndArgsMap.get(BaseSysConf.UID) != null) {
                        moduleUid = nameAndArgsMap.get(BaseSysConf.UID).toString();
                    }
                };break;
                case BLOG_PRAISE: {
                    // 判断是否给博客点赞
                    if(nameAndArgsMap.get(BaseSysConf.UID) != null) {
                        moduleUid = nameAndArgsMap.get(BaseSysConf.UID).toString();
                    }
                };break;
                case VISIT_PAGE: {
                    // 访问页面
                    otherData = bussinessName;
                };break;
                case PUBLISH_COMMENT: {
                    Object object = nameAndArgsMap.get(BaseSysConf.COMMENT_VO);
                    Map<String, Object> map = JsonUtils.objectToMap(object);
                    if(map.get(BaseSysConf.CONTENT) != null) {
                        otherData = map.get(BaseSysConf.CONTENT).toString();
                    }
                };break;
                case REPORT_COMMENT: {
                    // 举报评论
                    Object object = nameAndArgsMap.get(BaseSysConf.COMMENT_VO);
                    Map<String, Object> map = JsonUtils.objectToMap(object);
                    if(map.get(BaseSysConf.CONTENT) != null) {
                        otherData = map.get(BaseSysConf.CONTENT).toString();
                    }
                };break;
                case DELETE_COMMENT: {
                    // 删除评论
                    Object object = nameAndArgsMap.get(BaseSysConf.COMMENT_VO);
                    Map<String, Object> map = JsonUtils.objectToMap(object);
                    if(map.get(BaseSysConf.CONTENT) != null) {
                        otherData = map.get(BaseSysConf.CONTENT).toString();
                    }
                };break;
            }
            Map<String, String> result = new HashMap<>();
            result.put(BaseSysConf.MODULE_UID, moduleUid);
            result.put(BaseSysConf.OTHER_DATA, otherData);
            return result;
        }
    
        public String getContent() {
            return content;
        }
    
        public void setContent(String content) {
            this.content = content;
        }
    
        public String getBehavior() {
            return behavior;
        }
    
        public void setBehavior(String behavior) {
            this.behavior = behavior;
        }
    
    
    }

编写AOP代码
-------

在AOP中，我们使用环绕通知的方式，来收集用户的访问日志

    package com.moxi.mogublog.web.log;
    
    import com.moxi.mogublog.utils.AopUtils;
    import com.moxi.mogublog.utils.AspectUtil;
    import com.moxi.mogublog.utils.IpUtils;
    import com.moxi.mogublog.web.global.SysConf;
    import com.moxi.mogublog.xo.entity.ExceptionLog;
    import com.moxi.mogublog.xo.entity.SysLog;
    import com.moxi.mogublog.xo.service.WebVisitService;
    import com.moxi.mougblog.base.enums.EBehavior;
    import com.moxi.mougblog.base.holder.RequestHolder;
    import com.moxi.mougblog.base.util.RequestUtil;
    import lombok.extern.slf4j.Slf4j;
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Pointcut;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;
    
    import javax.servlet.http.HttpServletRequest;
    import java.lang.reflect.Method;
    import java.util.Map;
    
    /**
     * 日志切面
     */
    @Aspect
    @Component
    @Slf4j
    public class LoggerAspect {
    
        private SysLog sysLog;
    
        private ExceptionLog exceptionLog;
    
        @Autowired
        private WebVisitService webVisitService;
    
    
        @Pointcut(value = "@annotation(bussinessLog)")
        public void pointcut(BussinessLog bussinessLog) {
    
        }
    
        @Around(value = "pointcut(bussinessLog)")
        public Object doAround(ProceedingJoinPoint joinPoint, BussinessLog bussinessLog) throws Throwable {
    
            //先执行业务
            Object result = joinPoint.proceed();
    
            try {
                // 日志收集
                handle(joinPoint);
            } catch (Exception e) {
                log.error("日志记录出错!", e);
            }
    
            return result;
        }
    
        private void handle(ProceedingJoinPoint point) throws Exception {
    
            HttpServletRequest request = RequestHolder.getRequest();
    
            Method currentMethod = AspectUtil.INSTANCE.getMethod(point);
            //获取操作名称
            BussinessLog annotation = currentMethod.getAnnotation(BussinessLog.class);
    
            boolean save = annotation.save();
    
            EBehavior behavior = annotation.behavior();
    
            String bussinessName = AspectUtil.INSTANCE.parseParams(point.getArgs(), annotation.value());
    
            String ua = RequestUtil.getUa();
    
            log.info("{} | {} - {} {} - {}", bussinessName, IpUtils.getIpAddr(request), RequestUtil.getMethod(), RequestUtil.getRequestUrl(), ua);
            if (!save) {
                return;
            }
    
            // 获取参数名称和值
            Map<String, Object> nameAndArgsMap = AopUtils.getFieldsName(point);
    
            Map<String, String> result = EBehavior.getModuleAndOtherData(behavior, nameAndArgsMap, bussinessName);
    
            AopUtils.getFieldsName(point);
    
            if (result != null) {
                String userUid = "";
                if (request.getAttribute(SysConf.USER_UID) != null) {
                    userUid = request.getAttribute(SysConf.USER_UID).toString();
                }
                webVisitService.addWebVisit(userUid, request, behavior.getBehavior(), result.get(SysConf.MODULE_UID), result.get(SysConf.OTHER_DATA));
            }
        }
    }
    

这里使用了一个AspectUtils工具类

    package com.moxi.mogublog.utils;
    
    import com.alibaba.fastjson.JSON;
    import org.aspectj.lang.JoinPoint;
    import org.aspectj.lang.Signature;
    import org.aspectj.lang.reflect.MethodSignature;
    import org.springframework.util.StringUtils;
    
    import java.lang.reflect.Method;
    import java.util.List;
    
    /**
     * AOP相关的工具
     * @author 陌溪
     * @date 2020年2月27日08:44:28
     */
    public enum AspectUtil {
    
        INSTANCE;
    
        /**
         * 获取以类路径为前缀的键
         *
         * @param point 当前切面执行的方法
         */
        public String getKey(JoinPoint point, String prefix) {
            String keyPrefix = "";
            if (!StringUtils.isEmpty(prefix)) {
                keyPrefix += prefix;
            }
            keyPrefix += getClassName(point);
            return keyPrefix;
        }
    
        /**
         * 获取当前切面执行的方法所在的class
         *
         * @param point 当前切面执行的方法
         */
        public String getClassName(JoinPoint point) {
            return point.getTarget().getClass().getName().replaceAll("\\.", "_");
        }
    
        /**
         * 获取当前切面执行的方法的方法名
         *
         * @param point 当前切面执行的方法
         */
        public Method getMethod(JoinPoint point) throws NoSuchMethodException {
            Signature sig = point.getSignature();
            MethodSignature msig = (MethodSignature) sig;
            Object target = point.getTarget();
            return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        }
    
        public String parseParams(Object[] params, String bussinessName) {
            if (bussinessName.contains("{") && bussinessName.contains("}")) {
                List<String> result = RegexUtils.match(bussinessName, "(?<=\\{)(\\d+)");
                for (String s : result) {
                    int index = Integer.parseInt(s);
                    bussinessName = bussinessName.replaceAll("\\{" + index + "}", JSON.toJSONString(params[index - 1]));
                }
            }
            return bussinessName;
        }
    }
    

以及AOPUtils

    package com.moxi.mogublog.utils;
    
    import com.alibaba.fastjson.JSON;
    import lombok.extern.slf4j.Slf4j;
    import org.apache.ibatis.javassist.*;
    import org.apache.ibatis.javassist.bytecode.CodeAttribute;
    import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
    import org.apache.ibatis.javassist.bytecode.MethodInfo;
    import org.aspectj.lang.JoinPoint;
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.Signature;
    import org.aspectj.lang.reflect.MethodSignature;
    import org.springframework.core.DefaultParameterNameDiscoverer;
    import org.springframework.core.ParameterNameDiscoverer;
    import org.springframework.web.multipart.MultipartFile;
    
    import javax.servlet.ServletRequest;
    import javax.servlet.ServletResponse;
    import java.lang.reflect.Method;
    import java.util.HashMap;
    import java.util.Map;
    
    /**
     * 切面相关工具类
     *
     * @author: 陌溪
     * @create: 2020-01-21-12:34
     */
    @Slf4j
    public class AopUtils {
    
        /**
         * 获取参数名和值
         * @param joinPoint
         * @return
         */
        public static Map getFieldsName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
            // 参数值
            Object[] args = joinPoint.getArgs();
    
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] parameterNames = methodSignature.getParameterNames();
    
            // 通过map封装参数和参数值
            HashMap<String, Object> paramMap = new HashMap();
            for (int i = 0; i < parameterNames.length; i++) {
                paramMap.put(parameterNames[i], args[i]);
            }
            return paramMap;
        }
    }
    

需要注意的是，我们在进行日志收集的时候，采用的是@Async注解修饰，也就是异步调用

>  在Spring中，基于@Async标注的方法，称之为异步方法；这些方法将在执行的时候，将会在独立的线程中被执行，调用者无需等待它的完成，即可继续其他的操作。

        @Async
        @Override
        public void addWebVisit(String userUid, HttpServletRequest request, String behavior, String moduleUid, String otherData) {
    
            //增加记录（可以考虑使用AOP）
            Map<String, String> map = IpUtils.getOsAndBrowserInfo(request);
            String os = map.get("OS");
            String browser = map.get("BROWSER");
            WebVisit webVisit = new WebVisit();
            String ip = IpUtils.getIpAddr(request);
            webVisit.setIp(ip);
    
            //从Redis中获取IP来源
            String jsonResult = stringRedisTemplate.opsForValue().get("IP_SOURCE:" + ip);
            if (StringUtils.isEmpty(jsonResult)) {
                String addresses = IpUtils.getAddresses("ip=" + ip, "utf-8");
                if (StringUtils.isNotEmpty(addresses)) {
                    webVisit.setIpSource(addresses);
                    stringRedisTemplate.opsForValue().set("IP_SOURCE" + BaseSysConf.REDIS_SEGMENTATION + ip, addresses, 24, TimeUnit.HOURS);
                }
            } else {
                webVisit.setIpSource(jsonResult);
            }
            webVisit.setOs(os);
            webVisit.setBrowser(browser);
            webVisit.setUserUid(userUid);
            webVisit.setBehavior(behavior);
            webVisit.setModuleUid(moduleUid);
            webVisit.setOtherData(otherData);
            webVisit.insert();
        }

tip：在使用@Async注解时候，需要在启动类中加入  @EnableAsync  才能够开启异步功能

指定接口进行收集
--------

最后我们使用  @BussinessLog 在我们需要收集的日志出进行标记，标记后AOP的环绕通知 就会获取该接口的相关参数，将其实例化到数据库中

  示例代码如下：

        @BussinessLog(value = "发表评论", behavior = EBehavior.PUBLISH_COMMENT)
        @ApiOperation(value = "增加评论", notes = "增加评论")
        @PostMapping("/add")
        public String add(@Validated({Insert.class}) @RequestBody CommentVO commentVO, BindingResult result) {
    
            QueryWrapper<WebConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SysConf.STATUS, EStatus.ENABLE);
            WebConfig webConfig = webConfigService.getOne(queryWrapper);
            if (SysConf.CAN_NOT_COMMENT.equals(webConfig.getStartComment())) {
                return ResultUtil.result(SysConf.ERROR, MessageConf.NO_COMMENTS_OPEN);
            }
            ThrowableUtils.checkParamArgument(result);
    
            if (commentVO.getContent().length() > SysConf.TWO_TWO_FIVE) {
                return ResultUtil.result(SysConf.ERROR, MessageConf.COMMENT_CAN_NOT_MORE_THAN_225);
            }
            Comment comment = new Comment();
            comment.setSource(commentVO.getSource());
            comment.setBlogUid(commentVO.getBlogUid());
            comment.setContent(commentVO.getContent());
            comment.setUserUid(commentVO.getUserUid());
            comment.setToUid(commentVO.getToUid());
            comment.setToUserUid(commentVO.getToUserUid());
            comment.setStatus(EStatus.ENABLE);
            comment.insert();
    
            User user = userService.getById(commentVO.getUserUid());
    
            //获取图片
            if (StringUtils.isNotEmpty(user.getAvatar())) {
                String pictureList = this.pictureFeignClient.getPicture(user.getAvatar(), SysConf.FILE_SEGMENTATION);
                if (webUtils.getPicture(pictureList).size() > 0) {
                    user.setPhotoUrl(webUtils.getPicture(pictureList).get(0));
                }
            }
            comment.setUser(user);
    
            return ResultUtil.result(SysConf.SUCCESS, comment);
        }

最后用户的日志记录也能够成功记录下来了

![](http://image.moguit.cn/6211208ae36e4523ae620cd9cf0e180d)