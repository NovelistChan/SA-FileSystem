package czf.nju.namenode.domain;

import org.hibernate.usertype.UserCollectionType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 定义dataNode节点
 */
@Entity
public class DataNode extends BaseEntity{
    /**
     * 区分dataNode，包含了IP+Port
     */
    private String url;
 //   private int port;
    private int blockInUse;
    private String name;
    /**
     * 文件名对应文件总块数
     */
    @ElementCollection
    private Map<String, Integer> fileNameToBlockNum;

    @ElementCollection
    private Set<String> fileList;

    public void setName(String name) { this.name = name; }

    public String getName() { return this.name; }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

 //   public void setPort(int port){
 //       this.port = port;
 //   }

 //   public int getPort(){
 //       return this.port;
 //   }

    public void incBlockInUse() {
        this.blockInUse++;
    }

    public void decBlockInUse() {
        this.blockInUse--;
    }

    public void setBlockInUse(int blockInUse) {
        this.blockInUse = blockInUse;
    }

    public int getBlockInUse() {
        return this.blockInUse;
    }

    public DataNode(String Id, String url, String name){
        this.setId(Id);
        this.setUrl(url);
   //     this.setPort(port);
        this.setName(name);
        this.blockInUse = 0;
       // this.fileNameToPartId = new LinkedMultiValueMap<>();
        this.fileList = new HashSet<>();
        this.fileNameToBlockNum = new HashMap<>();
    }

//    public void addFileChain(String fileName, Integer partId) {
//        this.fileNameToPartId.add(fileName, partId);
//    }
//
//    public void deleteFileChain(String fileName) {
//        this.fileNameToPartId.remove(fileName);
//    }

    public void addFileBlock(String fileName, Integer blockNum) {
        this.fileNameToBlockNum.put(fileName, blockNum);
    }

    public void deleteFileBlock(String fileName) {
        this.fileNameToBlockNum.remove(fileName);
    }

//    public MultiValueMap<String, Integer> getFileNameToPartId() {
//        return this.fileNameToPartId;
//    }

    public Map<String, Integer> getFileNameToBlockNum() {
        return this.fileNameToBlockNum;
    }

    public void addFile(String fileNameBlock) {
        this.fileList.add(fileNameBlock);
    }

    public void deleteFile(String fileNameBlock) {
        this.fileList.remove(fileNameBlock);
    }

    public Set<String> getFileList() {
        return this.fileList;
    }

    public DataNode(){

    }
}
