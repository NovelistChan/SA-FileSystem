# SA-FileSystem
### SA-2019-FinalProject
  - A FileSystem consists of two kind of micro-services: datanode & namenode, you can create several datanode services to complete a distributed model.
  - Tried to merge datanode and namenode into one project but failed(See in Repository:https://github.com/NovelistChan/DistributedFileSystem, Error: Bean Not Found)
  - So now you can see two projects: datanode & namenode, run several instances(3 in example) in datanode and one instance in namenode then check the interfaces by POSTMAN.
  - Here is the constructure of the whole project.
  - Here are the output-examples displayed by POSTMAN.
### References
  - http://www.ityouknow.com/ & https://github.com/ityouknow/spring-cloud-examples (Especially spring-cloud-eureka & upload files in spring-boot)
