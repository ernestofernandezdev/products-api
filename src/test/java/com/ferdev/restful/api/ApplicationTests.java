package com.ferdev.restful.api;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.exceptions.InvalidProductException;
import com.ferdev.restful.api.repositories.ProductRepository;
import com.ferdev.restful.api.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApplicationTests {

	private List<Product> products;

	@MockBean
	private ProductRepository productRepository;

	@Autowired
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
	@DisplayName("Wrong query params tests")
	void getProductsTEST(String sort, String order) {
		when(productRepository.findAll()).thenReturn(this.products);
		when(productRepository.findByOrderByNameAsc()).thenReturn(
				this.products.stream()
						.sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
						.collect(Collectors.toList())
		);

		assertDoesNotThrow(() -> this.productService.getProducts(sort, order).get(0), "Should not throw an exception");
		assertEquals(this.products.size(), productService.getProducts(sort, order).size());
	}

	@ParameterizedTest(name = "name={0}, price={1}, amount={2}")
	@CsvFileSource(resources = "/testFiles/productExamples.csv")
	@DisplayName("Bad products tests")
	void validateProductTEST(String name, Integer price, Integer amount) {
		assertThrows(
				InvalidProductException.class,
				() -> this.productService.validateProduct(new Product(
						name,
						"Generic description",
						price,
						amount)
				),
				"This should throw an InvalidProductException"
		);
	}
}
