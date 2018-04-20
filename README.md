# Bumo Java SDK

## 简介
Java开发人员可以轻松通过Bumo Java SDK操作Bumo区块链。您可以在几分钟内通过Maven或下载jar包的方式完成SDK的安装。

1. [docs](/docs) 是 Bumo Java SDK 的使用文档。
2. [examples](/examples) 是一个基于maven的项目示例。
3. [libs](/libs)  是 Bumo Java SDK 的 jar 包以及依赖包。
4. [src](/src)  是 Bumo Java SDK 的源代码

## 准备环境

适用于JDK 8及以上版本。

## 安装方式

#### 方式一：在Maven项目中加入依赖项（推荐方式）
在Maven工程中使用Bumo Java SDK，只需在maven配置中加入Bumo提供的远程仓库后，在pom.xml中加入相应依赖即可

本文的以1.0.0版本为例说明

maven 远程仓库
``` xml
<repository>
    <id>pubnexus</id>                
    <url>http://maven.bumo.io/content/groups/public/</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```
在dependencies标签内加入如下内容：
``` xml
<dependency>
  <groupId>org.bumo</groupId>
  <artifactId>bumo-sdk</artifactId>
  <version>1.0.0</version>
</dependency>
```
#### 方式二：在项目中导入JAR包
1. 下载Bumo Java SDK开发包
2. 解压该开发包
3. 将bumo-java-sdk-{version}.jar以及附带的libs里的jar导入到您的项目中

## 示例工程
Bumo Java SDK 提供了丰富的示例，供开发者参考

[示例文档入口](/docs)