package czf.nju.namenode.service;

import com.sun.org.apache.xpath.internal.operations.Mult;
import czf.nju.namenode.domain.Block;
import czf.nju.namenode.domain.DataNode;
//import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.domain.Directory;
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
    private DataNodeRepository dataNodeRepository;

    @Autowired
    private BlockService blockService;

//    @Autowired
//    private DataNodeService dataNodeService;

    @Autowired
    private DirectoryService directoryService;

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
    public void uploadFile(MultipartFile file) throws Exception{
        //saveDirectory(uri);
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
                blockService.newBlock(blockId, save, fileName, url, i);
                //dataNodeService.uploadToDataNode();
                // dataNodeRepository.save(dataNodeList.get(0));
            }
        }
        for(int i = 0; i < dataNodeList.size(); i++)
            dataNodeRepository.save(dataNodeList.get(i));
        checkDataNodeInfo();
    }


    public String downloadFile(String uri) {
        String path = uri.substring(1);

        String res = null;
        return res;
    }

    /**
     * 判断是目录还是文件
     * @param uri
     * @return
     */
    public boolean isDirectory(String uri) {
        if (uri.equals("")) return true;
        else {
            String path = uri.substring(1);
            if (path.charAt(path.length() - 1) != '/')
                path += '/';
            int index = path.indexOf('/');
            String user = "";
            if (index >= 0)
                user = path.substring(0, index);
            else user = path;
            logger.info("user detected: " + user);
            String fileName = path.substring(index + 1);
            if (!fileName.equals("")) {
                logger.info("fileName: " + fileName);
                return false;
            }
            return directoryService.isDirectory(user);
            //return false;
        }
    }

    public String generateDirectoryList() {
        return "Hello Directory";
    }

    /**
     * 从uri中截取user字段
     * @param uri
     * @return
     */
    public String getUser(String uri) {
        String path = uri.substring(1);
        if (path.charAt(path.length() - 1) != '/')
            path += '/';
        int index = path.indexOf('/');
        String user = "";
        if (index >= 0)
            user = path.substring(0, index);
        return user;
    }

    /**
     * 保存当前uri至仓库，以备以后查询
     * @param uri
     * @param file
     */
    public void saveDirectory(String uri, MultipartFile file) {
        String user = getUser(uri);
        String fileName = file.getOriginalFilename();
        directoryService.saveDirectory(user, fileName);
    }

    /**
     * 获取系统目录
     * @param uri
     * @return
     */
    public String getDirectory(String uri) {
        if (uri.equals(""))
            return directoryService.getDirectory(uri);
        String user = getUser(uri);
        return directoryService.getDirectory(user);
    }
}
