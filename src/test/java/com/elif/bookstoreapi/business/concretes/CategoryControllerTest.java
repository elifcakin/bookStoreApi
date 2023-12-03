package com.elif.bookstoreapi.business.concretes;

import com.elif.bookstoreapi.BaseControllerTest;
import com.elif.bookstoreapi.BookStoreApiApplication;
import com.elif.bookstoreapi.containers.BookStoreApiMysqlContainer;
import com.elif.bookstoreapi.repository.CategoryRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CategoryControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/category";

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void deleteAll(){
        categoryRepository.deleteAll();
    }


    @ClassRule
    protected static BookStoreApiMysqlContainer container = BookStoreApiMysqlContainer
            .getInstance();

    @Test
    @DisplayName("GetAll Category Then Success")
    void getAllCategoryThenSuccess() throws Exception{
        postCategoryThenSuccess();
        mockMvc.perform(get(baseUrl))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("Get Category Then Success")
    void getByIdThenSuccess()throws Exception{
        String data = loadFile("payloads/category-valid.json");
        String responseJson
                = addCategory(data).andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();

        Integer categoryId = JsonPath.parse(responseJson).read("$.id",Integer.class);

        mockMvc.perform(get(baseUrl + "/" + categoryId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("Try Not Existing Id Then Return NotFound Response")
    void tryNotExistingIdThenReturnNotFoundResponse() throws Exception{
        mockMvc.perform(get(baseUrl + "/39485"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    @DisplayName("Post Category Then Success")
    void postCategoryThenSuccess() throws Exception{
        String data = loadFile("payloads/category-valid.json");
        addCategory(data).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @DisplayName("Put Category Then Success")
    void putCategoryThenSuccess() throws Exception{
        String data = loadFile("payloads/category-valid.json");
        String addResponse = addCategory(data).andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();

        DocumentContext documentContext = JsonPath.parse(addResponse);

        documentContext.set("$.name","Fantastik");

        String updatedJson = documentContext.jsonString();

        mockMvc.perform(put(baseUrl)
                        .with(postHeaders()).content(updatedJson))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.CREATED.value()));

    }

    @Test
    @DisplayName("Try Put Not Exist Category And Throw Error")
    void tryPutNotExistCategoryAndThrowError() throws Exception{

        String data = loadFile("payloads/category-update-notvalid.json");


        mockMvc.perform(put(baseUrl)
                        .with(postHeaders()).content(data))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Delete Category Then Success")
    void deleteCategoryThenSuccess() throws Exception{
        String data = loadFile("payloads/category-valid.json");
        String responseJson
                = addCategory(data).andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();

        Integer categoryId = JsonPath.parse(responseJson).read("$.id",Integer.class);

        mockMvc.perform(delete(baseUrl + "/" + categoryId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("Delete Not Exist Id And Throw Error")
    void deleteNotExistIdAndThrowError() throws Exception{


        mockMvc.perform(delete(baseUrl + "/73873"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    private ResultActions addCategory(String data) throws Exception{
        return mockMvc.perform(post(baseUrl)
                        .with(postHeaders()).content(data))
                .andDo(MockMvcResultHandlers.print());
    }

}
