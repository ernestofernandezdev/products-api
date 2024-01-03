package com.ferdev.restful.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferdev.restful.api.dto.Mapper;
import com.ferdev.restful.api.dto.ProductDto;
import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;
import com.ferdev.restful.api.repositories.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest(name = "sort={0}, order={1}")
    @CsvFileSource(resources = "/testFiles/sortAndOrder.csv")
    @Order(1)
    void getProductsTEST(String sort, String order) throws Exception {
        List<ProductDto> products = this.productRepository.findAll(sort, order, MockData.mockFilters()).stream()
                .map(Mapper::toDto)
                .toList();

        String productJson = mockMvc.perform(get("/api/products")
                        .param("sort", sort)
                        .param("order", order)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        List<ProductDto> productsResponse = Arrays.asList(objectMapper.readValue(productJson, ProductDto[].class));

        assertIterableEquals(products, productsResponse);
    }

    @ParameterizedTest(name = "sort={0}, order={1}")
    @CsvFileSource(resources = "/testFiles/badSortAndOrder.csv")
    @Order(1)
    void getProductsWrongQueryParamsTEST(String sort, String order) throws Exception {

        mockMvc.perform(get("/api/products")
                        .param("sort", sort)
                        .param("order", order)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("InvalidQueryParamException")));
    }

    @Test
    @Order(1)
    void getSingleProductTEST() throws Exception {
        int id = 1;

        Optional<Product> productOpt = this.productRepository.findById(id);
        assertTrue(productOpt.isPresent());
        Product product = productOpt.get();

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.amount", is(product.getAmount())));
    }

    @Test
    @Order(1)
    void getSingleProductWithWrongIdTEST() throws Exception {
        int id = 7;

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    void createProductTEST() throws Exception {

        int id = 7;

        ProductDto newProduct = new ProductDto("Mouse", "", 1000, 2);

        String newProductAsJson = this.objectMapper.writeValueAsString(newProduct);

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductAsJson)
                    )
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newProduct.getName())))
                .andExpect(jsonPath("$.price", is(newProduct.getPrice())))
                .andExpect(jsonPath("$.amount", is(newProduct.getAmount())));
    }

    @Test
    @Order(3)
    void updateProductTEST() throws Exception {

        int id = 7;

        ProductDto updatedProduct = new ProductDto("Mouse", "", 1500, 1);

        String updatedProductAsJson = this.objectMapper.writeValueAsString(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductAsJson)
                )
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedProduct.getName())))
                .andExpect(jsonPath("$.price", is(updatedProduct.getPrice())))
                .andExpect(jsonPath("$.amount", is(updatedProduct.getAmount())));

    }

    @Test
    @Order(4)
    void updateProductNotFoundTEST() throws Exception {

        int id = 10;

        ProductDto updatedProduct = new ProductDto("Mouse", "", 1500, 1);

        String updatedProductAsJson = this.objectMapper.writeValueAsString(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductAsJson)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(5)
    void deleteProductTEST() throws Exception {

        int id = 7;

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isOk());

    }

    @Test
    @Order(6)
    void deleteProductNotFoundTEST() throws Exception {

        int id = 7;

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNotFound());

    }

    @ParameterizedTest(name = "name={0}, price={1}, amount={2}")
    @CsvFileSource(resources = "/testFiles/filters.csv")
    @Order(7)
    void getProductsFiltered(String name, String price, String amount) throws Exception {
        HashMap<ProductFields, String> customFilters = new HashMap<>();
        customFilters.put(ProductFields.NAME, name);
        customFilters.put(ProductFields.PRICE, price);
        customFilters.put(ProductFields.AMOUNT, amount);

        List<ProductDto> products = this.productRepository
                .findAll(ProductFields.ID.getLabel(), "asc", customFilters)
                .stream()
                .map(Mapper::toDto)
                .toList();

        String jsonProducts = mockMvc.perform(get("/api/products")
                        .param(ProductFields.NAME.getLabel(), name)
                        .param(ProductFields.PRICE.getLabel(), price)
                        .param(ProductFields.AMOUNT.getLabel(), amount))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        List<ProductDto> queriedProducts = Arrays.asList(objectMapper.readValue(jsonProducts, ProductDto[].class));

        assertIterableEquals(products, queriedProducts);

    }
}
