package nido.backnido.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nido.backnido.entity.Category;
import nido.backnido.service.CategoryService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test // BACK-CONTROLLER NID-005
    public void createCategoryValidationReturnsStatus201Test() throws Exception {
        Category mockCategory = Category.builder().title("title").description("Description test").urlImage("urlTest.jpg").build();

        doNothing().when(categoryService).create(getCategory());

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCategory)))
                .andExpect(status().isCreated());

        verify(categoryService).create(any());
    }


    @Test // BACK-CONTROLLER NID-006
    public void createCategoryValidationReturnsStatus400Test() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildInvalidCategory())))
                .andExpect(status().isBadRequest());
    }

    @Test // BACK-CONTROLLER NID-007
    public void listAllReturnsStatus200Test() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk());
    }

    @Test // BACK-CONTROLLER NID-008
    public void validContentUpdateReturnsStatus200Test() throws Exception {
        Category mockCategory = Category.builder().title("title").description("Description test").urlImage("urlTest.jpg").build();


        mockCategory.setCategoryId(1L);

        mockMvc.perform(put("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCategory))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test // BACK-CONTROLLER NID-009
    public void invalidContentUpdateReturnsStatus400Test() throws Exception {
        Category expectedResponse = buildInvalidCategory();
        expectedResponse.setCategoryId(1L);

        mockMvc.perform(put("/api/v1/category")
                .content(objectMapper.writeValueAsString(expectedResponse))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test // BACK-CONTROLLER NID-010
    public void deleteCategoryByIdReturnsStatus204Test() throws Exception {

        mockMvc.perform(delete("/api/v1/category/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test //
    public void getCategoryByIdReturnsStatus200Test() throws Exception {
        mockMvc.perform(get("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Category buildInvalidCategory() {
        Category newCategory = new Category();
        return newCategory;
    }

    private Category getCategory() {
        return Category.builder().title("Test title").description("Description category").urlImage("urlCategory.jpg").build();
    }


}