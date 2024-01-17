package de.htw.product.service;

import de.htw.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import Product.src.main.java.de.htw.product.model.Product.java
public interface IProductService
{
    void createProduct (Product product);

    void updateProduct (Product product);

    void deleteProduct (Product product);

    Product getProduct(int id);

    Iterable<Product> getAllProducts();
}
