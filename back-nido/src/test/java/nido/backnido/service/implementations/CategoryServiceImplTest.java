package nido.backnido.service.implementations;

import nido.backnido.entity.Category;
import nido.backnido.entity.Product;
import nido.backnido.entity.User;
import nido.backnido.exception.CustomBaseException;
import nido.backnido.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void saveCategoryTest_Ok(){
        Set<Product> product = new HashSet<>();
        Category category = new Category(null,"Title category test", "Description category test", "Image category test", product);
        Category categoryResponse = new Category(1L,"Title category test", "Description category test", "Image category test", product);

        when(categoryRepository.save(category)).thenReturn(categoryResponse);

        categoryService.create(category);

        verify(categoryRepository).save(category);
        assertEquals(category.getTitle(),categoryResponse.getTitle());
        assertEquals(1L, categoryResponse.getCategoryId());
    }

    @Test
    public void saveCategoryTest_Null(){
        when(categoryRepository.save(any())).thenReturn(null);
        categoryService.create(null);
        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    public void getAllCategoryTest_Ok(){
        Category category = new Category("Test title","Test description", "Test urlImage");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        categoryService.getAll();

        verify(categoryRepository).findAll();
        assertEquals(1, categoryList.size());
    }

    @Test
    public void getCategoryByIdTest_Ok(){
        Category category = new Category(1L,"Test title","Test description", "Test urlImage");
        Category categoryResponse = new Category(1L,"Test title","Test description", "Test urlImage");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryResponse));
        categoryService.getById(1L);

        verify(categoryRepository).findById(category.getCategoryId());
        assertEquals(category.getTitle(), categoryResponse.getTitle());
        assertEquals(1L, categoryResponse.getCategoryId());

    }

    @Test
    public void updateCategoryTest_Ok(){
        Category categoryDB = new Category(1L, "Test title","Test description", "Test urlImage");
        Category categoryNew = new Category(1L, "Test title","Test description", "Test urlImage");
        Category categoryResponse = new Category(1L,"Test title","Test description", "Test urlImage");

        when(categoryRepository.findById(categoryDB.getCategoryId())).thenReturn(Optional.of(categoryResponse));
        categoryService.update(categoryNew);

        verify(categoryRepository).save(categoryNew);
        assertEquals(categoryNew.getTitle(), categoryResponse.getTitle());

    }

    @Test
    public void deleteCategoryTest_Ok(){
        Category categoryDB = new Category(1L, "Test title","Test description", "Test urlImage");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryDB));
        doNothing().when(categoryRepository).deleteById(categoryDB.getCategoryId());
        categoryService.delete(1L);

        verify(categoryRepository, times(1)).deleteById(categoryDB.getCategoryId());
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    public void findByCategoryTitleTest_Ok(){
        Category category = new Category(1L,"Test title","Test description", "Test urlImage");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        when(categoryRepository.findByCategoryTitle(anyString())).thenReturn(categoryList);
        categoryService.findByCategoryTitle("Test title");

        verify(categoryRepository).findByCategoryTitle(category.getTitle());
        assertEquals(1, categoryList.size());
    }

    @Test
    public void getCategoryByIdException(){
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            categoryService.getById(1L);
        });

    }

    @Test
    public void updateCategoryByIdException(){
        Category category = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            categoryService.update(category);
        });

    }

    @Test
    public void deleteCategoryByIdException(){
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomBaseException.class,()->{
            categoryService.delete(1L);
        });

    }
}