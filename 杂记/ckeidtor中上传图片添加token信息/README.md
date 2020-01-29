# CkEidtor中上传图片添加token信息

这两天在给蘑菇博客增加图片上传至七牛云的功能，在改造至ckeditor的时候，因为原来mogu-picture是不需要token信息就能进行上传的，但是现在想着mogu-picture应该从mogu-admin服务中获取七牛云的配置文件，然后进行图片上传的时候，因为mogu-admin是需要携带请求头的，因此ckeditor在上传的时候，配置token信息，然后通过feign传递token至mogu-admin，然后最终返回对应的七牛云配置

我们首先需要做的就是，修改ckeditor中的配置，将下面两个配置都注释

```
//开启工具栏“图像”中文件上传功能，后面的url为待会要上传action要指向的的action或servlet
config.filebrowserImageUploadUrl = "http://localhost:8602/ckeditor/imgUpload?";

//开启插入\编辑超链接中文件上传功能，后面的url为待会要上传action要指向的的action或servlet
config.filebrowserUploadUrl = 'http://localhost:8602/ckeditor/fileUpload';
```

然后找到ckeditor的组件，添加filebrowserImageUploadUrl 和 filebrowserUploadUrl

```
    //使用ckeditor替换textarea，设置代码块风格为 zenburn
    CKEDITOR.replace('editor',
    {height: '275px',
    width: '100%',
    toolbar: 'toolbar_Full',
    codeSnippet_theme: 'zenburn',
    filebrowserImageUploadUrl: 'http://localhost:8602/ckeditor/imgUpload?token=' + getToken(),
    filebrowserUploadUrl: 'http://localhost:8602/ckeditor/imgUpload?token=' + getToken(),
    });

```

同时链接后面增加token信息即可

然后Java后端通过以下代码获取token参数，

```
String token = request.getParameter("token");
```



