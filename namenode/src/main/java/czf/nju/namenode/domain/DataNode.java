package czf.nju.namenode.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 定义datanode节点
 */
@Entity
public class DataNode extends BaseEntity{
    /**
     * 区分datanode
     */
    private String url;
    private int port;
    private int blockInUse;

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        return this.port;
    }

    public void incBlockInUse() {
        this.blockInUse++;
    }

    public void setBlockInUse(int blockInUse) {
        this.blockInUse = blockInUse;
    }

    public int getBlockInUse() {
        return this.blockInUse;
    }

    public DataNode(String Id, String url, int port){
        this.setId(Id);
        this.setUrl(url);
        this.setPort(port);
        this.blockInUse = 0;
    }

    public DataNode(){

    }
}
