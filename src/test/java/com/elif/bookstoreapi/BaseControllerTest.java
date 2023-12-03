package com.elif.bookstoreapi;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest(classes = {BookStoreApiApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {

    public static String APPLICATION_JSON = "application/json";

    public String loadFile(String jsonFile) throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = FileUtils.toFile(classLoader.getResource(jsonFile));
        assert file != null;
        return FileUtils.readFileToString(file, "UTF-8");
    }


    public RequestPostProcessor postHeaders() {

        return request -> {
            request.addHeader("Accept", APPLICATION_JSON);
            request.addHeader("Content-Type", APPLICATION_JSON);
            return request;
        };
    }

    public RequestPostProcessor getHeaders() {
        return request -> {
            request.addHeader("Accept", APPLICATION_JSON);
            return request;
        };
    }

}
