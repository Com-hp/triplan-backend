package irlab.triplan.repository;

import irlab.triplan.entity.group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface groupReporitory extends JpaRepository<group, Integer> {
    @Query(nativeQuery = true, value = "select g.group_id, g.group_name, g.group_code, g.group_pw, g.group_path from `group` g where g.group_id in (select gu.group_id from groupUser gu where gu.user_id = :user_id);")
    List<group> findByGroup(Integer user_id);

    @Query(nativeQuery = true, value = "insert into `group` (group_name, group_pw, group_code) value(:group_name, :group_pw, :group_code);")
    void CreateGroup(String group_name, String group_pw, String group_code);

    @Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID();")
    Integer selectGroupId();

    @Query(nativeQuery = true, value = "select group_code from `group`;")
    List<String> selectCode();
}
