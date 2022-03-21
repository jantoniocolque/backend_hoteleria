package nido.backnido.service;

import nido.backnido.entity.Location;
import nido.backnido.entity.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    List<LocationDTO> getAll();
    LocationDTO getById(Long id);
    void create(Location newLocation);
    void update(Location updatedLocation);
    void delete(Long id);
    List<LocationDTO> getAllCities();

}
