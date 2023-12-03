package com.elif.bookstoreapi.business.concretes;

import com.elif.bookstoreapi.BaseControllerTest;
import com.elif.bookstoreapi.containers.BookStoreApiMysqlContainer;
import com.elif.bookstoreapi.repository.BookStoreRepository;
import com.elif.bookstoreapi.repository.CategoryRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookStoreControllertest extends BaseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/bookStore";

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @AfterEach
    void deleteAll(){
        bookStoreRepository.deleteAll();
    }


    @ClassRule
    protected static BookStoreApiMysqlContainer container = BookStoreApiMysqlContainer
                    .getInstance();

    @Test
    @DisplayName("GetAll BookStore Then Success")
    void getAllBookStoreThenSuccess() throws Exception{
        postBookStoreThenSuccess();
        mockMvc.perform(get(baseUrl))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("Get BookStore Then Success")
    void getByIdThenSuccess()throws Exception{
        String data = loadFile("payloads/book-store-valid.json");
        String responseJson
                        = addBookStore(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        Integer bookStoreId = JsonPath.parse(responseJson).read("$.id", Integer.class);

        mockMvc.perform(get(baseUrl + "/" + bookStoreId))
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
    @DisplayName("Post BookStore Then Success")
    void postBookStoreThenSuccess() throws Exception{
        String data = loadFile("payloads/book-store-valid.json");
        addBookStore(data).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @DisplayName("Put BookStore Then Success")
    void putBookStoreThenSuccess() throws Exception{
        String data = loadFile("payloads/book-store-valid.json");
        String addResponse = addBookStore(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        DocumentContext documentContext = JsonPath.parse(addResponse);

        documentContext.set("$.name","Istanbul Kitapcisi");

        String updatedJson = documentContext.jsonString();

        mockMvc.perform(put(baseUrl)
                                        .with(postHeaders()).content(updatedJson))
                        .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.CREATED.value()));

    }

    @Test
    @DisplayName("Try Put Not Exist BookStore And Throw Error")
    void tryPutNotExistBookStoreAndThrowError() throws Exception{

        postBookStoreThenSuccess();
        String data = loadFile("payloads/bookStore-update-notvalid.json");

        mockMvc.perform(put(baseUrl).with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Delete BookStore Then Success")
    void deleteBookStoreThenSuccess() throws Exception{
        String data = loadFile("payloads/book-store-valid.json");
        String responseJson
                        = addBookStore(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        Integer bookStoreId = JsonPath.parse(responseJson).read("$.id",Integer.class);

        mockMvc.perform(delete(baseUrl + "/" + bookStoreId))
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

    private ResultActions addBookStore(String data) throws Exception{
        return mockMvc.perform(post(baseUrl).with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print());
    }

}
