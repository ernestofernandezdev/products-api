package com.ferdev.restful.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferdev.restful.api.controllers.ProductController;
import com.ferdev.restful.api.enums.ProductFields;
import com.ferdev.restful.api.services.ProductService;
import com.ferdev.restful.api.entities.Product;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Test
	void getProductsTEST() throws Exception {
		when(productService.getProducts(
				Mockito.anyString(),
				Mockito.anyString(),
				anyMap()
		)).thenReturn(MockData.mockProductList());

		this.mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@ParameterizedTest(name = "sort={0}, order={1}")
	@CsvFileSource(resources = "/testFiles/sortAndOrder.csv")
	void getProductsWithParamsTEST(String sort, String order) throws Exception {
		when(productService.getProducts(
				Mockito.anyString(),
				Mockito.anyString(),
				anyMap()
		)).thenReturn(MockData.mockProductList());

		this.mockMvc.perform(get("/api/products")
					.param("sort", sort)
					.param("order", order)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	void getProductByIdTEST() throws Exception {
		Product product = MockData.mockSingleProduct();

		when(this.productService.getProductById(Mockito.anyInt())).thenReturn(product);

		this.mockMvc.perform(get("/api/products/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(product.getName())));
	}

	@ParameterizedTest(name = "name={0}, price={1}, amount={2}")
	@CsvFileSource(resources = "/testFiles/sampleProducts.csv")
	void createProductTEST(String name, Integer price, Integer amount) throws Exception {
		Product testProduct = Product.builder()
				.name(name)
				.price(price)
				.amount(amount)
				.build();

		when(this.productService.createProduct(Mockito.any(Product.class))).thenReturn(testProduct);

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonObject = objectMapper.writeValueAsString(testProduct);

		this.mockMvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(name)));
	}

	@ParameterizedTest(name = "name={0}, price={1}, amount={2}")
	@CsvFileSource(resources = "/testFiles/badSampleProducts.csv")
	void createProductFailTEST(String name, Integer price, Integer amount) throws Exception {
		Product testProduct = Product.builder()
				.name(name)
				.price(price)
				.amount(amount)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonObject = objectMapper.writeValueAsString(testProduct);

		this.mockMvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject)
				)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("MethodArgumentNotValidException")));
	}

	@ParameterizedTest(name = "name={0}, price={1}, amount={2}")
	@CsvFileSource(resources = "/testFiles/sampleProducts.csv")
	void updateProductTEST(String name, Integer price, Integer amount) throws Exception {
		Product testProduct = Product.builder()
				.name(name)
				.price(price)
				.amount(amount)
				.build();

		when(this.productService.updateProduct(Mockito.any(Product.class), Mockito.anyInt())).thenReturn(testProduct);

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonObject = objectMapper.writeValueAsString(testProduct);

		this.mockMvc.perform(put("/api/products/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(name)));
	}

	@ParameterizedTest(name = "name={0}, price={1}, amount={2}")
	@CsvFileSource(resources = "/testFiles/badSampleProducts.csv")
	void updateProductFailTEST(String name, Integer price, Integer amount) throws Exception {
		Product testProduct = Product.builder()
				.name(name)
				.price(price)
				.amount(amount)
				.build();

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonObject = objectMapper.writeValueAsString(testProduct);

		this.mockMvc.perform(put("/api/products/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject)
				)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("MethodArgumentNotValidException")));
	}

	@Test
	void deleteProductTEST() throws Exception {
		Product product = MockData.mockSingleProduct();

		when(this.productService.deleteProduct(Mockito.anyInt())).thenReturn(product);

		this.mockMvc.perform(delete("/api/products/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(product.getName())));
	}
}
