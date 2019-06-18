package czf.nju.namenode.controller;

//import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import czf.nju.namenode.service.DataNodeService;
import czf.nju.namenode.service.NameNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Logger;

/**
 * Restful接口
 */
@RestController
public class NameNodeController {

//    @Value("${block.size}")
//    private int SIZE;

//    @RequestMapping("/**")
//    public String home() {
//        return "Home NameNode";
//    }

    private Logger logger = Logger.getLogger(NameNodeController.class.getName());

    @RequestMapping("/hello")
    public String index() {
        return "Hello NameNode";
    }

    @Autowired
    private NameNodeService nameNodeService;

    /**
     * 获取请求，实际中是POSTMAN发送的
     */
    @Autowired
    private HttpServletRequest httpServletRequest;


    @GetMapping("/download")
    public String downloadFile(@RequestParam("fileName") String fileName){
        byte[] bytes = nameNodeService.downloadFile(fileName);
        return "Download Success!";
    }

    @PutMapping("/**")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "No File Selected!";
        }
        String uri = httpServletRequest.getRequestURI();

        try {
            uri = URLDecoder.decode(uri, "Utf-8");
            //return uri;
            if(uri.charAt(uri.length() - 1) != '/')
                uri += ('/' + file.getOriginalFilename());
            else
                uri += file.getOriginalFilename();
            //byte fileBytes[] = file.getBytes();
            logger.info("uploading... uri: " + uri);
            nameNodeService.uploadFile(file, uri);
            //String str = String.valueOf(fileBytes);
            //return str;
            //nameNodeService.uploadFile(fileBytes, uri);
            //return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload Failure";
        }


        //if(file == ""){
        //    return "No File Selected!";
        //}
        // Get the file and save it somewhere
        //byte[] bytes = file.getBytes();
        //nameNodeService.uploadFile(bytes);
        //nameNodeService.uploadFile(file);
        return "Upload Success!";
    }

    @DeleteMapping("/delete")
    public String deleteFile(){
        return "Delete Page";
    }
}
