package czf.nju.namenode.service;

import com.sun.org.apache.xpath.internal.operations.Mult;
import czf.nju.namenode.domain.Block;
import czf.nju.namenode.domain.DataNode;
//import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

    //@Autowired
    //BlockRepository blockRepository;

    @Autowired
    DataNodeRepository dataNodeRepository;

    @Autowired
    BlockService blockService;

    @Autowired
    DataNodeService dataNodeService;

    private Logger logger = Logger.getLogger(NameNodeService.class.getName());

    void checkDataNodeInfo() {
        List<DataNode> dataNodeList = dataNodeRepository.findAll();
        for(int i = 0; i < dataNodeList.size(); i++){
            logger.info(dataNodeList.get(i).getUrl() + dataNodeList.get(i).getBlockInUse());
        }
    }

    /**
     * 上传文件，分Block并存到各个DataNode
     * 负载均衡：使用Block数最少最优先
     * @param file
     */
    public void uploadFile(MultipartFile file, String uri) throws Exception{
        //String fileName = String.valueOf(file[0] + file[1] + file[2]);
        byte fileBytes[] = file.getBytes();
        String fileName = file.getOriginalFilename();
        int blockNum = fileBytes.length / SIZE + 1;
        //如果文件长度是块大小的倍数，只分length/SIZE块即可，不必+1
        if(fileBytes.length == SIZE * (blockNum - 1))
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
                    if(k + i * blockNum >= fileBytes.length) break;
                    else{
                        save[k] = fileBytes[k + blockNum * i];
                    }
                }
                String dataNodeId = dataNodeList.get(0).getId();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //用DataNodeId和当前时间生成BlockId
                String blockId = dataNodeList.get(0).getId() + simpleDateFormat.format(new Date());
                String url = dataNodeList.get(0).getUrl();
                System.out.println("Choose DataNode: " + url);
                blockService.newBlock(blockId, save, fileName, url);
                //dataNodeService.uploadToDataNode();
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

    public boolean isDirectory(String path) {
        return false;
    }
}
