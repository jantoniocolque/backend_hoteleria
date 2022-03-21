package nido.backnido.repository;

import nido.backnido.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT DISTINCT l.city FROM location", nativeQuery = true)
    List<Location> getAllCities();

}
