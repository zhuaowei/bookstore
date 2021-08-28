### bookstore

#### 一、项目简介

##### 1、介绍

一个图书评价和管理系统，实现了用户登录注册，评论点赞，标记图书状态，图书的增删改查等功能。

##### 2、项目地址

[图书评价系统](http://47.98.214.74:8080/)

[图书管理系统](http://47.98.214.74:8080/management/book/index.html)

##### 3、技术栈

-   前端：html、css、javascript、jquery、freemarker、template、wangEditor
-   后端：SSM框架、Kaptcha验证码组件、MD5加密、spring task定时任务

##### 4、界面展示

1、图书列表

![Snipaste_2021-08-28_10-42-05](https://user-images.githubusercontent.com/59002435/131204726-9331990c-fdcd-41a1-a577-a29287e905c7.png)

2、图书详情页

![Snipaste_2021-08-28_10-43-44](https://user-images.githubusercontent.com/59002435/131204741-f5c123f1-5fab-4d9b-b408-ce9e1c724861.png)

3、图书管理页

![Snipaste_2021-08-28_10-44-46](https://user-images.githubusercontent.com/59002435/131204745-2496fbd1-3b76-4e35-b8fd-1f3258b2e726.png)

#### 二、详细介绍

##### 1、目录

```tex
/
│  pom.xml （maven依赖）
│
└─src
   ├─main
   │  ├─java （java源码）
   │  │  └─com
   │  │      └─zhuaowei
   │  │          └─bookstore
   │  │              ├─bean （实体类）
   │  │              │      Book.java
   │  │              │      Category.java
   │  │              │      Evaluation.java
   │  │              │      Member.java
   │  │              │      MemberReadState.java
   │  │              │
   │  │              ├─controller （控制器）
   │  │              │  │  BookController.java
   │  │              │  │  KaptchaController.java
   │  │              │  │  MemberController.java
   │  │              │  │
   │  │              │  └─management （管理页控制器）
   │  │              │          MBookController.java
   │  │              │
   │  │              ├─mapper （mapper接口）
   │  │              │      BookMapper.java
   │  │              │      CategoryMapper.java
   │  │              │      EvaluationMapper.java
   │  │              │      MemberMapper.java
   │  │              │      MemberReadStateMapper.java
   │  │              │
   │  │              ├─service （服务接口）
   │  │              │  │  BookService.java
   │  │              │  │  EvaluationService.java
   │  │              │  │  MemberService.java
   │  │              │  │
   │  │              │  ├─exception （异常）
   │  │              │  │      BussinessException.java
   │  │              │  │
   │  │              │  └─impl （服务实现类）
   │  │              │          BookServiceImpl.java
   │  │              │          EvaluationServiceImpl.java
   │  │              │          MemberServiceImpl.java
   │  │              │
   │  │              ├─task （定时任务）
   │  │              │      ComputeTask.java
   │  │              │
   │  │              └─util （工具类）
   │  │                      MD5Util.java
   │  │
   │  ├─resources （资源目录）
   │  │  │  applicationContext.xml （spring 配置）
   │  │  │  logback.xml （日志配置）
   │  │  │  mybatis-config.xml （MyBatis配置）
   │  │  │
   │  │  ├─db （数据库文件）
   │  │  │      createTable.sql
   │  │  │
   │  │  └─mappers （mapper）
   │  │          book.xml
   │  │          category.xml
   │  │          evaluation.xml
   │  │          member.xml
   │  │          memberReadState.xml
   │  │
   │  └─webapp （web目录）
   │      ├─resources （资源文件）
   │      │  │  jquery-3.6.0.js
   │      │  │  jquery-3.6.0.min.js
   │      │  │  template-web.js
   │      │  │  wangEditor.min.js
   │      │  │
   │      │  ├─css （css表单文件）
   │      │  │      book.css
   │      │  │      bookstore.css
   │      │  │      detail.css
   │      │  │      index.css
   │      │  │      login.css
   │      │  │
   │      │  ├─images （图片文件）
   │      │  │      book.png
   │      │  │      cover.jpg
   │      │  │      like.png
   │      │  │      user.png
   │      │  │
   │      │  ├─js （js文件）
   │      │  └─raty （raty星型评分组件）
   │      │      └─lib
   │      │          │  jquery.raty.css
   │      │          │  jquery.raty.js
   │      │          │
   │      │          ├─fonts
   │      │          │      raty.eot
   │      │          │      raty.svg
   │      │          │      raty.ttf
   │      │          │      raty.woff
   │      │          │
   │      │          └─images
   │      │                  cancel-off.png
   │      │                  cancel-on.png
   │      │                  star-half.png
   │      │                  star-off.png
   │      │                  star-on.png
   │      │
   │      ├─upload （文件上传目录）
   │      └─WEB-INF （web配置文件）
   │          │  web.xml
   │          │
   │          └─ftl （freemarker文件）
   │              │  bookstore.ftl
   │              │  detail.ftl
   │              │  login.ftl
   │              │  test.ftl
   │              │
   │              └─management
   │                      book.ftl
   │
   └─test （测试用）
       └─java
           └─com
               └─zhuaowei
                   └─bookstore
                       ├─controller
                       └─service
                           └─impl
                                   BookServiceImplTest.java
 
```

