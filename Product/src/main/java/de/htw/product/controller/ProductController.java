package de.htw.product.controller;

import de.htw.product.dto.ProductRequest;
import de.htw.product.dto.ProductResponse;
import de.htw.product.service.impl.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/rest/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping(produces = "application/json")
    public ResponseEntity<Iterable<ProductResponse>> getProducts(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return productService.getAllProducts(token);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Integer id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return productService.getProduct(id, token);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws URISyntaxException {
        return productService.createProduct(productRequest, token);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return productService.updateProduct(id, productRequest, token);
    }
}
