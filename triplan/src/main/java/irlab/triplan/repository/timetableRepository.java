package irlab.triplan.repository;

import irlab.triplan.entity.timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface timetableRepository extends JpaRepository<timetable, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM timetable WHERE trip_id =:trip_id ORDER BY start_date ASC")
    List<timetable> getAllTimetable(Integer trip_id);

    @Query(nativeQuery = true, value = "SELECT * FROM timetable WHERE trip_id = :trip_id and start_date >= :start_date and end_date <= :end_date ORDER BY start_date ASC")
    List<timetable> getTimetable(Integer trip_id, Timestamp start_date, Timestamp end_date);

    @Query(nativeQuery = true, value = "INSERT INTO timetable (trip_id, title, memo, start_date, end_date) VALUES (:trip_id, :title, :memo, :start_date, :end_date)")
    void createTimetable(Integer trip_id, String title, String memo, Timestamp start_date, Timestamp end_date);

    @Query(nativeQuery = true, value = "DELETE FROM timetable WHERE timetable_id = :timetable_id")
    void deleteTimetable(Integer timetable_id);

    @Query(nativeQuery = true, value = "UPDATE timetable SET title = :title, memo = :memo, start_date = :start_date, end_date = :end_date WHERE timetable_id = :timetable_id")
    void updateTimetable(Integer timetable_id, String title, String memo, Timestamp start_date, Timestamp end_date);

    @Query(nativeQuery = true, value = "SELECT start_date, end_date FROM trip WHERE trip_id = :trip_id")
    Map<String, Object> findTrip(Integer trip_id);
}
