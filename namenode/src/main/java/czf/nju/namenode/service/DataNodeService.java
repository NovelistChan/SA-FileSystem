package czf.nju.namenode.service;

import czf.nju.namenode.domain.DataNode;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * datanode的创建等一系列操作
 */
@Service
public class DataNodeService {
    @Autowired
    private DataNodeRepository dataNodeRepository;

    @Autowired
    private BlockService blockService;

    public void newDataNode(String Id, String url, String name) {
        DataNode dataNode = new DataNode(Id, url, name);
        dataNodeRepository.save(dataNode);
        //DataNode newNode = dataNodeRepository.findByName(name);
        //System.out.println("after new node: " + newNode.getName());
    }

    public void deleteDataNode(String name) {
        //int n = dataNodeRepository.deleteById(Id);
        if(dataNodeRepository.findByName(name) != null)
            dataNodeRepository.deleteByName(name);
        System.out.println("finish delete");
    }

    public void deleteFile(String fileName) {
        List<DataNode> dataNodeList = dataNodeRepository.findAll();
        int blockNum = 0;
        for (int i = 0; i < dataNodeList.size(); i++) {
            if (dataNodeList.get(i).getFileNameToBlockNum().containsKey(fileName)) {
                blockNum = dataNodeList.get(i).getFileNameToBlockNum().get(fileName);
                dataNodeList.get(i).deleteFileBlock(fileName);
                for (int j = 0; j < blockNum; j++) {
                    String fileBlock = fileName + String.valueOf(j);
                    if (dataNodeList.get(i).getFileList().contains(fileBlock)) {
                        dataNodeList.get(i).deleteFile(fileBlock);
                        String url = dataNodeList.get(i).getUrl();
                        blockService.deleteFile(fileName, url, i);
                    }
                }
                //break;
            }
        }
        for (int i = 0; i < dataNodeList.size(); i++) {
            dataNodeRepository.save(dataNodeList.get(i));
        }
    }

//    public void uploadToDataNode(){
//
//    }
}
