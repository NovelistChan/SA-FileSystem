package czf.nju.namenode.repository;

import czf.nju.namenode.domain.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * 存储文件uri
 */
@Repository
public interface DirectoryRepository extends JpaRepository<Directory, String> {
    public Directory findByUser(String user);
    @Modifying(flushAutomatically = true)
    @Transactional
    public void deleteByUser(String user);
}
