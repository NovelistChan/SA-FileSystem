package czf.nju.namenode.controller;

import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import czf.nju.namenode.service.DataNodeService;
import czf.nju.namenode.service.NameNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


/**
 * Restful接口
 */
@RestController
public class NameNodeController {

    @Value("${block.size}")
    private int SIZE;

    @RequestMapping("/hello")
    public String index() {
        return "Hello NameNode";
    }

    @Autowired
    private NameNodeService nameNodeService;

    @GetMapping("/download")
    public String downloadFile(){
        return "Download Page";
    }

    @PutMapping("/upload")
    public String uploadFile(@RequestParam("file") String file){
        if (file.isEmpty()) {
            return "No File Selected!";
        }
        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        nameNodeService.uploadFile(bytes);

        return "Upload Success!";
    }

    @DeleteMapping("/delete")
    public String deleteFile(){
        return "Delete Page";
    }
}
