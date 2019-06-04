package czf.nju.namenode.listener;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;
import czf.nju.namenode.service.*;

/**
 * 监听datanode节点
 */
@Component
public class EurekaListener {
    private Logger logger = Logger.getLogger(EurekaListener.class.getName());
    private DataNodeService dataNodeService;

    @Autowired
    public EurekaListener(DataNodeService dataNodeService) {
        this.dataNodeService = dataNodeService;
    }

    /**
     * 新注册一个datanode
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        logger.info("新增" + event.getInstanceInfo().getInstanceId() + "号datanode节点");
        dataNodeService.newDataNode(event.getInstanceInfo().getInstanceId(), event.getInstanceInfo().getIPAddr());
    }

    /**
     * 某个datanode被删除或是失效
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        logger.info(event.getServerId() + "号datanode已被删除");
        dataNodeService.deleteDataNode(event.getServerId());
    }

    /**
     * 注册服务启动
     * @param event
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        System.err.println("注册中心 启动");
    }

    /**
     * Eureka server启动
     * @param event
     */
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        System.err.println("Eureka Server 启动");
    }
}
