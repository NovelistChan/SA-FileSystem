package czf.nju.namenode.service;

import czf.nju.namenode.domain.Directory;
import czf.nju.namenode.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理目录集
 */
@Service
public class DirectoryService {
    @Autowired
    private DirectoryRepository directoryRepository;

    public boolean isDirectory(String user) {
        List<Directory> directoryList = directoryRepository.findAll();
        boolean value = false;
        for (int i = 0; i < directoryList.size(); i++) {
            if (directoryList.get(i).getUser().equals(user)) {
                value = true;
                break;
            }
        }
        return value;
    }
}
