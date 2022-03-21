package nido.backnido.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nido.backnido.entity.Product;
import nido.backnido.entity.dto.ProductDTO;
import nido.backnido.exception.CustomBindingException;
import nido.backnido.service.ProductService;
import nido.backnido.service.ReserveService;
import nido.backnido.utils.UtilsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;
    private final ReserveService reserveService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public ProductController(ProductService productService, ReserveService reserveService) {
        this.productService = productService;
        this.reserveService = reserveService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAll(){

        return productService.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getById(@PathVariable Long id){
        return productService.getById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    public void create(@RequestParam("body") String product, @RequestPart(value= "file") final List<MultipartFile> files) throws JsonProcessingException {
        ProductDTO productDTO = objectMapper.readValue(product, ProductDTO.class);
        productService.create(productDTO, files);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public void update(@RequestBody @Valid Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new CustomBindingException ("Errores encontrados, por favor compruebe e intente nuevamente",HttpStatus.NOT_FOUND.value(),UtilsException.fieldBindingErrors(bindingResult));
        }
        productService.update(product);
    }

    @PutMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        reserveService.deleteAllByProductId(id);
        productService.delete(id);
    }

    @GetMapping("/search/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductByCity(@PathVariable String city){
        return productService.findProductByCity(city);
    }
    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductByCategory(@RequestParam("name") String category){
    	return productService.findProductByCategory(category);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> filterProductsByLocationAndDate(@RequestParam("city") String city, @RequestParam("dateIn") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateIn, @RequestParam("dateOut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOut){
     return productService.filterProductsByLocationAndDate(city, dateIn, dateOut);
    }

    @GetMapping("/page/{page}")
    public Page<ProductDTO> productAllPage(@PathVariable("page") Integer page) {
        return productService.findAll(PageRequest.of(page, 10));
    }

    @GetMapping("/category/page")
    public Page<ProductDTO> productByCategoryPage(@RequestParam("name") String category, @RequestParam("page") Integer page) {
        return productService.findProductsByCategory_Title(category, PageRequest.of(page, 10));
    }
    @GetMapping("/search/location/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> findProductByLocation_LocationId(@PathVariable("id") Long id){
        return productService.findProductByLocation_LocationId(id);
    }
}
