package czf.nju.namenode.domain;


import javax.persistence.Entity;

/**
 * 定义文件uri,用来判断是查看目录还是取文件
 */
@Entity
public class Directory extends BaseEntity{
    /**
     * 目录层
     */
    String user;

    /**
     *该目录下的文件
     */
    String[] fileNameList;

    /**
     * 目录和文件合成uri
     */
    String[] uriList;

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void addFile(String fileName) {
        if (this.fileNameList == null){
            String[] newList = {fileName};
            this.fileNameList = newList;
        } else {
            String[] newList = new String[this.fileNameList.length + 1];
            for(int i = 0; i < this.fileNameList.length; i++)
                newList[i] = this.fileNameList[i];
            newList[this.fileNameList.length] = fileName;
            this.fileNameList = newList;
        }
    }

    public void deleteFile(String fileName) {
        if (this.fileNameList == null)
            return;
        boolean exist = false;
        for (int i = 0; i < this.fileNameList.length; i++){
            if(this.fileNameList[i].equals(fileName)){
                exist = true;
                break;
            }
        }
        if (!exist) return;
        String[] newList = new String[fileNameList.length - 1];
        int cnt = 0;
        for (int i = 0; i < this.fileNameList.length; i++){
            if(!this.fileNameList[i].equals(fileName)){
                newList[cnt] = this.fileNameList[i];
                cnt++;
            }
        }
        this.fileNameList = newList;
        generateUri();
    }

    Directory(){
        this.user = null;
        this.fileNameList = null;
        this.uriList = null;
    }

    public void generateUri(){
        String[] newUriList = new String[this.fileNameList.length];
        for (int i = 0; i < this.fileNameList.length; i++)
            newUriList[i] = this.user + "/" + this.fileNameList[i];
        this.uriList = newUriList;
    }
}
