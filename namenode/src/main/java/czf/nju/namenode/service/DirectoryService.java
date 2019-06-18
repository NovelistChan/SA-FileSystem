package czf.nju.namenode.service;

import czf.nju.namenode.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理目录集
 */
@Service
public class DirectoryService {
    @Autowired
    private DirectoryRepository directoryRepository;

    public boolean isDirectory(String uri) {

        return false;
    }
}
