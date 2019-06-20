# SA-FileSystem
### SA-2019-FinalProject
  - A FileSystem consists of two kind of micro-services: datanode & namenode, you can create several datanode services to complete a distributed model.
  - Tried to merge datanode and namenode into one project but failed(See in Repository:https://github.com/NovelistChan/DistributedFileSystem, Error: Bean Not Found)
  - So now you can see two projects: datanode & namenode. Namenode is a Eureka Server with a listener to manage the health condition of datanodes & send requests to datanodes. Datanode is a Eureka Client & it can save blocks in repository. Run several instances(3 in example) in datanode and one instance in namenode then check the interfaces by POSTMAN. （namenode实现为EurekaServer, 有一个EurekaListener可以监听datanode的健康情况，datanode实现为EurekaClient, 接收namenode发送的请求对block进行管理）
  - Here is the structure of the whole project(namenode & datanode).
```
namenode
├── mvnw
├── mvnw.cmd
├── namenode.iml
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── czf
│   │   │       └── nju
│   │   │           └── namenode
│   │   │               ├── NamenodeApplication.java
│   │   │               ├── controller
│   │   │               │   └── NameNodeController.java
│   │   │               ├── domain
│   │   │               │   ├── BaseEntity.java
│   │   │               │   ├── Block.java
│   │   │               │   ├── DataNode.java
│   │   │               │   └── Directory.java
│   │   │               ├── listener
│   │   │               │   └── EurekaListener.java
│   │   │               ├── repository
│   │   │               │   ├── DataNodeRepository.java
│   │   │               │   └── DirectoryRepository.java
│   │   │               └── service
│   │   │                   ├── BlockService.java
│   │   │                   ├── DataNodeService.java
│   │   │                   ├── DirectoryService.java
│   │   │                   └── NameNodeService.java
│   │   └── resources
│   │       ├── application.properties
            └── templates
   
```
```
datanode
├── datanode.iml
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── czf
│   │   │       └── nju
│   │   │           └── datanode
│   │   │               ├── DatanodeApplication.java
│   │   │               ├── controller
│   │   │               │   └── DataNodeController.java
│   │   │               ├── domain
│   │   │               │   ├── BaseEntity.java
│   │   │               │   └── Block.java
│   │   │               ├── repository
│   │   │               │   └── BlockRepository.java
│   │   │               └── service
│   │   │                   └── BlockService.java
│   │   └── resources
│   │       └── application.properties

```
  - modify the application.properties to run 3 instances of datanode:
```
spring.application.name=spring-cloud-datanode1
server.port=9000
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```
```
spring.application.name=spring-cloud-datanode2
server.port=9001
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```
```
spring.application.name=spring-cloud-datanode3
server.port=9002
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```
  - Here are the output-examples displayed by POSTMAN.
    - upload file to /user1 & /user2
![上传testfile1](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/upload1.png)
![上传testfile2](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/upload2.png)
    - get file directory
![获取根目录](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/get%20all.png)
    - get file in /user1
![获取user1文件夹下目录](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/get%20user1.png)
    - download /user1/testfile1 (return the bytes generated by file.getBytes()现在获得的字符串是文件执行file.bytes()后的字节串)
![下载文件](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/get%20file.png)
    - delete /user1/testfile1
![删除文件](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/delete.png)   
    - get directory after delete
![查看删除文件后的目录](https://github.com/NovelistChan/SA-FileSystem/blob/master/images/get%20after%20delete.png)
  - Load Balance: When a Block needs to be upload, choose a datanode with fewest blocks to upload to. （每次要上传一个block时，选取负载量最少的datanode来作为block上传的节点）
  
### References
  - http://www.ityouknow.com/ & https://github.com/ityouknow/spring-cloud-examples 
