## 功能概述

​		基于SpringBoot/SpringSecurity/SpringSession/SpringSecurityOAuth2构建，着重于解决互联网应用分布式集群场景下的身份认证，并提供丰富的认证方式和社会化认证集成。能够满足所有互联网应用的所有认证需求，并通过基础组件来支撑互联网应用快速开发，例如：用户中心、认证中心、通知中心。

#### git 地址

https://codeup.aliyun.com/62dfa4061dbb334b98bab491/backend/account-center.git



### 结构设计

<img src="image-20231024143244476.png" alt="image-20231024143244476" style="zoom:50%;" />

### 用户中心

​		提供用户的管理服务，全局下手机号唯一。

#### 注册

- 短信注册
- 账号密码注册
- 第三方授权（支付宝、微信）

以微信小程序为例子，用户进入小程序后匹配unionId+渠道是否已关联用户（同一开发者下的所有小程序共用同一unionId）

- 如果未关联则发起登录，用户选择授权手机号。
    - 手机号已存在，则将unionId+渠道和该手机号做关联
        - 如果AuthStepType=1 则发起用户信息授权，修改用户信息，修改AuthStepType=2。
        - 登录成功
    - 手机号不存则视为新用户，修改AuthStepType=1，发起用户信息授权，修改用户信息，修改AuthStepType=2。
- unionId+渠道已关联了用户
    - 直接返回用户信息

<img src="/Users/zhanghaojie/Library/Application Support/typora-user-images/image-20231024094548634.png" style="zoom:50%;" />

````
AuthStepType=1，用户登录成功，已绑定手机号，无用户信息
AuthStepType=2，用户登录成功，已绑定手机号，有用户信息
````



微信小程序具体授权流程

<img src="/Users/zhanghaojie/Library/Application Support/typora-user-images/image-20231024110032751.png" alt="image-20231024110032751" style="zoom:50%;" />



#### 登录

- 账户密码登录
- 短信验证登录
- 第三方登录（支付宝、微信）

####  用户信息查询、维护

- 查询方式
    - 手机号
    - 第三方渠道查询
    - 账号密码

- 查询内容
    - 手机号
    - 邮箱
    - 昵称
    - 密码
    - 身份证信息
    - 头像
    - ....
- 查询后刷新token有效时间

#### 登录态检验

- 根据token判断是否还在登录
- 根据第三方渠道的唯一id（unionId）判断是否已注册、登录

#### 对内部提供服务

- 根据token获取用户信息
- 强制登出



#### Account-center Client

​		提供client jar包服务，提供丰富的使用方法给接入方。动态配置化，接入方可以根据需求而选择对应的服务。

##### 配置信息

​		所有的配置都放在application.properties 或 applicatiom.yml中

|                        备注                        | 配置                                |
| :------------------------------------------------: | ----------------------------------- |
|                  关闭登录拦截检验                  | account.login.closeIntercept = true |
|            Account-center颁布的授权账号            | account.setting.clientId= xxxx      |
|            Account-center颁布的授权秘钥            | account.setting.secret= yyyy        |
| 是否需要自动跳转登录页面,如设置为true 则不进行调转 | account.login.autologinpage = true  |



##### 接口、注解设计

###### Controller请求拦截

提供接口，实现后可配置需要拦截的class

````java
public interface  InterceptedClass{
  public List<Class> interceptedClass();
}
````

