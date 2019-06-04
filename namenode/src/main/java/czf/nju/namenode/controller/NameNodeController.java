package czf.nju.namenode.controller;

import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Restful接口
 */
@RestController
public class NameNodeController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello NataNode";
    }

    @GetMapping("/**")
    public void downloadFile(){

    }

    @PostMapping("/**")
    public void uploadFile(){

    }

    @DeleteMapping("/**")
    public void deleteFile(){

    }
}
