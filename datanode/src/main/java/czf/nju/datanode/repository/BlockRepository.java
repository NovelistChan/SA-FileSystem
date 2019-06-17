package czf.nju.datanode.repository;

import czf.nju.datanode.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 存储Block
 */
@Repository
public interface BlockRepository extends JpaRepository<Block, String> {
}
