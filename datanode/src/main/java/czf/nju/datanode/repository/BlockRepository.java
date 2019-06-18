package czf.nju.datanode.repository;

import czf.nju.datanode.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * 存储Block
 */
@Repository
public interface BlockRepository extends JpaRepository<Block, String> {

    @Modifying(flushAutomatically = true)
    @Transactional
    public void deleteByFileName(String fileName);
}
