package nido.backnido.service.implementations;

import nido.backnido.entity.Feature;
import nido.backnido.entity.dto.FeatureDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.FeatureRepository;
import nido.backnido.service.FeatureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    FeatureRepository featureRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<FeatureDTO> getAll() {
        List<FeatureDTO> featureResponse = new ArrayList<>();

        for (Feature feature : featureRepository.findAll()) {
            featureResponse.add(modelMapper.map(feature, FeatureDTO.class));
        }

        return featureResponse;
    }

    @Override
    public FeatureDTO getById(Long id) {

        Feature response = featureRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Comodidad no encontrada, por favor compruebe", HttpStatus.BAD_REQUEST.value())
        );

        return modelMapper.map(response, FeatureDTO.class);
    }

    @Override
    public void create(Feature newFeature) {
        if (newFeature != null) {
            featureRepository.save(newFeature);
        }
    }

    @Override
    public void update(Feature updatedFeature) {
        if(updatedFeature.getFeatureId() != null) {
            featureRepository.findById(updatedFeature.getFeatureId()).orElseThrow( () ->
                    new CustomBaseException("Comodidad no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value()));
        } else {
            throw new CustomBaseException("El id de la Comodidad no puede estar vacío, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }
        featureRepository.save(updatedFeature);
    }

    @Override
    public void delete(Long id) {
        featureRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Comodidad con el id: " + id + " no encontrada por favor compruebe el id e intente nuevamente ", HttpStatus.NOT_FOUND.value())
        );
        featureRepository.softDelete(id);
    }
}
