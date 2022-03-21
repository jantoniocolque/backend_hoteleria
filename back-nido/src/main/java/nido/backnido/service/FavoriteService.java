package nido.backnido.service;

import java.util.List;

import nido.backnido.entity.Favorite;
import nido.backnido.entity.dto.FavoriteDTO;

public interface FavoriteService {
	List<FavoriteDTO> getAll();
	FavoriteDTO getById(Long id);
    void create(Favorite favorite);
    void delete(Long id);
    List<FavoriteDTO> findAllFavoritesByUser(Long userId);
    void deleteByUserAndProduct(Long productId,Long userId);
}
