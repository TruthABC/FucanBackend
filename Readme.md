## fucanapp接口文档

### 后端开发日志
* 180304: controller包依赖service包(controller - Url映射控制)
* 180304: service包依赖mapper包(service - 数据服务)
* 180304: mapper包基于mybatis(mapper - 数据库操作)
* 180304: entity包为实体，用于数据库ORMapping或返回JSON
* 180304: Local Server - "http://localhost:8080/fucan"
* 100304: application.yml -
```
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/diwei_jin?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    username: jindiwei
    password: qBgWCGUbsM9M
    platform: mysql
    initialize: false
```
* 180304: TODO - 区分http方法以实现RESTful - GET POST PUT DELETE
* 180304: TODO - from HTTP to HTTPS

* 180305: 新增：2.获取分类(category)
* 180305: TODO - session判定
* 180305: TODO - 数据获取场景问题：初始病例数据库为空，如何新建，如何使新建的病例与已有数据统一
* 180305: TODO - 病例(case)的thumbUrl含义，获取方式未知
* 180305: TODO - 病例(case)的state与progress有导出关系，与三个count有导出关系
* 180305: TODO - 病例(case)totalCount、positiveCount与negativeCount疑似为导出属性
* 180305: 新增：3.获取所有病例(case)，前端要求时间为long，对应服务器entity为Timestamp，数据库为DATETIME
* 180305: TODO - 获取病例的疑似导出数据需要在图像处理数据变化时实时更新
* 180307: 新增：4.开始筛查(/filter_start)
* 180307: TODO - 数据库与逻辑重构、对接、正规测试用例
* 180308: 新增：4.开始筛查(/filter_start)中实现了MockService，等待对接
* 180308: TODO - thumbUrl需要补充
* 180308: 
    10.48.43.53Aryan8]opals
    jindiwei
* 180313:
    建立git项目在线仓库
    

### 1.登录 /login

``` yaml
http://localhost:8080/fucan/login
username: xxx
password: xxx
```

``` json
{
  errcode: 0,
  errmsg: "",
  data: {
    session: "xxxxx",
  }
}
```

### 2.获取分类 /category

``` yaml
http://localhost:8080/fucan/category
session: xxx
```

``` json
{
  errcode: 0,
  errmsg: "",
  data: {
    categories: [
      "正常",
      "溃疡"
    ]
  }
}
```

### 3.获取所有病例 /case

``` yaml
http://localhost:8080/fucan/case
session: xxx
```

``` json
{
  errcode: 0,
  errmsg: "",
  data: {
    cases: [
      {
        id: 123456,
        name: "xxx",
        time: 1321312131123,	// 毫秒
        thumbUrl: "http://xxx.jpg",
        state: 0,	// 0-3 分别 未开始、进行中、待确认、已结束
        progress: 50,	// 进度 比如50%
        totalCount: 2000,	// 影像总数
        positiveCount: 1000,	// 阳性
        negativeCount: 20		// 阴性
      },
      {
        ...
      }
    ]
  }
}
```

### 4.开始筛查 /filter_start

``` yaml
http://localhost:8080/fucan/filter_start
session: xxx
id: 123456
```

``` json
{
  errcode: 0,
  errmsg: ""
}
```

### 5.获取筛查结果 /filter_result

``` yaml
http://localhost:8080/fucan/filter_result
session: xxx
id: 123456
```

``` json
{
  errcode: 0,
  errmsg: "",
  data: {
    id: 123456,
    results: [
      {
        category: "溃疡",
        count: 30,
        tagCount: 5,
        thumbs: [
          {
            id: 999999,
            url: "http://xx.jpg",
            time: 123456,
            tag: true
          },
          ...
        ]
      },
      ...
    ]
  }
}
```

### 影像打标记、取消标记

``` yaml
session: xxx
id: 999999
tag: true
```

``` json
{
  errcode: 0,
  errmsg: ""
}
```

### 影像修改分类

``` yaml
session: xxx
id: 999999
category: "正常"
```

``` json
{
  errcode: 0,
  errmsg: ""
}
```

### 生成报告

``` yaml
session: xxx
id: 123456	// 病例id
ids: "111,222,333,444,555,666"	// 选择的图片 id，字符串，逗号分隔
```

``` json
{
  errcode: 0,
  errmsg: ""
}
```

### 退出登录

``` yaml
session: xxx
```

``` json
... 忽略
```

