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
    public String upload(@RequestParam(value = "file")MultipartFile file, @RequestParam(value = "id")String id) {
        logger.info("Now uploading: " + file.getOriginalFilename());
        logger.info("BlockId: " + id);
        //logger.info("Data: " + file.getBytes());
        try{
            String fileName = file.getOriginalFilename();
            byte data[] = file.getBytes();
            logger.info("Len: " + data.length);
            logger.info("Data: " + data);
            blockService.newBlock(id, data, fileName);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("upload failure in datanode");
        }

        return "Upload Success!";
    }

    @GetMapping("/download")
    public String download(@RequestParam(value = "fileName")String fileName) {

        return "Download Success!";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(value = "fileName")String fileName) {
        blockService.deleteBlock(fileName);
        return "Delete Success!";
    }
}
