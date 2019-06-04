package czf.nju.datanode.datanode;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataNodeController {
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello DataNode";
    }
}
