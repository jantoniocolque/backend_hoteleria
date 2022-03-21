package nido.backnido.service;

import nido.backnido.entity.Product;
import nido.backnido.entity.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getAll();
    ProductDTO getById(Long id);
    void create(ProductDTO newProduct, List<MultipartFile> file);
    void update(Product updatedProduct);
    void delete(Long id);
    List<ProductDTO> findProductByCategory(@Param("title")String title);
    List<ProductDTO> findProductByCity(String city);
    List<ProductDTO> filterProductsByLocationAndDate(String city, LocalDate dateIn, LocalDate dateOut);
    List<ProductDTO> findProductByLocation_LocationId(Long id);
    Page<ProductDTO> findAll(Pageable page);
    Page<ProductDTO> findProductsByCategory_Title(String title, Pageable page);
}
