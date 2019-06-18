package czf.nju.namenode.repository;
import czf.nju.namenode.domain.DataNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * 存储datanode
 */
@Repository
public interface DataNodeRepository extends JpaRepository<DataNode, String>{
 //   DataNode findById(String Id);
    DataNode findByName(String name);

    @Modifying(flushAutomatically = true)
    @Transactional
    void deleteByName(String name);
}
