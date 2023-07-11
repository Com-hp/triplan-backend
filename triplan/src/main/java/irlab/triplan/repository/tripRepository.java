package irlab.triplan.repository;

import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface tripRepository extends JpaRepository<trip, Integer> {
    @Query(value = "select t from trip t where t.tGroup.group_id = :group_id")
    List<trip> findGroupInTrip(Integer group_id);

    @Query(nativeQuery = true, value = "select * from trip t where t.group_id = :group_id and NOW() < end_date;")
    List<trip> findGroupDate(Integer group_id);

    @Query(nativeQuery = true, value = "select u.user_id, u.user_name, u.default_id from `user` u join tripuser t on t.user_id = u.user_id where t.trip_id = :trip_id")
    List<Map<String, Object>> findByMember(Integer trip_id);

    @Query(nativeQuery = true, value = "insert into trip(group_id, trip_path) value (:group_id, :newFilename);")
    void InsertTrip(Integer group_id, String newFilename);

    @Query(nativeQuery = true, value = "insert into tripuser(trip_id, user_id) value(:trip_id, :user_id);")
    void insertMember(Integer trip_id, Integer user_id);

    @Query(nativeQuery = true, value = "delete from tripuser where trip_id = :trip_id and user_id = :user_id")
    void deleteMember(Integer trip_id, Integer user_id);
}
