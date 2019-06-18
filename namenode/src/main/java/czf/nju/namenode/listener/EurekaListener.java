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
        this.lastDownNode = null;
        this.dataNodeService = dataNodeService;
    }

    /**
     * 记录上一个下线的节点
     */
    private String lastDownNode;

    /**
     * 新注册一个datanode
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        if (event.getInstanceInfo().getStatus() != InstanceInfo.InstanceStatus.UP) return;
        logger.info("新增节点: " + event.getInstanceInfo().getAppName());
        logger.info("url: " + event.getInstanceInfo().getIPAddr());
        logger.info("port: " + event.getInstanceInfo().getPort());
        logger.info("homepage: " + event.getInstanceInfo().getHomePageUrl());
        logger.info("Id: " + event.getInstanceInfo().getInstanceId());
        logger.info("name: " + event.getInstanceInfo().getAppName());
        dataNodeService.newDataNode(event.getInstanceInfo().getInstanceId(), event.getInstanceInfo().getHomePageUrl()
                , event.getInstanceInfo().getAppName());
    }
    /**
     * 某个datanode被删除或是失效
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        logger.info(event.getAppName() + "已下线");
        if(this.lastDownNode == null)
            this.lastDownNode = event.getAppName();
        //else if(this.lastDownNode == event.getAppName())
        //    return;
        dataNodeService.deleteDataNode(event.getAppName());
        this.lastDownNode = event.getAppName();
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
