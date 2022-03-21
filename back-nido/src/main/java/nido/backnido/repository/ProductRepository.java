package nido.backnido.repository;

import nido.backnido.entity.Product;
import nido.backnido.entity.Reserve;
import nido.backnido.entity.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query(value = "Select p.* FROM Products p INNER JOIN Categories c WHERE c.title LIKE CONCAT('%',:title,'%');", nativeQuery = true)
    //List<Product> findProductByCategory(@Param("title")String title);
	List<Product> findByCategory_TitleContaining(String title);
    @Query(value= "select * from products p\n" +
            "INNER JOIN locations l ON p.locations_location_id = l.location_id \n" +
            "where l.city like %:city%", nativeQuery = true)
    List<Product> findProductByCity(@Param("city")String city);

    @Query(value = "SELECT * FROM products p\n" +
            "INNER JOIN locations l ON p.locations_location_id = l.location_id\n" +
            "WHERE p.product_id NOT IN \n" +
            "(SELECT p.product_id FROM products p\n" +
            "INNER JOIN reserves r ON p.product_id = r.products_product_id\n" +
            "WHERE (:dateIn BETWEEN r.date_in AND r.date_out)  \n" +
            "OR  (:dateOut BETWEEN r.date_in AND r.date_out)  \n" +
            ") AND l.city = :city", nativeQuery = true)
    List<Product> filterProductsByLocationAndDate(@Param("city")String city,@Param("dateIn") LocalDate dateIn,@Param("dateOut") LocalDate dateOut);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.active = false WHERE p.productId = :productId")
    void softDelete(@Param("productId") Long productId);

    //@Query("select p from Product p where p.location.locationId = :id")
    List<Product> findProductByLocation_LocationId(Long id);

    Page<Product> findAll(Pageable page);
    Page<Product> findProductsByCategory_Title(String title, Pageable page);
}