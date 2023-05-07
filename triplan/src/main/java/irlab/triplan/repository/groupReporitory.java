package irlab.triplan.repository;

import irlab.triplan.entity.group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface groupReporitory extends JpaRepository<group, Integer> {
    @Query(nativeQuery = true, value = "select g.group_id, g.group_name, g.group_code, g.group_pw, g.group_path from `group` g where g.group_id in (select gu.group_id from groupUser gu where gu.user_id = :userId);")
    List<group> findByGroup(Integer userId);
}
