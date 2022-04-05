# 如何运行 frog-eams 后端

## 前期准备

### 依赖

你的计算机上需要安装有：

- `jdk`（项目基于 JDK 17，建议直接使用 17 LTS 版本）

同时你需要有一个能被连接的 `MySQL` 服务器（无论是运行在本机上或者其他地方的）

### 创建 MySQL 数据库

1. **接到你的 MySQL**（shell）

   ```shell
   mysql -h <hostname> -u <username> -p
   ```

   例如

   ```shell
   mysql -h localhost -u root -p
   ```

2. **创建数据库**（SQL）

   ```sql
   create database frogsoft_eams_db;
   ```

3. **创建用户**（SQL）

   ```sql
   create user 'frogsoft_eams_user'@'%' identified by 'frogsoft_eams_password';
   ```

4. **赋予用户操作权限**（SQL）

   ```sql
   grant all on frogsoft_eams_db.* to 'frogsoft_eams_user'@'%';  
   ```

## 运行后端

1. 将你的 working directory 调整为 `frogsoft-eams/backend`，也就是有 `pom.xml` 的目录

2. **运行 Spring Web 项目**

   1. 如果你完全按照上面的说明创建了数据库和用户，并且你的 MySQL 服务器在本机的话，你可以不进行任何设置直接运行：`./mvnw spring-boot:run`
   2. 如果你的数据库和用户名名称和上面所说的不一样，或者你的 MySQL 服务器不在本地的话，你需要先定义相关的环境变量（详见[环境变量说明](#环境变量说明)一节），然后运行 `./mvnw spring-boot:run`

3. 如果不出意外，你将会看到控制台输出

   `Started EamsApplication in xx.xxx seconds (JVM running for xx.xxx)`

   表示运行成功

4. **测试登录接口**

   我们使用默认用户登录：用户名 admin，密码 admin，将其序列化为 JSON 后附入 POST 请求体。记得将 `<host>` 替换为后端所在 IP，`<port>` 替换为后端端口（默认 8080）。

   ```shell
   curl -X POST http://<host>:<port>/v1/auth/login -d '{"username":"admin","password":"admin"}' -H "Content-Type: application/json; charset=utf-8"
   ```

   例如

   ```
   curl -X POST http://localhost:8080/v1/auth/login -d '{"username":"admin","password":"admin"}' -H "Content-Type: application/json; charset=utf-8"
   ```

   如果请求成功，你将看到返回登录用户的 JWT

   ```
   {"status":201,"statusString":"Created","message":"","error":"","data":{"username":"admin","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImp0aSI6IjEiLCJyb2xlcyI6IlJPTEVfQURNSU4sUk9MRV9URUFDSEVSIiwiaWF0IjoxNjQxNzMwNzYyLCJleHAiOjE2NDE4MTcxNjJ9.L9V9By6X6ec2NbC_NBk1xkP1-rd7d8_6TjD4ZkhJXi0"}}
   ```

## 环境变量说明
| Key                | Description            | Default                      | Example      |
| ------------------ | ---------------------- | ---------------------------- | ------------ |
| `MYSQL_URL`        | MySQL 服务器所在地址   | `localhost`                  | `10.12.33.2` |
| `MYSQL_PORT`       | MySQL 服务器所在端口   | `3306`                       | `3306`       |
| `MYSQL_DB`         | MySQL 数据库名称       | `frogsoft_eams_db`           | `test_db`    |
| `MYSQL_USERNAME`   | MySQL 用户名           | `frogsoft_eams_user`         | `user`       |
| `MYSQL_PASSWORD`   | MySQL 用户密码         | `frogsoft_eams_password`     | `pswd`       |
| `PORT`             | Spring 后端所在端口    | `8080`                       | `5000`       |
| `JWT_SECRET`       | JWT 密钥               | `abcdefghijklmnopqrstuvwxyz` | `<长字符串>` |
| `JWT_EXPIRE_DELAY` | JWT 密钥过期时间（秒） | `86400`                      | `3600`       |

