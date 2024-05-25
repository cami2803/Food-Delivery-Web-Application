package com.example.fooddelivery.productControllerTest;

import com.example.fooddelivery.controllers.ProductController;
import com.example.fooddelivery.entities.Product;
import com.example.fooddelivery.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void testGetAllProducts_shouldReturnOkWithProducts() {
        List<Product> mockProducts = Arrays.asList(new Product("Pizza", 10.0f), new Product("Burger", 8.0f));
        when(productService.getAllProducts()).thenReturn(mockProducts);

        ResponseEntity<?> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<Product> actualProducts = (List<Product>) response.getBody();
        assertEquals(mockProducts.size(), actualProducts.size());
    }

    @Test
    public void testGetProductById_shouldReturnProductIfExists() {
        int productId = 1;
        Product mockProduct = new Product("Pizza", 10.0f);
        when(productService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

        ResponseEntity<?> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    public void testGetProductById_shouldReturnNotFoundIfProductDoesNotExist() {
        int productId = 1;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productController.getProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProduct_shouldReturnOkIfProductUpdatedSuccessfully() {
        int productId = 1;
        Product mockProduct = new Product("Pizza", 10.0f);
        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(mockProduct);

        ResponseEntity<?> response = productController.updateProduct(productId, mockProduct);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
    }

    @Test
    public void testUpdateProduct_shouldReturnNotFoundIfProductToUpdateDoesNotExist() {
        int productId = 1;
        Product mockProduct = new Product("Pizza", 10.0f);
        when(productService.updateProduct(eq(productId), any(Product.class))).thenThrow(new RuntimeException());

        ResponseEntity<?> response = productController.updateProduct(productId, mockProduct);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct_shouldReturnNoContentIfProductDeletedSuccessfully() {
        int productId = 1;
        ResponseEntity<?> expectedResponse = ResponseEntity.noContent().build();
        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<?> response = productController.deleteProduct(productId);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }
}
