package czf.nju.namenode.service;

import czf.nju.namenode.repository.BlockRepository;
import czf.nju.namenode.repository.DataNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 上传、分块、下载
 */
@Service
public class NameNodeService {
    @Autowired
    BlockRepository blockRepository;

    @Autowired
    DataNodeRepository dataNodeRepository;
}
