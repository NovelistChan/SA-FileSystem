package czf.nju.namenode.repository;
import czf.nju.namenode.domain.DataNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 存储datanode
 */
@Repository
public interface DataNodeRepository extends JpaRepository<DataNode, String>{
}
