package nido.backnido.repository;

import nido.backnido.entity.Image;
import nido.backnido.entity.Product;
import nido.backnido.entity.dto.ImageDTO;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	Set<Image> findByProduct(Product product);
}
