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

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public DataNode(String Id, String url){
        this.setId(Id);
        this.setUrl(url);
    }
}
