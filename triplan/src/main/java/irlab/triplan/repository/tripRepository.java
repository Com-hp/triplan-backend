package irlab.triplan.repository;

import irlab.triplan.DTO.tripDTO;
import irlab.triplan.DTO.userDTO;
import irlab.triplan.entity.trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface tripRepository extends JpaRepository<trip, Integer> {
    @Query(nativeQuery = true, value = "select t.trip_id, t.trip_name, t.trip_path, DATE_FORMAT(t.start_date,'%Y.%m.%d.') as start_date, DATE_FORMAT(t.end_date,'%Y.%m.%d.') as end_date from trip t where t.group_id = :group_id ORDER BY trip_id DESC")
    List<Map<String, Object>> findGroupInTrip(Integer group_id);

    @Query(nativeQuery = true, value = "select * from trip t where t.group_id = :group_id and NOW() < end_date;")
    List<trip> findGroupDate(Integer group_id);

    @Query(nativeQuery = true, value = "select u.user_id, u.user_name, u.default_id from `user` u join tripuser t on t.user_id = u.user_id where t.trip_id = :trip_id")
    List<Map<String, Object>> findByMember(Integer trip_id);

    @Query(nativeQuery = true, value = "insert into trip(group_id, trip_path, trip_name, start_date, end_date) value (:group_id, :newFilename, :trip_name, :start_date, :end_date);")
    void InsertTrip(Integer group_id, String newFilename, String trip_name, LocalDate start_date, LocalDate end_date);

    @Query(nativeQuery = true, value = "insert into tripuser(trip_id, user_id) value(:trip_id, :user_id);")
    void insertMember(Integer trip_id, Integer user_id);

    @Query(nativeQuery = true, value = "delete from tripuser where trip_id = :trip_id and user_id = :user_id")
    void deleteMember(Integer trip_id, Integer user_id);

    @Query(nativeQuery = true, value = "update trip set trip_name = :trip_name, start_date = :start_date, end_date =:end_date, trip_path = :newFilename where trip_id = :trip_id")
    void editTrip(Integer trip_id, String trip_name, LocalDate start_date, LocalDate end_date, String newFilename);
}
