package czf.nju.datanode.service;

import czf.nju.datanode.domain.Block;
import czf.nju.datanode.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Block的创建、分配
 */
@Service
public class BlockService {
    @Autowired
    BlockRepository blockRepository;


    public void newBlock(String Id, byte data[], String fileName, int partId){
        Block block = new Block(Id, data, fileName, partId);
        blockRepository.save(block);
    }

    public void deleteBlock(String fileName, Integer partId) {
        //blockRepository.deleteByFileName(fileName);
        List<Block> blockList = blockRepository.findAll();
        String id = "";
        for (int i = 0; i < blockList.size(); i++) {
            if (blockList.get(i).getFileName().equals(fileName) && blockList.get(i).getPartId() == partId) {
                id = blockList.get(i).getId();
                break;
            }
        }
        blockRepository.deleteById(id);
    }

    public String getBlockData(String fileName, int partId){
        String res = "";
        List<Block> blockList = blockRepository.findAll();
        for (int i = 0; i < blockList.size(); i++) {
            if (blockList.get(i).getFileName().equals(fileName) && blockList.get(i).getPartId() == partId) {
                res += String.valueOf(blockList.get(i).getData());
                break;
            }
        }
        return res;
    }

}
