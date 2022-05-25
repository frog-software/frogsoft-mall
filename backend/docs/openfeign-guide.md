## SpringCloud中使用Openfeign进行微服务跨模块调用指南

> by Nickbit

废话不多说，直接举例：

假设你有两个模块：module-A和module-B

- module-A和module-B对应的数据库基本模型是Apple和Banana

- Apple中有Banana外键的引用关系。
- 因此，在module-A的api中，获取数据时需要调用module-B的service。
- 为此，你需要对代码进行以下修改（A调用B）：

#### 增加依赖引用

在被调用的服务模块上，修改pom.xml，增加以下依赖项：

```xml
<!-- openfeign -->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

在被调用的服务模块上，找到模块入口类（被命名为`XXXXApplication`且前面有SpringBean的标志），然后在主函数类的前面加上注解：

> 注意，为了正确引用到openfeign包，你可能需要重新让maven索引一遍模块中的所有依赖

```java
@EnableFeignClients // 增加Feign服务
```

#### 撰写Client Api接口

自然的，被调用的模块中，必须有能够把数据通过Http协议返回给调用模块的函数。简单来说，你可以直接使用Controller中提供给前端的api实现后端通信的api，但这有个问题：

*在RESTful的接口设计中，返回给前端的数据是由二次包装的数据结构Dto来实现的，Dto和model不一样，是在模块中独立声明的，而不是在所有模块中都共同依赖的common模块中声明的，因此被调用者即使拿到了Dto数据，也没有办法解包，除非Dto层再进行一次后端间Client通信。*

> 这不是脱裤子放屁吗！

而且同一个模块中的Dto可能不止一种，部分返回给前端的Dto只包含部分必要的数据，并不涵盖所有在数据库中的数据（按需返回的），这些都不是我们在后端间通信想要的。

为此，简单来解决这个问题，就可以**直接为后端之间的调用撰写单独的api接口**，这样虽然不能复用提供给前端的api，但是写起来也是非常快的，依然拿上面的情况举例：

假设我们之前要返回给前端的，按照id查找banana数据的接口是这样的：

```java
private final BananaService bananaService;

// 给前端的接口
@GetMapping("/{id}")
    public ResponseEntity<?> getBananaInfo(
        @PathVariable(value = "id") Long id,
    ){
        return new ResponseBodyWrapper<BananaDto>()
            .status(HttpStatus.OK)
            .body(bananaService.getBanana(id))
            .build();
    }
```

现在，我们无需返回`BananaDto`类型的数据，而且也无需用`ResponseEntity<?>`的格式包装它了，可以直接返回`Banana`类型的数据，毕竟这个对象会被传到另一个后端模块，可以直接处理。

```java
// 我们可以约定，在url路径中以/client来开头的为后端间通信的接口，前端不应该访问它们
@GetMapping("/client/{id}")
public Banana getBananaForClient(
    @PathVariable(value = "id") Long id
){
    return bananaService.getBananaForClient(id);
}
```

问题在于，在Service-Controller结构中，Dto类型是Service负责映射和返回的，所以如果返回值依然用`bananaService.getBanana(id)`，那将返回一个`BananaDto`类型的数据，module-A是无法处理的，所以相对应的，在`bananaService`中也要对应添加一个直接返回`Banana`类型的函数`bananaService.getBananaForClient(id)`：

```java
private final BananaRepository bananaRepository;

/*带有“client”的，为后端专属服务调用，直接返回相应model类型*/
public Banana getBananaForClient(Long id){
    Optional<Banana> res = bananaRepository.findById(id);
    // 无需使用DtoMapper再进行映射了，如果非空就直接返回Banana类型
    return res.orElseThrow(() -> new NotFoundException("Banana not found."));
}
```

#### 进行Client Api调用

有了进行调用的接口，那么在module-A中，现在就可以通过openfeign来访问接口调用module-B的服务了。

首先，在module-A的controller包下面新建一个包，以此区分开正常的，返回给前端的api，并名为client，使得文件结构如下：

- controller
  - api
    - AppleController
  - client
    - XXXClient
  - request（处理带query参数请求的类）
    - ......

然后，需要后端调用哪一个模块的api，我们就**新建一个相应的interface接口**，以XXX（被调用的模块名）Client命名，这里就叫`BananaClient`：

```java
package org.frogsoft.example.apple.controller.client;

import org.frogsoft.example.common.model.banana.Banana;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 使用nacos分配的url进行服务发现
@FeignClient(url = "http://example-module-B-service:6666", path = "/bananas", value = "example-module-B")
@Component
public interface BananaClient {
    // 后端内部重映射module-B的api接口
    @GetMapping("/client/{id}")
    public Banana getBanana(@PathVariable(value = "id") Long id);
}
```

> @FeignClient 注解的参数解释：
>
> - url：后端通信指定访问到被调用模块（module-B）的url，在本项目外部访问中使用istio进行代理，所以内部url是写死的，格式为`"http://模块名-service:端口"`
> - path：所访问模块中提供api的根地址，即Controller类上`@RequestMapping(path)`注解中的根path的内容，有了这个参数，在Client接口中就无需`@RequestMapping`注解了
> - value：用于在没有url的情况下指定一个后端模块，既然这里已经用url了，该参数可有可无，可以为调用的模块起名以促进代码可读性

这样，我们在正式的业务代码`AppleService`和`AppleController`中，如果需要module-B中`Banana`类型的数据，我们就不再使用传统获得`BananaService`的方法（因为模块分离的问题，它已经无法在module-A中引用了！），取而代之的是，我们直接使用刚才定义的`BananaClient`接口。而且使用方法大同小异，例如：

```java
// 通过feign client跨模块调用module-B的服务
private final BananaClient bananaClient;

// 这个的函数会直接返回包装Banana类型的json返回包，如果你需要对数据进行进一步处理
// (比如外键连接、映射一层Dto之类的，可以直接对bananaClient.getBanana(id)的返回值进行处理)
public ResponseEntity<?> getBananaInfo(Long id){
    return new ResponseBodyWrapper<Banana>()
    .status(HttpStatus.OK)
    .body(bananaClient.getBanana(id))
    .build();
}
```

That's all.

恭喜你可以愉快撰写微服务模块间调用了！

