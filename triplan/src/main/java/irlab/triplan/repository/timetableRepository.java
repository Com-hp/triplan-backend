package irlab.triplan.repository;

import irlab.triplan.entity.timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface timetableRepository extends JpaRepository<timetable, Integer> {
}
