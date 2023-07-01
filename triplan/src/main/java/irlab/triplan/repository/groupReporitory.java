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

    @Query(nativeQuery = true, value = "insert into `group` (group_name, group_pw, group_code, group_path) value(:group_name, :group_pw, :group_code, :group_path);")
    void CreateGroup(String group_name, String group_pw, String group_code, String group_path);

    @Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID();")
    Integer selectGroupId();

    @Query(nativeQuery = true, value = "select group_code from `group`;")
    List<String> selectCode();

    @Query(nativeQuery = true, value = "update `group` set group_name = :group_name where group_id = :group_id")
    void updateGroupName(Integer group_id, String group_name);

    @Query(nativeQuery = true, value = "update `group` set group_path = :group_path where group_id = :group_id")
    void updateGroupPath(Integer group_id, String group_path);

    @Query(nativeQuery = true, value = "update `group` set group_name = :group_name, group_path = :result where group_id = :group_id")
    void updateGroup(Integer group_id, String group_name, String result);
}
