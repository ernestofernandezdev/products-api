package com.ferdev.restful.api;

import com.ferdev.restful.api.controllers.ProductController;
import com.ferdev.restful.api.services.ProductService;
import com.ferdev.restful.api.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ApplicationTests {

//	@MockBean
//	private ProductRepository productRepository;
//
//	@Autowired
//	private ProductService productService;
//

//
//	@ParameterizedTest(name = "sort={0}, order={1}")
//	@CsvFileSource(resources = "/testFiles/sortAndOrder.csv")
//	@DisplayName("Wrong query params tests")
//	void getProductsTEST(String sort, String order) {
//		when(productRepository.findAll()).thenReturn(this.products);
//		when(productRepository.findByOrderByNameAsc()).thenReturn(
//				this.products.stream()
//						.sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
//						.collect(Collectors.toList())
//		);
//
//		assertDoesNotThrow(() -> this.productService.getProducts(sort, order).get(0), "Should not throw an exception");
//		assertEquals(this.products.size(), productService.getProducts(sort, order).size());
//	}
//
//	@ParameterizedTest(name = "name={0}, price={1}, amount={2}")
//	@CsvFileSource(resources = "/testFiles/productExamples.csv")
//	@DisplayName("Bad products tests")
//	void validateProductTEST(String name, Integer price, Integer amount) {
//		assertThrows(
//				InvalidProductException.class,
//				() -> this.productService.validateProduct(new Product(
//						name,
//						"Generic description",
//						price,
//						amount)
//				),
//				"This should throw an InvalidProductException"
//		);
//	}

	private List<Product> products;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@BeforeEach
	void setData() {
		this.products = new ArrayList<>(Arrays.asList(
				new Product("Heladera", "frio", 100000, 1),
				new Product("Calefactor", "calor", 150000, 2),
				new Product("Teléfono", "celular", 50000, 1),
				new Product("Silla", "hogar", 4000, 4)
		));
	}

	@ParameterizedTest(name = "sort={0}, order={1}")
	@CsvFileSource(resources = "/testFiles/sortAndOrder.csv")
	@DisplayName("Get products TEST")
	void getProductsTEST(String sort, String order) throws Exception {
		when(productService.getProducts(sort, order)).thenReturn(products);

		MvcResult mvcResult = this.mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk())
				.andReturn();
	}
}
