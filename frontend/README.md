# Quick Start: 如何运行 frogsoft-mall 前端

### 前期准备

你需要有：

- `docker` （如果你使用 Windows / macOS ，那么只需要安装 Docker Desktop 便包含了这两个组件）
- `amd64/x86_64` 处理器，我们不提供其他架构的处理器预构建镜像

### 运行前端

首先你需要确定：

- 前端应当监听本机的哪一个端口？
- 前端所使用的后端服务地址是什么？

在确定过后，仅需要运行命令：


```shell
docker run -p [你的端口号]:80 -e VITE_BACKEND_URL=[你的后端地址] frogsoftware/frogsoft-mall
```

其含义为：

- `-p [你的端口号]:80` 将你所需要的端口号映射到 80 端口（因为镜像中的前端默认监听此端口）
- `-e VITE_BACKEND_URL=[你的后端地址] ` 传入环境变量 `VITE_BACKEND_URL`，你必须传入此参数，否则无法运行前端。
- `frogsoftware/frogsoft-mall` 前端的镜像名称