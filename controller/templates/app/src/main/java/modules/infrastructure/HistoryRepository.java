package <%= packageName %>.modules.<%= moduleName %>.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface <%= entityName %>HistoryRepository extends JpaRepository<<%= entityName %>HistoryOrmEntity, UUID> {

	public List<<%= entityName %>HistoryOrmEntity> findByParentIdOrderBySequenceDesc(UUID parentId);
	
}