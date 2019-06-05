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
        logger.info("新增节点" + event.getInstanceInfo().getAppName());
        logger.info("url: " + event.getInstanceInfo().getIPAddr());

        dataNodeService.newDataNode(event.getInstanceInfo().getInstanceId(), event.getInstanceInfo().getIPAddr(), event.getInstanceInfo().getPort());
    }

    /**
     * 某个datanode被删除或是失效
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        logger.info(event.getServerId() + "号datanode已下线");
        dataNodeService.deleteDataNode(event.getServerId());
    }

    /**
     * 注册服务启动
     * @param event
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        logger.info("注册中心 启动");
    }

    /**
     * Eureka server启动
     * @param event
     */
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        logger.info("Eureka Server 启动");
    }

    /**
     * 服务续约
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        logger.info(event.getAppName() + " 服务进行续约");
    }

}
