package czf.nju.datanode.service;

import czf.nju.datanode.domain.Block;
import czf.nju.datanode.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Block的创建、分配
 */
@Service
public class BlockService {
    @Autowired
    BlockRepository blockRepository;


    public void newBlock(String Id, byte data[], String fileName){
        Block block = new Block(Id, data, fileName);
        blockRepository.save(block);
    }

    public void deleteBlock(String fileName) {
        blockRepository.deleteByFileName(fileName);
    }

}
