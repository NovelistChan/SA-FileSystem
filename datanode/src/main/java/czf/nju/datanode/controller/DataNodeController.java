package czf.nju.datanode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataNodeController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello DataNode";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String upload(@RequestParam(value = "id")String id, @RequestParam(value = "data")byte[] data,
                         @RequestParam(value = "fileName")String fileName) {
        return "Upload Success!";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String download(@RequestParam(value = "fileName")String fileName) {

        return "Download Success!";
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam(value = "fileName")String fileName) {

        return "Delete Success!";
    }
}
