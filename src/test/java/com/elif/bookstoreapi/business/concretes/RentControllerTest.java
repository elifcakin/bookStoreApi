package com.elif.bookstoreapi.business.concretes;

import com.elif.bookstoreapi.BaseControllerTest;
import com.elif.bookstoreapi.entity.*;
import com.elif.bookstoreapi.entity.abstracts.DailyRentReportModel;
import com.elif.bookstoreapi.entity.dtos.BookDTO;
import com.elif.bookstoreapi.entity.dtos.RentLogDTO;
import com.elif.bookstoreapi.entity.dtos.RentReportDTO;
import com.elif.bookstoreapi.entity.dtos.UserDTO;
import com.elif.bookstoreapi.repository.*;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RentControllerTest extends BaseControllerTest {


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookStoreRepository bookStoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentLogRepository rentLogRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String baseUrl = "/api/rent";

    private BookDTO bookDTO = new BookDTO();

    private UserDTO userDTO = new UserDTO();

    @BeforeAll
    void addDatas(){
        objectMapper.registerModule(new JavaTimeModule());
        var author = authorRepository.save(new Author(null,"author","author",null));
        var category = categoryRepository.save(new Category(null,"Test",null));
        var bookStore = bookStoreRepository.save(new BookStore(null,"Test","Test","327832",null));

        var book = bookRepository.save(new Book(null,"Test",category,author,bookStore,null));

        var user = userRepository.save(new User(null,"TEST","TEST","TEst","test@test",null));

        bookDTO.setId(book.getId());
        userDTO.setId(user.getId());

    }

    @AfterAll
    void deleteAllDatas(){
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
        bookStoreRepository.deleteAll();
        userRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @AfterEach
    void deleteRentLogs(){
        rentLogRepository.deleteAll();
    }

    @Test
    @DisplayName("Add Rent Then Success")
    void addRentThenSuccess() throws Exception {

        RentLogDTO rentLogDTO =
                new RentLogDTO(null,bookDTO,userDTO, null
                        ,null);

        String data = objectMapper.writeValueAsString(rentLogDTO);

        String sendJson
                = JsonPath.parse(data).set("$.startDate","2023-12-01").set("$.endDate","2023-12-06").jsonString();

        addRent(sendJson).andExpect(status().is(HttpStatus.CREATED.value()));
    }


    @Test
    @DisplayName("Try Rent Not Available Book And Throw Error")
    void tryRentNotAvailableBookAndThrowError() throws Exception {

        RentLogDTO rentLogDTO =
                new RentLogDTO(null,bookDTO,userDTO, null
                        ,null);

        String data = objectMapper.writeValueAsString(rentLogDTO);

        String sendJson
                = JsonPath.parse(data).set("$.startDate","2023-12-01").set("$.endDate","2023-12-06").jsonString();

        addRent(sendJson).andExpect(status().is(HttpStatus.CREATED.value()));


         sendJson = JsonPath.parse(data).set("$.startDate","2023-12-04").set("$.endDate","2023-12-07").jsonString();


        addRent(sendJson).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    @DisplayName("Get Daily Report Then Success")
    void getDailyReportThenSuccess() throws Exception {


        RentLogDTO rentLogDTO =
                new RentLogDTO(null,bookDTO,userDTO, null
                        ,null);


        String data = objectMapper.writeValueAsString(rentLogDTO);


        String sendJson
                = JsonPath.parse(data).set("$.startDate","2023-12-01").set("$.endDate","2023-12-06").jsonString();

        addRent(sendJson).andExpect(status().is(HttpStatus.CREATED.value()));

        String responseStr = mockMvc.perform(get(baseUrl + "/getRentReport")
                        .param("startDate","2023-11-28").param("endDate","2023-12-03"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();


        RentReportDTO rentReportDTO = objectMapper.readValue(responseStr,RentReportDTO.class);

        List<DailyRentReportModel> dailyReport = rentReportDTO.getDailyReport();


        Assertions.assertEquals(6, dailyReport.size());
        Assertions.assertEquals(0,dailyReport.get(0).getRentedBookCount());
        Assertions.assertEquals(0,dailyReport.get(1).getRentedBookCount());
        Assertions.assertEquals(0,dailyReport.get(2).getRentedBookCount());
        Assertions.assertEquals(1,dailyReport.get(3).getRentedBookCount());
        Assertions.assertEquals(1,dailyReport.get(4).getRentedBookCount());
        Assertions.assertEquals(1,dailyReport.get(5).getRentedBookCount());

    }

    private ResultActions addRent(String data) throws Exception{
        return mockMvc.perform(post(baseUrl)
                        .with(postHeaders()).content(data))
                .andDo(MockMvcResultHandlers.print());
    }

}
