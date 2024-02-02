package de.htw.product.service.interfaces;

import de.htw.product.dto.ProductRequest;
import de.htw.product.dto.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface IProductService
{
    ResponseEntity<ProductResponse> createProduct (ProductRequest productRequest, String token) throws URISyntaxException;

    ResponseEntity<ProductResponse> updateProduct (Integer id, ProductRequest productRequest, String token);

    ResponseEntity<ProductResponse> deleteProduct (Integer id, String token);

    ResponseEntity<ProductResponse> getProduct(int id, String token);

    ResponseEntity<Iterable<ProductResponse>> getAllProducts(String token);
}
