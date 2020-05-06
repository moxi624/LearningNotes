前言
--

今天在蘑菇博客进行登录的时候，发现一个BUG，就是在用户登录后，有登录信息，但是刷新页面后，就没有头像了，F12打开调试页面，查看token也没有了

开始感觉非常的怪异，以为是chrome的原因，因为token是存储在cookie中的，而且也设置了有效期为7天，不可能退出浏览器就删除了，但是在我点开一个页面的时候

发现cookie有出现了，而且用户也正常登录，后面经过排查发现，是因为cookie二级域名和顶级域名不共享的原因而引起的

原理
--

首先Js在设置cookie的时候，默认会存放在当前的域名下，因为我登录后，会返回www.moguit.cn的页面，同时附带token信息，那么我们的token也就值保存在了 www.moguit.cn页面，而我们通过浏览器输入www.moguit.cn其实访问的是moguit.cn的顶级域名

那么就会造成：二级子域名下的cookie和顶级域名不共享的。同理 a.example.com下设置cookie， 在b.example.com下也是无法正常使用的

要避免这样的原因，我们就需要设置cookie的domain

下面是关于cookie的一些配置

*   name    Cookie的名称，Cookie一旦创建，名称便不可更改
*   value    Cookie的值，如果值为Unicode字符，需要为字符编码。如果为二进制数据，则需要使用BASE64编码
*   maxAge    Cookie失效的时间，单位秒。如果为整数，则该Cookie在maxAge秒后失效。如果为负数，该Cookie为临时Cookie，关闭浏览器即失效，浏览器也不会以任何形式保存该Cookie。如果为0，表示删除该Cookie。默认为-1。
*   secure    该Cookie是否仅被使用安全协议传输。安全协议。安全协议有HTTPS，SSL等，在网络上传输数据之前先将数据加密。默认为false。
*   path    Cookie的使用路径。如果设置为“/sessionWeb/”，则只有contextPath为“/sessionWeb”的程序可以访问该Cookie。如果设置为“/”，则本域名下contextPath都可以访问该Cookie。注意最后一个字符必须为“/”。
*   domain    可以访问该Cookie的域名。如果设置为“.google.com”，则所有以“google.com”结尾的域名都可以访问该Cookie。注意第一个字符必须为“.”。
*   comment    该Cookie的用处说明，浏览器显示Cookie信息的时候显示该说明。
*   version    Cookie使用的版本号。0表示遵循Netscape的Cookie规范，1表示遵循W3C的RFC 2109规范

我们通过domain的属性可能看到，当我们设置 .google.com的时候，那么以google.com结尾的域名都能够访问该cookie

那么如果我们想要让cookie进行共享的话，就必须以 .moguit.cn进行共享

    host = location.host;
    domainParts = host.split('.');
    domainParts.shift();
    domain = '.'+domainParts.join('.');

我们通过这段代码，就能够截取到蘑菇博客的顶级域名了，同时我们需要加上 .

![](http://image.moguit.cn/f004ce6dc7b24445805f193f09a02fa4)

完整的CookieUtil代码为：

    /**
     * CookieUtil常用的一些工具类
     */
    
    export function setCookie(name, value, days) {
      // var exp = new Date();
      // exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
      // document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    
      var domain, domainParts, date, expires, host;
    
      if (days)
      {
        date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        expires = "; expires="+date.toGMTString();
      }
      else
      {
        expires = "";
      }
    
      host = location.host;
      if (host.split('.').length === 1)
      {
        // no "." in a domain - it's localhost or something similar
        document.cookie = name+"="+value+expires+"; path=/";
      }
      else
      {
        // Remember the cookie on all subdomains.
        //
        // Start with trying to set cookie to the top domain.
        // (example: if user is on foo.com, try to set
        //  cookie to domain ".com")
        //
        // If the cookie will not be set, it means ".com"
        // is a top level domain and we need to
        // set the cookie to ".foo.com"
        domainParts = host.split('.');
        domainParts.shift();
        domain = '.'+domainParts.join('.');
    
        document.cookie = name+"="+value+expires+"; path=/; domain="+domain;
    
        // check if cookie was successfuly set to the given domain
        // (otherwise it was a Top-Level Domain)
        if (getCookie(name) == null || getCookie(name) != value)
        {
          // append "." to current domain
          domain = '.'+host;
          document.cookie = name+"="+value+expires+"; path=/; domain="+domain;
        }
      }
    }
    
    export function getCookie(name) {
      var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)")
      if (arr = document.cookie.match(reg))
        return unescape(arr[2])
      else
        return null
    }
    
    export function delCookie(name) {
      var exp = new Date();
      exp.setTime(exp.getTime() - 1);
      var cval = getCookie(name);
      if (cval != null)
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }