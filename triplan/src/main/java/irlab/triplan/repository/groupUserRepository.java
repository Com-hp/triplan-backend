package irlab.triplan.repository;

import irlab.triplan.entity.groupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface groupUserRepository extends JpaRepository<groupUser, Integer> {
    @Query(nativeQuery = true, value = "insert into groupUser (user_id, group_id) value(:user_id, :group_id);")
    void createGroupUser(Integer user_id, Integer group_id);
}
