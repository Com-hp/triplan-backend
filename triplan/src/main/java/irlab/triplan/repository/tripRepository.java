package irlab.triplan.repository;

import irlab.triplan.entity.trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface tripRepository extends JpaRepository<trip, Integer> {
    @Query(value = "select t from trip t where t.tGroup.id = :id")
    List<trip> findGroupInTrip(Integer id);
}
