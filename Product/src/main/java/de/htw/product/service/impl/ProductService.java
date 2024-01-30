package de.htw.product.service.impl;

import de.htw.product.dto.ProductRequest;
import de.htw.product.dto.ProductResponse;
import de.htw.product.exception.BadRequestException;
import de.htw.product.exception.ResourceNotFoundException;
import de.htw.product.model.Product;
import de.htw.product.repository.ProductRepository;
import de.htw.product.service.WebService;
import de.htw.product.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final WebService webService;

    public ResponseEntity<ProductResponse> createProduct (ProductRequest productRequest) throws URISyntaxException {
        if(productRequest.getDescription() == null || productRequest.getFoiltype() == null || productRequest.getName() == null || productRequest.getPrice() == null) {
            throw new BadRequestException("Product needs to have name, foiltype, description and price.");
        }

        Product savedProduct = productRepository.save(productBuilder(productRequest));

        URI productURI = new URI(String.format("/rest/product/%d", savedProduct.getId()));
        return ResponseEntity.created(productURI).build();
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        if(productRequest.getName() != null) product.setName(productRequest.getName());
        if(productRequest.getFoiltype() != null) product.setFoiltype(productRequest.getFoiltype());
        if(productRequest.getDescription() != null) product.setDescription(productRequest.getDescription());
        if(productRequest.getPrice() != null) product.setPrice(Float.parseFloat(productRequest.getPrice()));
        productRepository.save(product);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ProductResponse> deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProductResponse> getProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            ProductResponse response = mapToProductResponse(product.get());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            throw new ResourceNotFoundException("Product", "id", id);
        }
    }

    @Override
    public ResponseEntity<Iterable<ProductResponse>> getAllProducts() {
        Iterable<ProductResponse> products = productRepository.findAll().stream().map(this::mapToProductResponse).toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .foiltype(product.getFoiltype())
                .description(product.getDescription())
                .price(String.valueOf(product.getPrice()))
                .build();
    }
    private Product productBuilder(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .foiltype(productRequest.getFoiltype())
                .description(productRequest.getDescription())
                .price(Float.parseFloat(productRequest.getPrice()))
                .build();
    }
}
