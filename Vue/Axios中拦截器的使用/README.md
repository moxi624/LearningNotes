# 使用Axios拦截器携带token以及跳转错误页面

## 前言

Axios 是一个基于 promise 的 HTTP 库，可以用在浏览器和 node.js 中，在使用Axios时候，一般我们会进行一定的封装，Axios拦截器分为请求拦截器 和 相应拦截器，请求拦截器主要的作用是在请求后端接口前，携带Token信息，而响应拦截器的主要作用是对后端的状态码进行校验，跳转到对应的页面

## 创建Axios对象

这里主要就是填写baseURL 以及 超时时间 timeout

```
import axios from 'axios'

// 创建axios实例
const service = axios.create({
  baseURL: '', // api 的 base_url
  timeout: 10000 // 请求超时时间 10秒
})
```



## Request拦截器

即请求拦截器，我们在请求后端接口的时候，将token放入请求头中

```
// request拦截器
service.interceptors.request.use(
  config => {
    if (getCookie("token") != undefined) {
      config.headers.Authorization = getCookie("token") // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)
```

## Response拦截器

```
service.interceptors.response.use(
  response => {
    // return response.data
    const res = response.data
    if (res.code === 'success' || res.code === 'error') {
      return res
    } else if (res.code === 401 || res.code === 400) {
      console.log('返回错误内容', res)
      router.push('404')
      return res
    } else if (res.code === 500) {
      return Promise.reject('error')
    }
  },
  error => {
    // 出现网络超时
    router.push('401')
    return Promise.reject(error)
  }
)
```

在响应拦截器中，我们主要做的事就是对返回的状态码进行判别，如果出现 401, 400 等错误，那么我们将其跳转到我们编写的404页面，如果出现请求超时，那么就可以跳转到500错误页面