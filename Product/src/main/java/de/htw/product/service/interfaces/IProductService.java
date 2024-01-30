package de.htw.product.service.interfaces;

import de.htw.product.dto.ProductRequest;
import de.htw.product.dto.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface IProductService
{
    ResponseEntity<ProductResponse> createProduct (ProductRequest productRequest) throws URISyntaxException;

    ResponseEntity<ProductResponse> updateProduct (Integer id, ProductRequest productRequest);

    ResponseEntity<ProductResponse> deleteProduct (Integer id);

    ResponseEntity<ProductResponse> getProduct(int id);

    ResponseEntity<Iterable<ProductResponse>> getAllProducts();
}
