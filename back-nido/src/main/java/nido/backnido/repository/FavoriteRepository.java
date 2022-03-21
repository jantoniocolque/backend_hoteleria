package nido.backnido.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nido.backnido.entity.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
	List<Favorite> findByUser_UserId(Long userId);
	@Query(value="SELECT * FROM favorites WHERE products_product_id = :productId AND users_user_id= :userId",nativeQuery = true)
	Optional<Favorite> findByUser_IdAndProduct_IdGreaterThanEqual(@Param("productId") Long productId,@Param("userId") Long userId);
}
