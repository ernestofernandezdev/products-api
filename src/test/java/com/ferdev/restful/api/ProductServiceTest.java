package com.ferdev.restful.api;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.exceptions.ProductNotFoundException;
import com.ferdev.restful.api.repositories.ProductRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ferdev.restful.api.services.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    private List<Product> products;

    @BeforeEach
    void setData() {
        product = new Product("Heladera", "", 100, 1);

        products = Arrays.asList(
                new Product("Heladera", "", 100, 1),
                new Product("Telefono", "", 10, 1),
                new Product("Mesa", "", 20, 1),
                new Product("Silla", "", 3, 4)
        );
    }

    @Test
    void getProductsOrderedByPriceAscendingTEST() {
        when(productRepository.findByOrderByPriceAsc()).thenReturn(products);

        assertEquals(products, productService.getProducts("price", "asc"));

        verify(productRepository, times(1)).findByOrderByPriceAsc();
    }

    @Test
    void getProductsOrderedByPriceDescendingTEST() {
        when(productRepository.findByOrderByPriceDesc()).thenReturn(products);

        assertEquals(products, productService.getProducts("price", "desc"));

        verify(productRepository, times(1)).findByOrderByPriceDesc();
    }

    @Test
    void getProductsOrderedByNameAscendingTEST() {
        when(productRepository.findByOrderByNameAsc()).thenReturn(products);

        assertEquals(products, productService.getProducts("name", "asc"));

        verify(productRepository, times(1)).findByOrderByNameAsc();
    }

    @Test
    void getProductsOrderedByNameDescendingTEST() {
        when(productRepository.findByOrderByNameDesc()).thenReturn(products);

        assertEquals(products, productService.getProducts("name", "desc"));

        verify(productRepository, times(1)).findByOrderByNameDesc();
    }

    @Test
    void getProductsOrderedByAmountAscendingTEST() {
        when(productRepository.findByOrderByAmountAsc()).thenReturn(products);

        assertEquals(products, productService.getProducts("amount", "asc"));

        verify(productRepository, times(1)).findByOrderByAmountAsc();
    }

    @Test
    void getProductsOrderedByAmountDescendingTEST() {
        when(productRepository.findByOrderByAmountDesc()).thenReturn(products);

        assertEquals(products, productService.getProducts("amount", "desc"));

        verify(productRepository, times(1)).findByOrderByAmountDesc();
    }

    @Test
    void getProductsDefaultTEST() {
        when(productRepository.findAll()).thenReturn(products);

        assertEquals(products, productService.getProducts("noSort", "noOrder"));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductByIdTEST() {
        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(this.product));

        assertDoesNotThrow(() -> productService.getProductById(0));
        assertEquals(this.product, productService.getProductById(0));

        verify(productRepository, times(2)).findById(Mockito.anyInt());
    }

    @Test
    void getProductByIdNotFoundTEST() {
        when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(50));
    }

    @Test
    void createProductTEST() {
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(this.product);

        assertEquals(this.product, this.productService.createProduct(product));

        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    void updateProductTEST() {
        int id = 1;

        when(this.productRepository.findById(id)).thenReturn(Optional.of(this.product));
        when(this.productRepository.save(Mockito.any(Product.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        assertDoesNotThrow(() -> this.productService.updateProduct(this.product, id));

        Product returnedProduct = this.productService.updateProduct(this.product, id);
        assertEquals(id, returnedProduct.getId(), "Id of the product should be " + id);
        assertEquals(this.product, returnedProduct, "Object returned should be equal");

        verify(productRepository, times(2)).findById(Mockito.anyInt());
        verify(productRepository, times(2)).save(Mockito.any(Product.class));
    }

    @Test
    void updateProductNotFoundTEST() {
        int id = 1;

        when(this.productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> this.productService.updateProduct(this.product, id));

        verify(productRepository, times(1)).findById(Mockito.anyInt());
        verify(productRepository, times(0)).save(Mockito.any(Product.class));
    }

    @Test
    void deleteProductTEST() {
        int id = 1;

        when(this.productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(this.product));

        assertDoesNotThrow(() -> this.productService.deleteProduct(id));
        assertEquals(this.product, this.productService.deleteProduct(id));

        verify(this.productRepository, times(2)).findById(Mockito.anyInt());
        verify(this.productRepository, times(2)).deleteById(Mockito.anyInt());
    }

    @Test
    void deleteProductNotFoundTEST() {
        int id = 1;

        when(this.productRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> this.productService.deleteProduct(id));

        verify(this.productRepository, times(1)).findById(Mockito.anyInt());
        verify(this.productRepository, times(0)).deleteById(Mockito.anyInt());
    }
}
