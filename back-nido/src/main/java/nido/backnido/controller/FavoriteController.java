package nido.backnido.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nido.backnido.entity.Favorite;
import nido.backnido.entity.Reserve;
import nido.backnido.entity.dto.FavoriteDTO;
import nido.backnido.exception.CustomBindingException;
import nido.backnido.service.FavoriteService;
import nido.backnido.utils.UtilsException;

@RestController
@RequestMapping("api/v1/favorite")
@CrossOrigin("*")
public class FavoriteController {
	private final FavoriteService favoriteService;
	
	@Autowired
	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}
	
	 @GetMapping
	 @ResponseStatus(HttpStatus.OK)
	 public List<FavoriteDTO> getAll(){
	        return favoriteService.getAll();
	 }
	 
	@GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDTO getById(@PathVariable Long id){
        return favoriteService.getById(id);
    }
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Favorite favorite, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new CustomBindingException("Errores encontrados, por favor compruebe e intente nuevamente", HttpStatus.BAD_REQUEST.value(), UtilsException.fieldBindingErrors(bindingResult));
        }
        favoriteService.create(favorite);
    }
	
	@DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
		favoriteService.delete(id);
    }
	@GetMapping("/user/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<FavoriteDTO> getByUser(@PathVariable Long id) {
		return favoriteService.findAllFavoritesByUser(id);
	}
	
	@DeleteMapping("/{userId}/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteByUserAndProduct(@PathVariable Long userId,@PathVariable Long productId) {
		favoriteService.deleteByUserAndProduct(productId, userId);
	}
}
