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

    public void newDataNode(String Id, String url, int port){
        DataNode dataNode = new DataNode(Id, url, port);
        dataNodeRepository.save(dataNode);
    }

    public void deleteDataNode(String Id){
        dataNodeRepository.deleteById(Id);
    }


}
