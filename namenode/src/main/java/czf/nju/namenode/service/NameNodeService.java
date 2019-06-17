package czf.nju.namenode.service;

import czf.nju.namenode.domain.DataNode;
import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

/**
 * 上传、分块、下载
 */
@Service
public class NameNodeService {
    @Value("${block.size}")
    private int SIZE;

    @Value("${block.transcript}")
    private int TRANSCRIPT;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    DataNodeRepository dataNodeRepository;

    @Autowired
    BlockService blockService;

    private Logger logger = Logger.getLogger(NameNodeService.class.getName());

    void checkDataNodeInfo() {
        List<DataNode> dataNodeList = dataNodeRepository.findAll();
        for(int i = 0; i < dataNodeList.size(); i++){
            logger.info(dataNodeList.get(i).getUrl() + dataNodeList.get(i).getPort() + dataNodeList.get(i).getBlockInUse());
        }
    }

    /**
     * 上传文件，分Block并存到各个DataNode
     * 负载均衡：使用Block数最少最优先
     * @param file
     */
    public void uploadFile(byte file[]){
        String fileName = String.valueOf(file[0] + file[1] + file[2]);
        int blockNum = file.length / SIZE + 1;
        //如果文件长度是块大小的倍数，只分length/SIZE块即可，不必+1
        if(file.length == SIZE * (blockNum - 1))
            blockNum--;
        System.out.println(blockNum + "blocks in file: " + file);
        List<DataNode> dataNodeList = dataNodeRepository.findAll();
        checkDataNodeInfo();
        //负载均衡
        //选取使用block数最少的node存储一个新的block
        //i个block每个有TRANSCRIPT个副本
        for(int i = 0; i < blockNum; i++){
            for(int j = 0; j < TRANSCRIPT; j++){
                Collections.sort(dataNodeList, new Comparator<DataNode>() {
                    @Override
                    public int compare(DataNode o1, DataNode o2) {
                        return o1.getBlockInUse() - o2.getBlockInUse();
                    }
                });
                dataNodeList.get(0).incBlockInUse();
                byte save[] = new byte[SIZE];
                for(int k = 0; k < SIZE; k++) {
                    if(k + i * blockNum >= file.length) break;
                    else{
                        save[k] = file[k + blockNum * i];
                    }
                }
                String dataNodeId = dataNodeList.get(0).getId();
                String blockId = dataNodeList.get(0).getId() + String.valueOf(dataNodeList.get(0).getBlockInUse());
                blockService.newBlock(blockId, save, dataNodeId, fileName);
                // dataNodeRepository.save(dataNodeList.get(0));
            }
        }
        for(int i = 0; i < dataNodeList.size(); i++)
            dataNodeRepository.save(dataNodeList.get(i));
        checkDataNodeInfo();
    }


    public byte[] downloadFile(String fileName) {
        byte[] res = null;
        return res;
    }
}
