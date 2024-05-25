package com.example.fooddelivery.productControllerTest;

import com.example.fooddelivery.entities.Product;
import com.example.fooddelivery.repositories.ProductRepository;
import com.example.fooddelivery.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        List<Product> mockProducts = Arrays.asList(new Product("Pizza", 10.0f), new Product("Burger", 8.0f));
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Pizza", products.get(0).getName());
        assertEquals("Burger", products.get(1).getName());
    }

    @Test
    public void testGetProductById_shouldReturnProductIfExists() {
        int productId = 1;
        Product mockProduct = new Product("Pizza", 10.0f);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Optional<Product> product = productService.getProductById(productId);

        assertTrue(product.isPresent());
        assertEquals("Pizza", product.get().getName());
    }

    @Test
    public void testGetProductById_shouldThrowExceptionIfNotExists() {
        int productId = 1;
        when(productRepository.existsById(productId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(productId);
        });

        assertEquals("Product not found with id: " + productId, exception.getMessage());
    }

    @Test
    public void testCreateProduct() {
        Product mockProduct = new Product("Pizza", 10.0f);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);

        Product createdProduct = productService.createProduct(mockProduct);

        assertEquals("Pizza", createdProduct.getName());
        assertEquals(10.0f, createdProduct.getPrice());
    }

    @Test
    public void testUpdateProduct_shouldReturnUpdatedProductIfExists() {
        int productId = 1;
        Product existingProduct = new Product("Pizza", 10.0f);
        Product updatedProduct = new Product("Burger", 12.0f);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertEquals("Burger", result.getName());
        assertEquals(12.0f, result.getPrice());
    }

    @Test
    public void testUpdateProduct_shouldThrowExceptionIfNotExists() {
        int productId = 1;
        Product updatedProduct = new Product("Burger", 12.0f);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(productId, updatedProduct);
        });

        assertEquals("Product not found with id: " + productId, exception.getMessage());
    }

    @Test
    public void testDeleteProduct_shouldDeleteProductIfExists() {
        int productId = 1;
        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testDeleteProduct_shouldThrowExceptionIfNotExists() {
        int productId = 1;
        when(productRepository.existsById(productId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(productId);
        });

        assertEquals("Product not found with id: " + productId, exception.getMessage());
    }
}