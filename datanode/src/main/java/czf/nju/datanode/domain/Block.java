package czf.nju.datanode.domain;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;

/**
 * 定义一个文件块
 */
@Entity
public class Block extends BaseEntity{
    @Value("${block.size}")
    private int SIZE;

    @Value("${block.transcript}")
    private int TRANSCRIPT;
    /**
     * 块数据
     */
    private byte[] data;
    /**
     * Block所属的datanode编号
     */
    private String dataNodeId;

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() { return this.fileName; }

    public void setData(byte[] src){
        this.data = src;
    }

    public byte[] getData(){
        return this.data;
    }

    public void setDataNodeId(String dataNodeId){
        this.dataNodeId = dataNodeId;
    }

    public String getDataNodeId() {
        return this.dataNodeId;
    }

    public Block(String Id, byte[] data, String dataNodeId, String fileName){
        this.setId(Id);
        this.setData(data);
        this.setDataNodeId(dataNodeId);
        this.setFileName(fileName);
    }

    public Block(){

    }
}
