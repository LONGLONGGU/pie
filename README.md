# pie
  框架集成了 服务注册，服务发现、配置中心、消息总线、负载均衡、断路器、数据监控。
  
  ### 启动方式
  
   1. 下载注册中心 https://www.consul.io/ consul，启动consul
   
   `
   进入到下载目录
   ./consul agent -dev
   `
   2. 启动pie-backup服务，自动创建基础数据。
   3. 启动pie-admin管理服务。