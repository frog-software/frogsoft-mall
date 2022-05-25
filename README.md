# 蛙软商城 Frogsoft Mall

网上购物系统（指定 openGauss 数据库，北京邮电大学面向领域实践大数据与云服务方向课程）

## 技术栈简介

### 后端

- 开发相关

  - `Alibaba Nacos`: 配置中心、服务的注册与发现

  - `Istio`: 服务网格，提供模块间通信能力

  - `Spring Cloud Loadbalancer`: 客户端负载均衡

  - `Spring Cloud Gateway`: 服务网关

  - `Spring Cloud OpenFeign`: REST 客户端

  - `Spring Boot + Spring Framework`: 提供 Web 服务

  - `Spring Data JPA + Hibernate`: 数据库连接与 ORM

  - `JWT`: 用户鉴权

- 运维相关

  - `Kubernetes`: 容器编排、自动化部署和扩展

  - `Docker`: 容器解决方案

  - `containerd`: 容器运行时
  
  - `GitHub Actions`: CI/CD 系统

我们在决定后端架构时，就充分考虑到了**云原生**时代的需求，将我们的应用程序架构尽量设计成 ***可扩展*** *scalable* 、 ***低耦合*** *loosely coupled* 、 ***弹性*** *resilient* 、 ***可管理*** *manageable* 和 ***可观测*** *observable* 的。

> Cloud native technologies empower organizations to build and run scalable applications in modern, dynamic environments such as public, private, and hybrid clouds. Containers, service meshes, microservices, immutable infrastructure, and declarative APIs exemplify this approach.
>
> These techniques enable loosely coupled systems that are resilient, manageable, and observable. Combined with robust automation, they allow engineers to make high-impact changes frequently and predictably with minimal toil.
>
> *Excerpt From: CNCF Cloud Native Definition v1.0*

从整体来看，我们的后端主要采用了这几种概念：**微服务** + **DevOps** + **容器化** + **服务网格** ，下面逐一介绍：

#### 微服务

要做到可扩展、低耦合、弹性、可管理和可观测就基本注定了我们需要采用微服务架构，传统的单体架构已经无法满足云原生时代的需求。

微服务模块之间的低耦合度、现代容器引擎的支持和服务网格提供的通信支持，使得它们能很好地被横向扩展。现代容器编排工具提供的部署能力能够管理多个模块已经它们的冗余实例，提供传统架构无法达成的弹性和可管理性。现代的服务网格也提供了对整个应用架构的观测能力。

#### DevOps

作为校园团队项目，我们很难有专业的运维来进行应用程序的部署与维护，同时时间紧凑，这使得我们需要一种方式来加速构建、测试和发布软件的速度。

所以我们利用 GitHub Actions 搭建了我们的 DevOps Pipeline ，它的核心是 CI/CD ，即持续集成与持续交付。

开发者一旦把代码推送至 master 分支，便会触发 pipeline ，进行代码的静态检查、项目构建与可选的自动化测试。构建完成会将 Build Artifact 打包成 Docker 镜像发布至 Docker Hub ，以便我们在持续集成阶段部署至 Kubernetes 集群。

#### 容器化

容器化也是云原生时代的基础，它轻量、可移植，它将软件代码和所需的所有组件打包在一起，这样应用才能够在任何环境和任何基础架构上一致地运行。它主要使用 Linux Kernel 提供的能力，通过 `namespace` 实现了资源隔离，通过 `cgroups` 实现了资源限制，通过 `UnionFS` 实现了 Copy on Write 的文件操作。如果使用传统的部署方案，应用难以移动，开发者需要花费大量的时间适配新的环境，以上所说的微服务与 DevOps 将根本无法推行。可以说，容器化是云原生应用的必然选择，没有容器化就没有云原生。

而 Kubernetes 就是管理这些容器的工具，也是目前应用最广泛的容器编排工具。容器作为一个 IaaS 层的工具，它结合 Kubernetes 提供 PaaS 服务。它会自动执行容器的部署、管理和扩展。它采用声名式 API ，只需要指定我希望这些容器最终达成的状态， Kubernetes 就会替我达成并且维持这个状态。

#### 服务网格

在微服务的开发过程中，除了业务逻辑之外，我们还需要

- 通信确认：服务发现
- 安全逻辑：微服务模块之间的安全认证
- 重连逻辑：自动重连失败请求
- 运行指标：自动收集运行指标
- 请求追踪：追踪微服务模块之间的每一个调用请求 

而这些在传统的 Spring Cloud 等微服务的实现过程中，必须要通过其他的库来实现功能。这将为业务逻辑开发者带来额外的负担，且这些代码必须写入微服务内部，与代码相耦合。

而服务网格通过数据平面和控制平面两层结构来无感知地实现以上功能，实现额外功能与业务逻辑之间的解耦：

- 数据平面（`Data Plane`) ：每一个微服务都将拥有一个边车代理（ `sidecar`），所有进出微服务的请求都将经过该代理。
- 控制平面（`Control Plane`）：管理众多的边车代理，自动将边车代理注入到微服务容器中，实现这些边车代理之间的交流功能。

### 前端

- `Vue3`
- `Vuex`
- `Vue Router`
- `TypeScript`
- `Vite`
- `Element UI`

考虑到 Webpack 的编译速度过慢会影响开发者的开发体验，我们采用下一代前端构建工具 Vite 来提升前端开发者的开发体验。

考虑到实际部署过程中后端服务地址的多变性，我们设计可传入的环境变量参数，同时结合已经打包好的 Docker 镜像，让前端真正做到开箱即用。

我们将程序所用到的网络服务接口，抽取出服务层。服务层中的每一个接口都与接口文档中的一一对应，这样可以避免相同接口在不同的组件中需要重复实现，且减少了后续修改时候的不便之处。同时因为采用 `TypeScirpt` 我们同样单独抽取了 `types` 文件夹，统一管理各种数据类型，方便其他模块的调用与后续维护。

根据“UI 界面设计简约”的原则，我们也对前端页面风格进行初步约定，并制定口号：“知你所爱，寻你所需”，“我们致力于打造一款现代电商，犹如提着油灯在黑暗中闪闪发光前行的探索者”，整体风格呈现为黑金色调。展示效果如下：

https://user-images.githubusercontent.com/20886330/170355067-6410530f-6d0c-4f1e-9645-ed21def33d10.mp4

> 如果视频无法播放，请检查本机能否正确解码 HEVC 编码。

## 成员 Team members

[Charlie Chiang](https://github.com/charlie0129)

[sheeplin](https://github.com/FrogDar)

[Nick-bit233](https://github.com/Nick-bit233)

[Han Meng](https://github.com/NILIKUO)

[QQK](https://github.com/QQKdeGit)

