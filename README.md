# SA-FileSystem
### SA-2019-FinalProject
  - A FileSystem consists of two kind of micro-services: datanode & namenode, you can create several datanode services to complete a distributed model.
  - Tried to merge datanode and namenode into one project but failed(See in Repository:https://github.com/NovelistChan/DistributedFileSystem, Error: Bean Not Found)
  - So now you can see two projects: datanode & namenode, run several instances(3 in example) in datanode and one instance in namenode then check the interfaces by POSTMAN.
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
  - Here are the output-examples displayed by POSTMAN.

### References
  - http://www.ityouknow.com/ & https://github.com/ityouknow/spring-cloud-examples (Especially spring-cloud-eureka & upload files in spring-boot)
