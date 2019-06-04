package czf.nju.namenode.service;

import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 上传、分块、下载
 */
@Service
public class NameNodeService {
    @Value("${block.size}")
    private int SIZE;

    @Value("${block.transcript}")
    private int TRANSCRIPT;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    DataNodeRepository dataNodeRepository;

    public void uploadFile(byte file[]){

    }
}
