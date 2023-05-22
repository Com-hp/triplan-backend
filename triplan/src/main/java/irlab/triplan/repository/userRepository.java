package irlab.triplan.repository;

import irlab.triplan.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<user, Integer> {
    @Query(nativeQuery = true, value = "insert into `user` (user_name, access_token, default_id) VALUES (:user_name, :access_token, :default_id)")
    void CreateUser(String user_name, String access_token, Integer default_id);

    @Query(nativeQuery = true, value = "select * from `user` where access_token = :access_token ")
    user findByAccessToken(String access_token);
    @Query(nativeQuery = true, value = "select count(user_id) as cnt from `user` where access_token = :access_token")
    Integer countUser(String access_token);
}
