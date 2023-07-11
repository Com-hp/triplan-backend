package irlab.triplan.repository;

import irlab.triplan.entity.trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface tripRepository extends JpaRepository<trip, Integer> {
    @Query(value = "select t from trip t where t.tGroup.group_id = :group_id")
    List<trip> findGroupInTrip(Integer group_id);

    @Query(nativeQuery = true, value = "select * from trip t where t.group_id = :group_id and NOW() < end_date;")
    List<trip> findGroupDate(Integer group_id);

    @Query(nativeQuery = true, value = "insert into trip(group_id, trip_path) value (:group_id, :newFilename);")
    void InsertTrip(Integer group_id, String newFilename);
}