提供注解，提供给使用方标注需要拦截的方法或类

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccountAuthorization {

}
```

提供注解@IgnoreAuthorization，提供给使用方标注跳过拦截的方法

````java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuthorization {

}
````

spring.factories 注册需要自动配置的bean，在服务启动过程中Account-center Client会自动从中扫描出需要被拦截的方法

- 1、 找到所有InterceptedClass的实现类，调用interceptedClass()方法，获取到需要拦截的类
- 2、 实现BeanPostProcessor的after方法，轮询注册成功的Bean，判断类是否带有@AccountAuthorization或是第一步中配置的Class
- 3、 如果是的，则扫描Bean中的所有方法，注册进Account-center Client的待拦截列表，同时需要过滤掉被@IgnoreAuthorization注解修饰的方法

​		在服务启动运行过程中，Account-center Client会拦截所有的http请求，判断其是否是在需要拦截的队列中，不在队列中便会跳过检查。如在队列中，则通过HttpServletRequest，获取Request中的Account-token，如果Account-token没有值，直接结束，http code设置为302，跳转至统一登录页面（可配置页面）。Account-token有值下，远程调用Account-center服务，检验Account-token是否过期，检验token持有者与请求发起方是否一致，返回token对应用户信息，刷新token。如果Account-token已经过期，也会跳转至登录页面。



###### 接口服务

提供接口AccountCenterUserService，提供给业务方调用远程服务

````java
public Interface AccountCenterUserService{
  /**
   * 根据账号密码进行登录
   */
  public User UserLogin(String username, String password);
  /**
   * 根据手机号验证码进行登录
   */
  public User UserLogin(String phone, String code);
   /**
   * 根据token获取用户信息
   */
  public User getUserInfo(String token);
   /**
   * 根据phone发生短信验证码
   * type为验证码类型：登录、注册
   */
  public String sendCode(String phone，int type);
  /**
   * 根据用户名获取用户信息
   */
  public User getUserInfoByUsername(String username);
  /**
   * 根据手机号获取用户信息
   */
  public User getUserInfoByPhone(String phone);
   /**
   * 注册用户,code为注册时的验证码
   */
  public User registryUser(User user, String code);
   /**
   * 修改用户信息
   */
  public User updateUserInfo(User user);
	/**
   * 根据Authorization code获取Account-token
   * 只有客户端获取过的Authorization code才可以
   */
  public String updateUserInfo(String code, String clinetId, String secret);
}
````



##### 登录拦截设计 Account-center client

​		Account-center client在登录拦截后，会根据用户选择的配置进行拦截后跳转。

​		可以关闭登录拦截，即Account-center client 不会进行未登录校验，配置文件application.properties 或 applicatiom.yml 中配置 account.login.closeIntercept = true。

未登录Account-center client提供了三种登录方式：

​	1.OAuth2.0 简化模式

​	2.OAuth2.0 授权码模式

​	3.自定义登录页面进行登录

###### OAuth2.0 简化模式

​		OAuth2.0 简化模式，开放应用程序，应用程序运行在公开开放的环境。

跳转步骤：

- 1.访问受限资源
- 2.解析cookie是否有token、解析url中是否带有token
- 3.检验token是否有效
- 4.失效
- 5.重定向
- 6.跳转至Account-center登录页
- 7.填写内容后发起登录
- 8.校验登录信息
- 9.成功 302至原始页面，并在url中拼接token
- 10.跳转
- 11.访问受限资源
- 12.解析cookie是否有token、解析url中是否带有token
- 13.检验token
- 14.有效,在返回cookie中放入token
- 15.返回资源

<img src="image-20231025104839035.png" alt="image-20231025104839035" style="zoom: 40%;" />



###### OAuth2 授权码模式

<img src="image-20231025150717115.png" alt="image-20231025150717115" style="zoom: 57%;" />

授权码模式是OAuth2.0中的基本授权模式，中间会经过授权码code的过程，需要前端做二次开发，获取授权码后，再向服务端获取最终需要的Token。



###### 自定义登录页面

​		如果需要配置自定义登录页面，则需要在配置文件application.properties 或 applicatiom.yml 中配置 account.login.autologinpage = true。则可跳过未登录302重定向，由业务方自定义跳转至登录页面。

​		开启配置后，需在代码中显式调用Account-center的发送验证码、检验登录信息等接口，登录检验成功后会返回对应的token。

​		此时Account-center只是单纯的一个分布式服务，校验成功便直接返回Account-token。



##### Account-center client的服务调用

​	启动服务后主动向nacos获取该服务的注册信息。

​	同时在client也会配置Account-center server的URL地址，如果不能从nacos中获取的话，直接使用url调用。



##### Account-center 注册

​		所有接入的服务需要先向Account-center server进行注册，由Account-center颁布服务对应的client id/secret。

​		并将client id/secret配置在配置文件application.properties 或 applicatiom.yml 中，

- account.setting.clientId= xxxx
- account.setting.secret= yyyy



### 认证服务

#### SSO平台

- 基于OAuth2构建

​		基于SpringSecurity进行安全认证，采用OAuth2.0认证体系，对客户端、用户进行认证及授权，支持账号密码登录，短信验证码登录。解决单点登录。

​		验证用户时，检验Session中是否有值、是否过期，返回登录信息。


账户密码认证

手机号验证认证

根据Access Token获取用户信息

刷新Access Token

#### 认证中心

​		提供给外部服务，输入对应的账号密码（或手机号、第三方unionId），返回对应的用户信息，维护Access Token。



### 通知中心

​	站内信、短信、邮件、微信公众号推送。



#### 短信服务

- 验证码

模板管理





#### 邮件





#### 站内信





#### 微信公众号推送

绑定公众号





### 安全服务

​	提供加密解密、数字签名、身份认证等安全相关的功能。确保用户数据和通信的安全性。

​









## 代码架构

### DDD四层代码结构

1. **用户界面层（User Interface Layer）：**
    - **职责：** 负责处理用户的请求，展示信息给用户，并将用户的输入传递给应用服务层。
    - **放置内容：** 控制器（Controllers）、视图（Views）、前端页面、前端组件等。
2. **应用服务层（Application Service Layer）：**
    - **职责：** 包含应用的用例（Use Cases），负责协调领域模型和用户界面层。处理应用业务逻辑，接受用户输入，调用领域服务和仓储层完成具体的业务操作。
    - **放置内容：** 应用服务类（Application Services）、DTOs（Data Transfer Objects，用于传递数据到用户界面）、应用服务接口等。
3. **领域层（Domain Layer）：**
    - **职责：** 包含领域模型（Domain Model），负责业务规则和领域逻辑的实现。领域层是DDD的核心，包含实体（Entities）、值对象（Value Objects）、聚合（Aggregates）、领域服务（Domain Services）、领域事件（Domain Events）等。
    - **放置内容：** 实体（Entities）、值对象（Value Objects）、聚合（Aggregates）、领域服务（Domain Services）、领域事件（Domain Events）、领域事件处理器（Event Handlers）等。
4. **基础设施层（Infrastructure Layer）：**
    - **职责：** 提供通用的技术能力，如数据访问、消息队列、日志、安全等。这一层不应该包含业务逻辑，而是为领域层和应用服务层提供支持。
    - **放置内容：** 数据访问对象（Data Access Objects，DAOs）、数据库连接、消息队列、日志、安全认证、外部服务接口等。



#### httpClient











### 性能