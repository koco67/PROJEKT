package de.htw.product.service.impl;

import de.htw.product.model.Product;
import de.htw.product.repository.ProductRepository;
import de.htw.product.service.WebService;
import de.htw.product.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final WebService webService;

    public void createProduct (Product product) {

        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {

        productRepository.delete(product);
    }

    @Override
    public Product getProduct(int id) {

        return null;
    }

    @Override
    public Iterable<Product> getAllProducts() {

        return productRepository.findAll();
    }


}
