package ukr.nure.itm.inf.speeddivingservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ukr.nure.itm.inf.speeddivingservice.model.Activity;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
    // Find activity with max date
    Activity findFirstByOrderByDateDesc();

    // Find activity with min date
    Activity findFirstByOrderByDateAsc();
}
