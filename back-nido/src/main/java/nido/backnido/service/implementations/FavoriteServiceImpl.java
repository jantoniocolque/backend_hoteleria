package nido.backnido.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import nido.backnido.entity.Favorite;
import nido.backnido.entity.Reserve;
import nido.backnido.entity.dto.FavoriteDTO;
import nido.backnido.entity.dto.ReserveDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.FavoriteRepository;
import nido.backnido.service.FavoriteService;
import nido.backnido.service.ImageService;

@Service
public class FavoriteServiceImpl implements FavoriteService{
	@Autowired
	FavoriteRepository favoriteRepository;
	@Autowired
    ImageService imageService;
	ModelMapper modelMapper = new ModelMapper();
	@Override
	public List<FavoriteDTO> getAll() {
		List<FavoriteDTO> response = new ArrayList<>();
		for(Favorite favorite: favoriteRepository.findAll()) {
			FavoriteDTO favoritedto = modelMapper.map(favorite,FavoriteDTO.class);
			favoritedto.getProduct().setImages(imageService.findByProductId(favorite.getProduct()));
			response.add(favoritedto);
		}
		return response;
	}

	@Override
	public FavoriteDTO getById(Long id) {
		Favorite favorite = favoriteRepository.findById(id).orElseThrow(() ->
         new CustomBaseException("Favorito no encontrada, por favor compruebe su peticion", HttpStatus.NOT_FOUND.value()));
		FavoriteDTO response = modelMapper.map(favorite, FavoriteDTO.class);
		response.getProduct().setImages(imageService.findByProductId(favorite.getProduct()));
		return response;
	}

	@Override
	public void create(Favorite favorite) {
		favoriteRepository.save(favorite);
	}

	@Override
	public void delete(Long id) {
		favoriteRepository.findById(id).orElseThrow(() ->
        new CustomBaseException("Favorito con el id: " + id + " no encontrada por favor compruebe el id e intente nuevamente ", HttpStatus.NOT_FOUND.value()));
		favoriteRepository.deleteById(id);
	}

	@Override
	public List<FavoriteDTO> findAllFavoritesByUser(Long userId) {
		List<Favorite> favorites = favoriteRepository.findByUser_UserId(userId);
		List<FavoriteDTO> response = new ArrayList<>();
		for(Favorite favorite: favorites) {
			FavoriteDTO favoritedto = modelMapper.map(favorite, FavoriteDTO.class);
			favoritedto.getProduct().setImages(imageService.findByProductId(favorite.getProduct()));
			response.add(favoritedto);
		}
		
		return response;
	}

	@Override
	public void deleteByUserAndProduct(Long productId, Long userId) {
		Favorite favorite = favoriteRepository.findByUser_IdAndProduct_IdGreaterThanEqual(productId, userId).orElseThrow(() ->
        new CustomBaseException("Favorito con el producto con id: " + productId + " y el usuario con id: "+userId+" no encontrada por favor compruebe los id e intente nuevamente ", HttpStatus.NOT_FOUND.value()));
		favoriteRepository.deleteById(favorite.getFavoriteId());
	}

}
