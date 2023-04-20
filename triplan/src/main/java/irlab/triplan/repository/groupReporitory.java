package irlab.triplan.repository;

import irlab.triplan.entity.group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface groupReporitory extends JpaRepository<group, Integer> {

}
