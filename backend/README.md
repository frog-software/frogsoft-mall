# Quick Start: 如何运行 frogsoft-mall 后端

> 如果你不需要开发后端代码，例如前端开发者、想测试后端接口的用户等，请参照这一小节运行后端

## Kubernetes

### 前期准备

你需要有：

- Kubernetes 集群，你可以有一下几种方法获得一个 Kubernetes 集群
  - 使用 `kubeadm` 自行搭建
  - 使用 `k3s`
  - 使用 `kind`, `minikube` 进行试用
  - 云服务厂商提供的集群，例如 Google Cloud Platform (GCP) 的 Google Kubernetes Engine (GKE) 、 AWS 的 Elastic Kubernetes Service (EKS) 、 Azure 的 Azure Kubernetes Service (AKS) 、 阿里云的 Alibaba container service for Kubernetes (ACK) 、 腾讯云的 Tencent Kubernetes Engine (TKE)
- 已经配置好与你的集群连接的 `kubectl`
- `istioctl` [(releases page)](https://github.com/istio/istio/releases)
- `docker`, `docker compose` v2

为保证顺畅运行，请阅读以下注意事项：

- 你的 Kubernetes 集群需要有足够大的物理内存，所有 Node ***至少*** 需要有合计 **24 GB** 的空闲物理内存以满足高可用部署
- Kubernetes 集群需要拥有国际互联网接入能力，以获取微服务镜像
- Kubernetes 集群可以连接你的计算机，以便连接至独立的 Nacos 和 openGauss 实例

### 部署服务

1. 在你的集群中安装 `istio`: `istio install`

2. 进入 `backend/config` 目录并执行 `./bootstrap.sh` 启动所有后端服务

3. 等待所有 pod 启动: `kubectl --namespace=frogsoft-mall get pods`

### 访问服务

判断你的 Kubernetes 集群环境是否支持外部负载均衡： `kubectl get svc istio-ingressgateway -n istio-system`。检查 `EXTERNAL-IP` 的值，如果有可用的 IP ，说明支持外部负载均衡，那么你可以使用这个 IP 作为 Ingress Gateway。

- 如果你的集群支持外部负载均衡

  - 获取 Ingress IP 和端口

    ```console
    $ export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
    $ export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}')
    $ export SECURE_INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="https")].port}')
    ```

  - 如果你的 `EXTERNAL-IP` 是  hostname 而不是 IP 地址

    ```console
    $ export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
    ```

- 如果你的集群不支持外部负载均衡

  - 获取 Ingress IP 和端口

    ```console
    $ export INGRESS_HOST=$(kubectl get po -l istio=ingressgateway -n istio-system -o jsonpath='{.items[0].status.hostIP}')
    $ export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
    ```

检查 Gateway URL

```console
$ export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
$ echo "$GATEWAY_URL"
```

接下来你可以使用这个 URL 来访问后端服务。

> 你可能也需要导入数据库初始数据，请执行以下操作（如果你自定义了数据库用户名密码，请自行替换）：
> ```bash
> docker exec -it backend-opengauss-1 /bin/bash
> su omm
> # openGauss 的 /docker-entrypoint-initdb.d 脚本执行顺序存在问题，所以我们不得不这么做
> /usr/local/opengauss/bin/gsql -v ON_ERROR_STOP=1 --username gaussdb --password Secretpassword@123 --dbname postgres -f /docker-entrypoint-initdb.d/gs.sql
> ```

## Docker Compose

> 不建议生产环境使用

### 前期准备

你需要有：

- `docker` 以及 `docker-compose` （如果你使用 Windows / macOS ，那么只需要安装 Docker Desktop 便包含了这两个组件）
- `amd64/x86_64` 处理器，我们不提供其他架构的处理器预构建镜像

为确保能够顺畅运行，你也需要有：

- 至少 12 GB 的 ***空闲*** 物理内存，这不考虑冗余和后续运行中内存使用量的增加（如果需要冗余，请准备至少 24 GB 的空闲物理内存）
- 至少 10 GB 的剩余磁盘空间（考虑到数据库的内容和微服务镜像的大小）

### 运行后端

在 `backend` 目录下，先运行 `docker compose pull` 拉取镜像，再运行 `docker compose up -d` 即可启动所有微服务模块及数据库。

> 第一次启动的话，建议再运行 `docker compose logs -f`  来查看日志。
> 
> 若几分钟后日志输出停止，而且没看到报错退出，那说明启动成功，按 `ctrl+c` 退出日志追踪。
> 
> 你可能也需要导入数据库初始数据，请执行以下操作（如果你自定义了数据库用户名密码，请自行替换）：
> ```bash
> docker exec -it backend-opengauss-1 /bin/bash
> su omm
> # openGauss 的 /docker-entrypoint-initdb.d 脚本执行顺序存在问题，所以我们不得不这么做
> /usr/local/opengauss/bin/gsql -v ON_ERROR_STOP=1 --username gaussdb --password Secretpassword@123 --dbname postgres -f /docker-entrypoint-initdb.d/gs.sql
> ```

服务网关监听于 `8080` 端口，你可以参照 Apifox 的文档发送请求测试。

# Quick Start: 如何开发 frogsoft-mall 后端（针对开发者）

> **注意：** 只有当你需要开发后端代码时，才根据这小节运行后端

## 前期准备

你需要有：

- `jdk`（项目基于 JDK 11 ，建议使用 11 及以上）
- `docker` 以及 `docker-compose` （如果你使用 Windows / macOS ，那么只需要安装 Docker Desktop 便包含了这两个组件）

为确保能够顺畅开发，你也需要有：

- 大约 16 GB 的 ***空闲*** 物理内存（包括 IDE 、后端进程等）
- 大约 10 GB 的剩余磁盘空间（考虑到数据库的内容和其他镜像、编译依赖等）

> 尽管你可以不使用 Docker ，但是并不推荐，因为这样的话微服务项目需要运行的一堆东西（例如注册中心、配置中心）还有 openGauss 数据库，就需要你自行安装了。

## 运行后端

要成功运行后端，你需要运行这几类程序：

1. 数据库 * 1 (openGauss)
2. 注册/配置中心 * 1 (Nacos + MySQL)
3. 微服务模块 * n

我们会逐一启动他们。

### 运行数据库

在 `backend` 目录下运行命令 `docker-compose up -d opengauss` 即可启动并初始化数据库。

> 第一次启动的话，建议再运行 `docker compose logs -f`  来查看日志。
> 
> 若几分钟后日志输出停止，而且没看到报错退出，那说明启动成功，按 `ctrl+c` 退出日志追踪。
> 
> 你可能也需要导入数据库初始数据，请执行以下操作（如果你自定义了数据库用户名密码，请自行替换）：
> ```bash
> docker exec -it backend-opengauss-1 /bin/bash
> su omm
> # openGauss 的 /docker-entrypoint-initdb.d 脚本执行顺序存在问题，所以我们不得不这么做
> /usr/local/opengauss/bin/gsql -v ON_ERROR_STOP=1 --username gaussdb --password Secretpassword@123 --dbname postgres -f /docker-entrypoint-initdb.d/gs.sql
> ```

### 运行注册/配置中心

在 `backend` 目录下运行命令 `docker-compose up -d nacos` 即可启动注册/配置中心。

> 同样，第一次启动的话，建议再运行 `docker-compose logs -f`  来查看日志。
>
> 若几分钟后日志输出停止，而且没看到报错退出，那说明启动成功，按 `ctrl+c` 退出日志追踪。

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

到这里后端的开发环境就启动完成了。

现在你能在 Service Tab 中看到这个项目中的模块（第一次打开是没有的），下次你可以从这里快速启动。

   ![idea-service-tab](readme-assets/idea-service-tab.png)

服务网关监听于 `8080` 端口。



## 有关 Nacos: 如何修改/新建配置

> 一般来说不需要修改配置

然后在你的浏览器中打开 http://localhost:8848/nacos/ 并登录（用户名和密码均为 `nacos`），看到 `配置管理 - 配置列表`。

在新启动的实例上你应该也会看到我内置的几个配置文件，这足够启动微服务模块了，所以理论上是不需要做任何事情的。

如果你要新建/修改配置的话，看一下下面的内容。

现在看后端目录下的 `nacos/config`  你会发现里面有***几***个文件，例如 `application-dev.yml` 。虽然他们只是 NACOS 里面配置文件的备份，但这里面的文件和 NACOS 里面的配置是一一对应的，如果你修改或者新增了配置，记得更新一下这里面的文件。

例如新增配置：

1. 在 `配置管理 - 配置列表` 标签上，点击加号➕添加配置。

   ![nacos-config-page](readme-assets/nacos-config-page.png)

2. 字段跟 `nacos/config` 下的配置文件备份是这样对应的

   ![nacos-new-config](readme-assets/nacos-new-config.png)

3. 填写完成后点击右下角的 `发布` 按钮，该配置文件发布结束。

## 环境变量说明

每个微服务模块都可以自定义这些环境变量

> 例如：虽然每个模块（例如用户模块、认证模块）的 Tomcat 默认在的端口不同，但是同一个模块启动多个实例时（例如启动 10 个用户模块），默认端口都一样就冲突了，这时可以利用 `PORT` 指定不同的实例在不同的端口

| 变量名称    | 说明                 | 默认值      |
| ----------- | -------------------- | ----------- |
| `NACOS_URL` | NACOS Server 所在 IP | `127.0.0.1` |
| `NACOS_PORT` | NACOS Server 所在端口 | `8848` |
| `PORT` | Tomcat 所在端口 | - |
| `OPENGAUSS_USERNAME` | openGauss 用户名 | `gaussdb` |
| `OPENGAUSS_PASSWORD` | openGauss 密码 | `Secretpassword@123` |
| `OPENGAUSS_URL` | openGauss 所在 IP | `localhost` |
| `OPENGAUSS_PORT` | openGauss 所在端口 | `5432` |
| `OPENGAUSS_DB` | openGauss 数据库名称 | `postgres` |
