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

    @GetMapping("/**")
    public @ResponseBody String downloadFile(){
        String uri = httpServletRequest.getRequestURI();
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "Judge Directory Error!";
        }
        logger.info("uri: " + uri);
        if (uri.equals("/eureka/status"))
            uri = "";
        logger.info("after transfer: " + uri);
        //判断是否是目录
        if (nameNodeService.isDirectory(uri)) {
            logger.info("processing directory...");
            String directoryList = nameNodeService.getDirectory(uri);
            return directoryList;
        } else {
            logger.info("downloading file...");
            String file = nameNodeService.downloadFile(uri);
            return file;
        }
        //return "Download Success!";
    }

    @PutMapping("/**")
    public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "No File Selected!";
        }
        String uri = httpServletRequest.getRequestURI();

        try {
            uri = URLDecoder.decode(uri, "Utf-8");
            //return uri;
            //if(uri.charAt(uri.length() - 1) != '/')
            //    uri += ('/' + file.getOriginalFilename());
            //else
            //    uri += file.getOriginalFilename();
            //byte fileBytes[] = file.getBytes();
            logger.info("uploading... uri: " + uri);
            nameNodeService.saveDirectory(uri, file);
            nameNodeService.uploadFile(file);
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

    @DeleteMapping("/**")
    public @ResponseBody String deleteFile(){
        String uri = httpServletRequest.getRequestURI();
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "Judge Delete Error!";
        }
        logger.info("uri: " + uri);
        if (uri.equals("/eureka/status"))
            uri = "";
        logger.info("after transfer: " + uri);
        if (nameNodeService.isDirectory(uri)) {
            logger.info("This is a Directory!");
            return "Can't delete a Directory!";
        } else {
            nameNodeService.deleteFile(uri);
        }
        return "Delete Success!";
    }
}
