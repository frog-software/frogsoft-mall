# Quick Start: 如何运行 frogsoft-mall 后端

## 前期准备

你需要有：

- `jdk`（项目基于 JDK 17，虽然 1.8 以上均可，建议直接使用 17 版本）

- `docker` 以及 `docker-compose` （如果你使用 Windows / macOS ，那么只需要安装 Docker Desktop 便包含了这两个组件）

> 强烈建议使用 Docker，否则微服务项目需要运行的一堆东西（例如注册中心、配置中心）还有 openGauss 数据库，光安装就能让你折腾半天了。

## 运行后端

要成功运行后端，你需要运行这几类程序：

1. 数据库 * 1 ( `openGauss` )
2. 注册/配置中心 * 1 ( `nacos` + `mysql` )
3. 微服务模块 * n

我们会逐一启动他们。

先熟悉一下我们后端目录的结构，等会你需要在这些目录间跳跃：

```
.
├── mall-auth
├── mall-common
├── mall-gateway
├── mall-user
├── ...
├── nacos
└── opengauss
```

### 运行数据库

请前往 `opengauss` 目录下运行命令 `docker-compose up -d` 即可启动并初始化数据库。

> 第一次启动的话，建议再运行 `docker-compose logs -f`  来查看日志。
>
> 若几分钟后日志输出停止，而且没看到报错退出，那说明启动成功，按 `ctrl+c` 退出日志追踪。

### 运行注册/配置中心

请前往 `nacos` 目录下运行命令 `docker-compose up -d` 即可启动注册/配置中心。

> 同样，第一次启动的话，建议再运行 `docker-compose logs -f`  来查看日志。
>
> 若几分钟后日志输出停止，而且没看到报错退出，那说明启动成功，按 `ctrl+c` 退出日志追踪。

然后在你的浏览器中打开 http://localhost:8848/nacos/ 并登录（用户名和密码均为 `nacos`）

现在看后端目录下的 `nacos/config`  你会发现里面有***几***个文件，例如 `application-dev.yml` ，你需要对这里面 ***每个*** 文件做这样的操作：

1. 在 `配置管理 - 配置列表` 标签上，点击加号➕添加配置。

   ![nacos-config-page](readme-assets/nacos-config-page.png)

2. 按照图中提示填写内容

   ![nacos-new-config](readme-assets/nacos-new-config.png)

3. 填写完成后点击右下角的 `发布` 按钮，该配置文件发布结束，继续对其他文件进行同样的操作。

### 运行微服务模块

上面我们看到了后端的目录结构，其中，这些目录包含了需要运行的模块：

```
├── mall-auth
├── mall-gateway
├── mall-user
```

> 这篇文档旨在 Quick Start ，这里暂时不会描述如何使用命令行启动项目。

如果你使用 Intellij IDEA 那么你需要做的是：

1. 不要新建项目，直接使用 IDEA 打开后端目录 `backend` ，等待索引完成。

2. 启动 ***所有*** 上面列出的需要运行的模块，以下以 `mall-auth` 为例，你需要对要启动的所有模块做这样的操作

3. 选择模块的主类

   ![idea-module](readme-assets/idea-module.png)

4. 右键选择 Debug 'xxx'，或使用快捷键 `ctrl+shift+D`

   ![idea-start-debug](readme-assets/idea-start-debug.png)
   
5. 查看日志是否报错。若启动正常，那么你可以继续启动下一个模块了，直到所有模块均启动成功。

现在你能在 Service Tab 中看到这个项目中的模块（第一次打开是没有的），下次你可以从这里快速启动。

   ![idea-service-tab](readme-assets/idea-service-tab.png)


## 环境变量说明
| 变量名称    | 说明                 | 默认值      |
| ----------- | -------------------- | ----------- |
| `NACOS_URL` | NACOS Server 所在 IP | `127.0.0.1` |

