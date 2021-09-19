# README

## 程序说明

这是一个简单运用多线程的下载图片的简单程序。

其中，使用了 Apache 的 `commons-io` JAR 包，需要下载后导入为项目依赖，下载地址：[Maven Repository](https://mvnrepository.com/artifact/commons-io/commons-io) ；如果网络条件不好，请使用阿里云的 [仓库服务 (aliyun.com)](https://developer.aliyun.com/mvn/guide)代替。

本程序位于项目 `src` 目录下的 `test` 目录（可以自行修改），下载后的图片位于与 src 目录同级的 `resource` 目录的 `img` 文件夹中（下载时指定了，可以自行修改）。



## 注意

代码中，如果调用 “thread.run();” 而不是 “thread.start();”，那么就不是异步执行了，而是同步执行。

程序中的图片链接来源于：[爱妹子 (imeizi.me)](https://imeizi.me/)，网站可能会挂。