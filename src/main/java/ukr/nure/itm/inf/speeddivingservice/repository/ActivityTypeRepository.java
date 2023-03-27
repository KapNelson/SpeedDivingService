package ukr.nure.itm.inf.speeddivingservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ukr.nure.itm.inf.speeddivingservice.model.ActivityType;
import ukr.nure.itm.inf.speeddivingservice.model.Sportsman;

import java.util.Optional;

@Repository
public interface ActivityTypeRepository extends CrudRepository<ActivityType, Long> {
    Optional<ActivityType> findActivityTypeByActivityTypeNameAndSportsman(String ActivityTypeName, Sportsman sportsman);
}
