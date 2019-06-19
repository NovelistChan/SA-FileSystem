package czf.nju.namenode.service;

import czf.nju.namenode.domain.Directory;
import czf.nju.namenode.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * 处理目录集
 */
@Service
public class DirectoryService {
    @Autowired
    private DirectoryRepository directoryRepository;

    private Logger logger = Logger.getLogger(DirectoryService.class.getName());

    public boolean isDirectory(String user) {
        //List<Directory> directoryList = directoryRepository.findAll();
        boolean value = false;
        //for (int i = 0; i < directoryList.size(); i++) {
        //    if (directoryList.get(i).getUser().equals(user)) {
        //        value = true;
        //        break;
        //    }
        //}
        if (directoryRepository.findByUser(user) != null)
            value = true;
        if (!value)
            logger.info("no user found");
        return value;
    }

    public void saveDirectory(String user, String fileName) {
 //       List<Directory> directoryList = directoryRepository.findAll();
//        boolean value = false;
//        for (int i = 0; i < directoryList.size(); i++) {
//            if (directoryList.get(i).getUser().equals(user))
//                value = true;
//            break;
//        }
        boolean value = false;
        if (directoryRepository.findByUser(user) != null)
            value = true;
        if (!value) {
            Directory directory = new Directory();
            directory.setUser(user);
            directory.addFile(fileName);
            directory.generateUri();
            directory.setId(user);
            directoryRepository.save(directory);
        } else {
            Directory directory = directoryRepository.findByUser(user);
            directory.addFile(fileName);
            directory.generateUri();
            directoryRepository.save(directory);
        }
    }

    public String getDirectory(String user) {
        String menu = "";
        if (user.equals("")) {
            List<Directory> directoryList = directoryRepository.findAll();
            for (int i = 0; i < directoryList.size(); i++) {
                menu += directoryList.get(i).getUriList();
            }
        } else {
            Directory directory = directoryRepository.findByUser(user);
            if (directory == null)
                return "This user doesn't exist!";
            menu = directory.getUriList();
        }
        return menu;
    }

    //public boolean inDirectoryRepository
}
