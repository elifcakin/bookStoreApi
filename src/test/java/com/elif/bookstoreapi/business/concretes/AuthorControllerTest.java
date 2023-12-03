package com.elif.bookstoreapi.business.concretes;

import com.elif.bookstoreapi.BaseControllerTest;
import com.elif.bookstoreapi.containers.BookStoreApiMysqlContainer;
import com.elif.bookstoreapi.repository.AuthorRepository;
import com.elif.bookstoreapi.repository.CategoryRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/author";

    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    void deleteAll(){
        authorRepository.deleteAll();
    }


    @ClassRule
    protected static BookStoreApiMysqlContainer container = BookStoreApiMysqlContainer
                    .getInstance();

    @Test
    @DisplayName("GetAll Author Then Success")
    void getAllAuthorThenSuccess() throws Exception{
        postAuthorThenSuccess();
        mockMvc.perform(get(baseUrl))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("Get Author Then Success")
    void getByIdThenSuccess()throws Exception{
        String data = loadFile("payloads/author-valid.json");
        String responseJson
                        = addAuthor(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        Integer authorId = JsonPath.parse(responseJson).read("$.id", Integer.class);

        mockMvc.perform(get(baseUrl + "/" + authorId))
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
    @DisplayName("Post Author Then Success")
    void postAuthorThenSuccess() throws Exception{
        String data = loadFile("payloads/author-valid.json");
        addAuthor(data).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @DisplayName("Put Author Then Success")
    void putAuthorThenSuccess() throws Exception{
        String data = loadFile("payloads/author-valid.json");
        String addResponse = addAuthor(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        DocumentContext documentContext = JsonPath.parse(addResponse);

        documentContext.set("$.firstname","Author2");

        String updatedJson = documentContext.jsonString();

        mockMvc.perform(put(baseUrl)
                                        .with(postHeaders()).content(updatedJson))
                        .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.CREATED.value()));

    }

    @Test
    @DisplayName("Try Put Not Exist Author And Throw Error")
    void tryPutNotExistAuthorAndThrowError() throws Exception{

        postAuthorThenSuccess();
        String data = loadFile("payloads/author-update-notvalid.json");

        mockMvc.perform(put(baseUrl).with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Delete Author Then Success")
    void deleteAuthorThenSuccess() throws Exception{
        String data = loadFile("payloads/author-valid.json");
        String responseJson
                        = addAuthor(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        Integer authorID = JsonPath.parse(responseJson).read("$.id",Integer.class);

        mockMvc.perform(delete(baseUrl + "/" + authorID))
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

    private ResultActions addAuthor(String data) throws Exception{
        return mockMvc.perform(post(baseUrl)
                                               .with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print());
    }

}
