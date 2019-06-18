package czf.nju.namenode.service;

import czf.nju.namenode.domain.DataNode;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * datanode的创建等一系列操作
 */
@Service
public class DataNodeService {
    @Autowired
    DataNodeRepository dataNodeRepository;

    public void newDataNode(String Id, String url, String name){
        DataNode dataNode = new DataNode(Id, url, name);
        dataNodeRepository.save(dataNode);
        //DataNode newNode = dataNodeRepository.findByName(name);
        //System.out.println("after new node: " + newNode.getName());
    }

    public void deleteDataNode(String name){
        //int n = dataNodeRepository.deleteById(Id);
        if(dataNodeRepository.findByName(name) != null)
            dataNodeRepository.deleteByName(name);
        System.out.println("finish delete");
    }

//    public void uploadToDataNode(){
//
//    }
}
