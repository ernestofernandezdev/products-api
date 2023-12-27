package com.ferdev.restful.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest(name = "sort={0}, order={1}")
    @CsvFileSource(resources = "/testFiles/sortAndOrder.csv")
    void getTEST(String sort, String order) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products?sort=" + sort + "&order=" + order))
                .andExpect(status().isOk())
                .andReturn();
    }
}
