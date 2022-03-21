package nido.backnido.service.implementations;

import nido.backnido.entity.Location;
import nido.backnido.entity.Product;
import nido.backnido.entity.dto.LocationDTO;
import nido.backnido.entity.dto.ProductDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.LocationRepository;
import nido.backnido.service.ImageService;
import nido.backnido.service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LocationServiceImpl implements LocationService {


   private final LocationRepository locationRepository;


   private final ImageService imageService;

    ModelMapper modelMapper = new ModelMapper();

    public LocationServiceImpl(LocationRepository locationRepository, ImageService imageService) {
        this.locationRepository = locationRepository;
        this.imageService = imageService;
    }

    @Override
    public List<LocationDTO> getAll() {
        List<LocationDTO> locationResponse = new ArrayList<>();
        for (Location location : locationRepository.findAll()) {
        	Set<ProductDTO> productdto = new HashSet<>();

            LocationDTO locationDto = modelMapper.map(location, LocationDTO.class);
            for(Product product : location.getProducts()) {
                ProductDTO noImgProduct = modelMapper.map(product, ProductDTO.class);
                noImgProduct.setImages(imageService.findByProductId(product));
                productdto.add(noImgProduct);
            }
            locationDto.setProducts(productdto);
            locationResponse.add(locationDto);
        }

        return locationResponse;
    }

    @Override
    public LocationDTO getById(Long id) {
        Location response = locationRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Locación no encontrada, por favor compruebe", HttpStatus.BAD_REQUEST.value())
        );
        LocationDTO locationdto = modelMapper.map(response, LocationDTO.class);

        Set<ProductDTO> listProductdto = new HashSet<>();

        for(Product product : response.getProducts()) {
            ProductDTO noImgProduct = modelMapper.map(product, ProductDTO.class);

            noImgProduct.setImages(imageService.findByProductId(product));

            listProductdto.add(noImgProduct);

        }

        locationdto.setProducts(listProductdto);
        return locationdto;
    }

    @Override
    public void create(Location newLocation) {
        if (newLocation != null) {
            locationRepository.save(newLocation);
        }
    }

    @Override
    public void update(Location updatedLocation) {
        if(updatedLocation.getLocationId() != null) {
            locationRepository.findById(updatedLocation.getLocationId()).orElseThrow( () ->
                    new CustomBaseException("Locación no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value()));
        } else {
            throw new CustomBaseException("El id de la locación no puede estar vacío, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }
        locationRepository.save(updatedLocation);
    }

    @Override
    public void delete(Long id) {
        locationRepository.findById(id).orElseThrow( () ->
                new CustomBaseException("Locación con el id: " + id + " no encontrada, por favor compruebe", HttpStatus.BAD_REQUEST.value()));
        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationDTO> getAllCities() {
        List<LocationDTO> locationResponse = new ArrayList<>();

        for (Location location : locationRepository.findAll()) {
            locationResponse.add(modelMapper.map(location, LocationDTO.class));
        }

        return locationResponse;
    }
}
