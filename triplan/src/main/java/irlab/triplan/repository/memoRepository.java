package irlab.triplan.repository;

import irlab.triplan.entity.memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface memoRepository extends JpaRepository<memo, Integer> {
    @Query(nativeQuery = true, value = "select c.trip_id, c.classification_id, c.user_id, u.user_name, c.content, c.image_path, c.content_datetime, c.is_url, c.like_count, c.category_id, c2.class from classification c\n" +
            "inner join `user` u on u.user_id = c.user_id " +
            "inner join category c2 on c2.category_id = c.category_id " +
            "where c.trip_id = :trip_id")
    List<memo> findClass(Integer trip_id);
}
