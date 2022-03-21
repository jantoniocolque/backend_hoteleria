package nido.backnido.service;

import nido.backnido.entity.Category;
import nido.backnido.entity.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAll();
    CategoryDTO getById(Long id);
    List<CategoryDTO> findByCategoryTitle(String title);
    void create(Category newCategory);
    void update(Category updatedCategory);
    void delete(Long id);
    void deleteByCategoryTitle(String title);

}
