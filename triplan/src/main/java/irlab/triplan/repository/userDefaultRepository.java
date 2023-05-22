package irlab.triplan.repository;

import irlab.triplan.entity.userDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface userDefaultRepository extends JpaRepository<userDefault, Integer> {
    @Query(nativeQuery = true, value = "select * from `default` d order by d.defalut_id;")
    List<userDefault> findAll();
}