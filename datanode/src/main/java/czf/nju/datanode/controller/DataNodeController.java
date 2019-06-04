package czf.nju.datanode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataNodeController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello DataNode";
    }
}
