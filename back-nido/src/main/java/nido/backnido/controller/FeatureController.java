package nido.backnido.controller;

import nido.backnido.entity.Feature;
import nido.backnido.entity.Reserve;
import nido.backnido.entity.dto.FeatureDTO;
import nido.backnido.entity.dto.ReserveDTO;
import nido.backnido.exception.CustomBindingException;
import nido.backnido.service.FeatureService;
import nido.backnido.utils.UtilsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/feature")
@CrossOrigin("*")
public class FeatureController {

    @Autowired
    FeatureService featureService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FeatureDTO> getAll(){
        return featureService.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeatureDTO getById(@PathVariable Long id){
        return featureService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid Feature feature, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new CustomBindingException("Errores encontrados, por favor compruebe e intente nuevamente", HttpStatus.BAD_REQUEST.value(), UtilsException.fieldBindingErrors(bindingResult));
        }
        featureService.create(feature);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid Feature feature, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new CustomBindingException("Errores encontrados, por favor compruebe e intente nuevamente",HttpStatus.NOT_FOUND.value(),UtilsException.fieldBindingErrors(bindingResult));
        }
        featureService.update(feature);

    }

    @PutMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        featureService.delete(id);
    }

}
