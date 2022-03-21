package nido.backnido.service.implementations;

import nido.backnido.entity.Category;
import nido.backnido.entity.dto.CategoryDTO;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.CategoryRepository;
import nido.backnido.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CategoryDTO> getAll() {

        List<CategoryDTO> categoryResponse = new ArrayList<>();

        for (Category category : categoryRepository.findAll()) {
            categoryResponse.add(modelMapper.map(category, CategoryDTO.class));
        }

        return categoryResponse;

    }

    @Override
    public CategoryDTO getById(Long id) {
        Category response = categoryRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Categoria no encontrada, por favor compruebe", HttpStatus.BAD_REQUEST.value())
        );
        return modelMapper.map(response, CategoryDTO.class);
    }

    @Override
    public void create(Category newCategory) {
        if (newCategory != null) {
            categoryRepository.save(newCategory);
        }
    }

    @Override
    public void update(Category updatedCategory) throws CustomBaseException {
        if (updatedCategory.getCategoryId() != null) {
            categoryRepository.findById(updatedCategory.getCategoryId()).orElseThrow(() ->
                    new CustomBaseException("Categoria no encontrada, por favor compruebe", HttpStatus.NOT_FOUND.value())
            );
        } else {
            throw new CustomBaseException("El id de la categoria no puede estar vacio, por favor compruebe", HttpStatus.BAD_REQUEST.value());
        }
        categoryRepository.save(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.findById(id).orElseThrow(() ->
                new CustomBaseException("Categoria con el id: " + id + " no encontrada por favor compruebe el id e intente nuevamente ", HttpStatus.BAD_REQUEST.value())
        );
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteByCategoryTitle(String title) {
        if (title != null) {
            categoryRepository.deleteByCategoryTitle(title);
        }

    }

    @Override
    public List<CategoryDTO> findByCategoryTitle(String title) {

        List<CategoryDTO> dtoResponse = new ArrayList<>();

        for (Category category : categoryRepository.findByCategoryTitle(title)) {
            dtoResponse.add(modelMapper.map(category, CategoryDTO.class));
        }

        return dtoResponse;
    }
}
