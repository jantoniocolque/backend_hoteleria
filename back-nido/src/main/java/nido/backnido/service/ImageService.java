package nido.backnido.service;

import nido.backnido.entity.Image;
import nido.backnido.entity.Product;
import nido.backnido.entity.dto.ImageDTO;

import java.util.List;
import java.util.Set;

public interface ImageService {

    List<ImageDTO> getAll();
    ImageDTO getById(Long id);
    void create(Image newImage);
    void update(Image updatedImage);
    void delete(Long id);
    Set<Image> findByProductId(Product product);
}
