# 如何运行 frogsoft-mall 后端

## 前期准备

你需要有：

- `jdk`（项目基于 JDK 17，虽然 1.8 以上均可，建议直接使用 17 版本）

- `docker` 以及 `docker-compose` （在开发阶段这个主要用于运行 `openGauss` 数据库）

**如果你满足了以上需求，那么可以前往下一节了。**

如果你不想或不能安装 `docker` 和 `docker-compose`，那么你需要提前准备一个能被连接的 `openGauss` (其实就是 PostgreSQL 🤫) 数据库（在本机或者网络上均可）。

## 运行后端

你能看到我们的项目是这样的结构

```
.
├── README.md
├── backend
├── frontend
└── opengauss
```

### 准备数据库

如果你安装了 `docker-compose` ，请前往 `opengauss` 目录下运行命令 `docker-compose up -d` 即可启动并初始化数据库，然后前往下一节。

如果你没有安装 `docker-compose`，那你需要先安装 `opanGauss` （你可以用 Linux 虚拟机），然后创建一个用户（用户名 `gaussdb` 密码 `Secretpassword@123`），再创建一个数据库名为 `postgres`，刚刚那个用户需要拥有对这个数据库的所有操作权限。

> 给定用户名密码和数据库名称是为了快速运行起来。你当然可以自定义他们，不同的用户名密码运行后端的时候只需配置环境变量即可。

### 运行后端

1. 将你所在的目录调整为 `backend`，也就是有 `pom.xml` 的目录

2. 运行命令 `mvnw spring-boot:run` (Windows), `./mvnw spring-boot:run` (Linux / macOS)

3. 如果不出意外，你将会看到控制台输出

   `Started MallApplication in xx.xxx seconds (JVM running for xx.xxx)`

   并且程序保持运行没有退出，这表示运行成功，你可以开始后端开发了。


## 环境变量说明
| 变量名称            | 说明                      | 默认值                       |
| ------------------- | ------------------------- | ---------------------------- |
| `POSTGRES_URL`      | PostgreSQL 服务器所在地址 | `localhost`                  |
| `POSTGRES_PORT`     | PostgreSQL 服务器所在端口 | `5432`                       |
| `POSTGRES_DB`       | PostgreSQL 数据库名称     | `postgres`                   |
| `POSTGRES_USERNAME` | PostgreSQL 用户名         | `gaussdb`                    |
| `POSTGRES_PASSWORD` | PostgreSQL 用户密码       | `Secretpassword@123`         |
| `PORT`              | Spring 后端所在端口       | `8080`                       |
| `JWT_SECRET`        | JWT 密钥                  | `abcdefghijklmnopqrstuvwxyz` |
| `JWT_EXPIRE_DELAY`  | JWT 密钥过期时间（秒）    | `86400`                      |

