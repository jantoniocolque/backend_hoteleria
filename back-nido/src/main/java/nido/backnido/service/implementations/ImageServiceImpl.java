package nido.backnido.service.implementations;

import nido.backnido.entity.Image;
import nido.backnido.entity.Product;
import nido.backnido.entity.dto.ImageDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.ImageRepository;
import nido.backnido.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ImageDTO> getAll() {
        List<ImageDTO> imageResponse = new ArrayList<>();

        for (Image image : imageRepository.findAll()) {
            imageResponse.add(modelMapper.map(image, ImageDTO.class));
        }

        return imageResponse;
    }

    @Override
    public ImageDTO getById(Long id) {
        ImageDTO dtoRes = modelMapper.map(imageRepository.getById(id), ImageDTO.class);

        return dtoRes;
    }

    @Override
    public void create(Image newImage) {
        try{
            if (newImage != null) {
                imageRepository.save(newImage);
            }
        }catch (Exception e){
            throw new CustomBaseException("Error al crear la imagen, verifique que la información sea correcta", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public void update(Image updatedImage) {
        if(updatedImage.getImageId() != null) {
            imageRepository.findById(updatedImage.getImageId()).orElseThrow( () ->
                    new CustomBaseException("Imagen no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value()));
        } else {
            throw new CustomBaseException("El id de la imagen no puede estar vacío, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }
        imageRepository.save(updatedImage);
    }

    @Override
    public void delete(Long id) {
        imageRepository.findById(id).orElseThrow( () ->
                new CustomBaseException("Imagen con el id: " + id + " no encontrada, por favor compruebe", HttpStatus.BAD_REQUEST.value()));
        imageRepository.deleteById(id);
    }

	@Override
	public Set<Image> findByProductId(Product product) {
		return imageRepository.findByProduct(product);
	}
}
