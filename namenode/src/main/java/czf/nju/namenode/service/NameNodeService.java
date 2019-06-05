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

    public void uploadFile(byte file[]){
        int blockNum = file.length / SIZE + 1;
        //如果文件长度是块大小的倍数，只分length/SIZE块即可，不必+1
        if(file.length == SIZE * (blockNum - 1))
            blockNum--;
        System.out.println(blockNum + "\n" + file);
        List<DataNode> dataNodeList = dataNodeRepository.findAll();
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
               // dataNodeRepository.save(dataNodeList.get(0));
            }
        }
        for(int i = 0; i < dataNodeList.size(); i++)
            dataNodeRepository.save(dataNodeList.get(i));
    }
}
