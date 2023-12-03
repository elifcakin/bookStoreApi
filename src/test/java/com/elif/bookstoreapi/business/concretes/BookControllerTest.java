package com.elif.bookstoreapi.business.concretes;

import com.elif.bookstoreapi.BaseControllerTest;
import com.elif.bookstoreapi.entity.Author;
import com.elif.bookstoreapi.entity.BookStore;
import com.elif.bookstoreapi.entity.Category;
import com.elif.bookstoreapi.entity.dtos.AuthorDTO;
import com.elif.bookstoreapi.entity.dtos.BookDTO;
import com.elif.bookstoreapi.entity.dtos.BookStoreDTO;
import com.elif.bookstoreapi.entity.dtos.CategoryDTO;
import com.elif.bookstoreapi.repository.AuthorRepository;
import com.elif.bookstoreapi.repository.BookRepository;
import com.elif.bookstoreapi.repository.BookStoreRepository;
import com.elif.bookstoreapi.repository.CategoryRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/api/book";

    private AuthorDTO authorDTO = new AuthorDTO();
    private BookStoreDTO bookStoreDTO = new BookStoreDTO();
    private CategoryDTO categoryDTO = new CategoryDTO();

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    void addDatas(){
       var author = authorRepository.save(new Author(null,"author","author",null));
       var bookStore =  bookStoreRepository.save(new BookStore(null,"Test","Test","Test",null));
       var category = categoryRepository.save(new Category(null,"Test",null));
       authorDTO.setId(author.getId());
       bookStoreDTO.setId(bookStore.getId());
       categoryDTO.setId(category.getId());
    }

    @AfterAll
    void deleteAllData(){
        authorRepository.deleteAll();
        bookStoreRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @AfterEach
    void deleteBook(){
        bookRepository.deleteAll();
    }

    @DisplayName("Get All Books Then Success")
    @Test
    void getAllBooksThenSuccess() throws Exception{
        postBookThenSuccess();
        mockMvc.perform(get(baseUrl))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @DisplayName("Get By Id Then Success")
    @Test
    void getByIdThenSuccess() throws Exception {
        String data = getValidBookDTO();
        String response = addBook(data).andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();

        Integer bookId = JsonPath.parse(response).read("$.id");

        mockMvc.perform(get(baseUrl + "/" + bookId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value()));

    }

    @DisplayName("Try Get With Not Exist Id And Throw Exception")
    @Test
    void tryGetWithNotExistIdAndThrowException() throws Exception{
        mockMvc.perform(get(baseUrl + "/58456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @DisplayName("Post Book Then Success")
    @Test
    void postBookThenSuccess() throws Exception{
        String data = getValidBookDTO();
        addBook(data).andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("Update Book Then Success")
    @Test
    void updateBookThenSuccess() throws Exception {
        String data = getValidBookDTO();
        String response = addBook(data).andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();

        String updatedJson = JsonPath.parse(response).set("$.name","The Lord Of The Ring").jsonString();

        mockMvc.perform(put(baseUrl)
                        .with(postHeaders()).content(updatedJson))
                .andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();

    }

    @DisplayName("Delete Book Then Success")
    @Test
    void deleteBooksThenSuccess() throws  Exception {

        String data = getValidBookDTO();

        String response = addBook(data).andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn().getResponse().getContentAsString();

        Integer bookId = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete(baseUrl + "/" + bookId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    private String getValidBookDTO(){
        BookDTO bookDTO = new BookDTO(null,"Test",categoryDTO,authorDTO,bookStoreDTO);
        try {
            return objectMapper.writeValueAsString(bookDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultActions addBook(String data) throws Exception{
        return mockMvc.perform(post(baseUrl)
                        .with(postHeaders()).content(data))
                .andDo(MockMvcResultHandlers.print());
    }

}
