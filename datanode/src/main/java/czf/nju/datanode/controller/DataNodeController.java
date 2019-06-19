package czf.nju.datanode.controller;

import czf.nju.datanode.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
public class DataNodeController {
    @Autowired
    private BlockService blockService;

    private Logger logger = Logger.getLogger(DataNodeController.class.getName());

    @RequestMapping("/hello")
    public String index() {
        return "Hello DataNode";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file")MultipartFile file, @RequestParam(value = "id")String id,
                         @RequestParam(value = "partId")Integer partId) {
        logger.info("Now uploading: " + file.getOriginalFilename());
        logger.info("BlockId: " + id);
        //logger.info("Data: " + file.getBytes());
        try{
            String fileName = file.getOriginalFilename();
            byte data[] = file.getBytes();
            logger.info("Len: " + data.length);
            logger.info("Data: " + data);
            logger.info("partId: " + partId);
            blockService.newBlock(id, data, fileName, partId);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("upload failure in datanode");
        }

        return "Upload Success!";
    }

    @PostMapping("/download")
    public String download(@RequestParam(value = "fileName")String fileName, @RequestParam(value = "partId")Integer partId) {
        logger.info("Receive download request...");
        logger.info("fileName: " + fileName);
        logger.info("filePart" + partId);
        String res = blockService.getBlockData(fileName, partId);
        return res;
        //return "Download Success!";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "fileName")String fileName) {
        blockService.deleteBlock(fileName);
        return "Delete Success!";
    }
}
