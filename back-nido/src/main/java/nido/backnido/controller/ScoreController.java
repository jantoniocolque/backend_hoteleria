package nido.backnido.controller;

import nido.backnido.entity.Product;
import nido.backnido.entity.Score;
import nido.backnido.entity.dto.ProductDTO;
import nido.backnido.entity.dto.ScoreDTO;
import nido.backnido.exception.CustomBindingException;
import nido.backnido.service.ProductService;
import nido.backnido.service.ScoreService;
import nido.backnido.utils.UtilsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/score")
@CrossOrigin("*")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid ScoreDTO score, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new CustomBindingException("Errores encontrados, por favor compruebe e intente nuevamente", HttpStatus.BAD_REQUEST.value(), UtilsException.fieldBindingErrors(bindingResult));
        }
        scoreService.create(score);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid ScoreDTO score, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new CustomBindingException ("Errores encontrados, por favor compruebe e intente nuevamente",HttpStatus.NOT_FOUND.value(),UtilsException.fieldBindingErrors(bindingResult));
        }
        scoreService.update(score);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        scoreService.delete(id);
    }

    @GetMapping("product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public double getAverageProductScore(@PathVariable Long productId){
        return scoreService.getAverageProductScore(productId);
    }

}
