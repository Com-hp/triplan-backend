package irlab.triplan.repository;

import irlab.triplan.entity.group;
import irlab.triplan.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Query(nativeQuery = true, value = "SELECT u.user_id, u.default_id, u.user_name FROM `user`  u WHERE user_id in (SELECT gu.user_id from groupUser gu WHERE gu.group_id = :group_id)")
    List<Map<String, Object>> findByMemeber(Integer group_id);

    @Query(nativeQuery = true, value = "SELECT g.group_pw,COUNT(*) as cnt, COUNT(CASE WHEN user_id = :user_id THEN 1 END) as exist, g.group_path FROM groupUser gu left join `group` g on g.group_id = gu.group_id WHERE gu.group_id  = :group_id")
    Map<String, Object> findByCheck(Integer user_id, Integer group_id);

    @Query(nativeQuery = true, value = "INSERT into groupUser (group_id, user_id)  value(:group_id, :user_id);")
    void InsertJoin(Integer group_id, Integer user_id);

    @Query(nativeQuery = true, value = "DELETE from groupUser where user_id = :user_id and group_id = :group_id")
    void DeleteGroupUser(Integer group_id, Integer user_id);

    @Query(nativeQuery = true, value = "select count(gu.group_id) from groupUser gu where gu.group_id = :group_id")
    Integer ExistGroup(Integer group_id);

    @Query(nativeQuery = true, value = "select group_name from `group` where group_code = :group_code")
    String findByGroupName(String group_code);

    @Query(nativeQuery = true, value = "delete from `group` where group_id = :group_id")
    void DeleteGroup(Integer group_id);
}