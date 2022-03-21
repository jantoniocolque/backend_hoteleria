package nido.backnido.service;

import nido.backnido.entity.Feature;
import nido.backnido.entity.Image;
import nido.backnido.entity.dto.FeatureDTO;
import nido.backnido.entity.dto.ImageDTO;

import java.util.List;

public interface FeatureService {

    List<FeatureDTO> getAll();
    FeatureDTO getById(Long id);
    void create(Feature newFeature);
    void update(Feature updatedFeature);
    void delete(Long id);

}
