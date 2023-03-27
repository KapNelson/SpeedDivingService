package ukr.nure.itm.inf.speeddivingservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ukr.nure.itm.inf.speeddivingservice.model.Activity;
import ukr.nure.itm.inf.speeddivingservice.model.Sportsman;

import java.util.Optional;

@Repository
public interface SportsmanRepository extends CrudRepository<Sportsman, Long> {
    Optional<Sportsman> findSportsmanBySportsmanName(String name);
}
