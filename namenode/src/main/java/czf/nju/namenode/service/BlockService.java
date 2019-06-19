package czf.nju.namenode.service;

import czf.nju.namenode.domain.Block;
import czf.nju.namenode.domain.DataNode;
//import czf.nju.namenode.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Block的创建、分配
 */
@Service
public class BlockService {
//    @Autowired
//    BlockRepository blockRepository;

    private Logger logger = Logger.getLogger(BlockService.class.getName());

    public void newBlock(String Id, byte data[], String fileName, String url, int partId){
        Block block = new Block(Id, data, fileName, partId);
       // blockRepository.save(block);
        uploadBlock(block, url);
    }

    //public void deleteBlock(String Id) { blockRepository.deleteById(Id); }

    public void uploadBlock(Block block, String url) {
        RestTemplate restTemplate = new RestTemplate();
        //Map<String, Object> param = new HashMap<>();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        ByteArrayResource byteArrayResource = new ByteArrayResource(block.getData()){
            @Override
            public String getFilename() {
                return block.getFileName();
            }
        };
        param.add("file", byteArrayResource);
        param.add("id", block.getId());
        param.add("partId", block.getPartId());
        logger.info("id: " + block.getId());
//        param.put("data", block.getData());
//        logger.info("data: " + block.getData().toString());
//        param.put("fileName", block.getFileName());
//        logger.info("fileName: " + block.getFileName());
        String requestUrl = url + "upload/";
        logger.info("RequestUrl: " + requestUrl);
        restTemplate.postForObject(requestUrl, param, String.class);
    }


    public String downloadBlock(String fileName, Integer partId, String url) {
        String res = "";
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = url + "download/";
        //Map<String, Object> param = new HashMap<>();
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("fileName", fileName);
        param.add("partId", partId);
        logger.info("Now request Downloading...");
        logger.info("fileName: " + fileName);
        logger.info("filePart: " + partId);
        logger.info("requestUrl: " + requestUrl);
        res += restTemplate.postForObject(requestUrl, param, String.class);
        return res;
    }

    public void deleteFile(String fileName) {

    }
}
