package com.ferdev.restful.api;

import com.ferdev.restful.api.controllers.ProductController;
import com.ferdev.restful.api.services.ProductService;
import com.ferdev.restful.api.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	private List<Product> products;

	private Product product;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@BeforeEach
	void setData() {
		this.product = new Product("Heladera", "frio", 100000, 1);

		this.products = new ArrayList<>(Arrays.asList(
				new Product("Heladera", "frio", 100000, 1),
				new Product("Calefactor", "calor", 150000, 2),
				new Product("Teléfono", "celular", 50000, 1),
				new Product("Silla", "hogar", 4000, 4)
		));
	}

	@Test
	void getProductsTEST() throws Exception {
		when(productService.getProducts("noSort", "noOrder")).thenReturn(products);

		this.mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@ParameterizedTest(name = "sort={0}, order={1}")
	@CsvFileSource(resources = "/testFiles/sortAndOrder.csv")
	void getProductsWithParamsTEST(String sort, String order) throws Exception {
		when(productService.getProducts(Mockito.anyString(), Mockito.anyString())).thenReturn(this.products);

		this.mockMvc.perform(get("/api/products")
					.param("sort", sort)
					.param("order", order)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	void getProductsWithNullParamsTEST() throws Exception {
		when(this.productService.getProducts("noSort", "noOrder")).thenReturn(this.products);

		this.mockMvc.perform(get("/api/products?sort=&order="))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	@Test
	void getProductByIdTEST() throws Exception {
		when(this.productService.getProductById(Mockito.anyInt())).thenReturn(this.product);

		this.mockMvc.perform(get("/api/products/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(this.product.getName())));
	}

	@Test
	void createProductTEST() {

	}
}
