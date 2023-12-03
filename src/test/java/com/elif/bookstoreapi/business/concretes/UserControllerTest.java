package com.elif.bookstoreapi.business.concretes;

import com.elif.bookstoreapi.BaseControllerTest;
import com.elif.bookstoreapi.containers.BookStoreApiMysqlContainer;
import com.elif.bookstoreapi.repository.AuthorRepository;
import com.elif.bookstoreapi.repository.UserRepository;
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

public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/user";

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void deleteAll(){
        userRepository.deleteAll();
    }


    @ClassRule
    protected static BookStoreApiMysqlContainer container = BookStoreApiMysqlContainer
                    .getInstance();

    @Test
    @DisplayName("GetAll User Then Success")
    void getAllUserThenSuccess() throws Exception{
        postUserThenSuccess();
        mockMvc.perform(get(baseUrl))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    @DisplayName("Get User Then Success")
    void getByIdThenSuccess()throws Exception{
        String data = loadFile("payloads/user-valid.json");
        String responseJson
                        = addUser(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        Integer userId = JsonPath.parse(responseJson).read("$.id", Integer.class);

        mockMvc.perform(get(baseUrl + "/" + userId))
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
    @DisplayName("Post User Then Success")
    void postUserThenSuccess() throws Exception{
        String data = loadFile("payloads/user-valid.json");
        addUser(data).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @Test
    @DisplayName("Put User Then Success")
    void putUserThenSuccess() throws Exception{
        String data = loadFile("payloads/user-valid.json");
        String addResponse = addUser(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        DocumentContext documentContext = JsonPath.parse(addResponse);

        documentContext.set("$.firstname","Author2");

        String updatedJson = documentContext.jsonString();

        mockMvc.perform(put(baseUrl)
                                        .with(postHeaders()).content(updatedJson))
                        .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.CREATED.value()));

    }

    @Test
    @DisplayName("Try Put Not Exist User And Throw Error")
    void tryPutNotExistUserAndThrowError() throws Exception{

        postUserThenSuccess();
        String data = loadFile("payloads/user-update-notvalid.json");

        mockMvc.perform(put(baseUrl).with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print()).andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    @DisplayName("Delete User Then Success")
    void deleteUserThenSuccess() throws Exception{
        String data = loadFile("payloads/user-valid.json");
        String responseJson
                        = addUser(data).andExpect(status().is(HttpStatus.CREATED.value()))
                        .andReturn().getResponse().getContentAsString();

        Integer userId = JsonPath.parse(responseJson).read("$.id",Integer.class);

        mockMvc.perform(delete(baseUrl + "/" + userId))
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

    private ResultActions addUser(String data) throws Exception{
        return mockMvc.perform(post(baseUrl)
                                               .with(postHeaders()).content(data))
                        .andDo(MockMvcResultHandlers.print());
    }
}
