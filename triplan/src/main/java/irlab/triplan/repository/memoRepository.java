package irlab.triplan.repository;

import irlab.triplan.entity.memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface memoRepository extends JpaRepository<memo, Integer> {
    @Query(nativeQuery = true, value = "insert into `classification` (trip_id, category_id, user_id, content, image_path, content_datetime, is_url, like_count) values (:trip_id, :category_id, :user_id, :content, :image_path, now(), 1, 0)")
    void classificationURL(Integer trip_id, Integer category_id, Integer user_id, String content, String image_path);
}
