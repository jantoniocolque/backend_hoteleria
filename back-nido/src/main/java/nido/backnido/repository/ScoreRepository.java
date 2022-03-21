package nido.backnido.repository;

import nido.backnido.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query(value = "select avg(s.score) as promedio from scores s where s.products_product_id = :productId", nativeQuery = true)
    Double getAverageProductScore(@Param("productId")Long productId );
    
    List<Score> findByProduct_ProductId(Long productId);
}
