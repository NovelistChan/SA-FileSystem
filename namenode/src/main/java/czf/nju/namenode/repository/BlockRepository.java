package czf.nju.namenode.repository;

import czf.nju.namenode.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 存储Block
 */
@Repository
public interface BlockRepository extends JpaRepository<Block, String> {
}
