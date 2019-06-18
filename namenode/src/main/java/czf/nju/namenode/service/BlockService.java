package czf.nju.namenode.service;

import czf.nju.namenode.domain.Block;
import czf.nju.namenode.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Block的创建、分配
 */
@Service
public class BlockService {
    @Autowired
    BlockRepository blockRepository;

    public void newBlock(String Id, byte data[], String dataNodeId, String fileName, String url){
        Block block = new Block(Id, data, dataNodeId, fileName);
        blockRepository.save(block);
        uploadBlock(block, url);
    }

    public void deleteBlock(String Id) { blockRepository.deleteById(Id); }

    public void uploadBlock(Block block, String url) {

    }

}
