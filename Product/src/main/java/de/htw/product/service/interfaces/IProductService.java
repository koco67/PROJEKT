package de.htw.product.service.interfaces;

import de.htw.product.model.Product;
import de.htw.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
public interface IProductService
{
    void createProduct (Product product);

    void updateProduct (Product product);

    void deleteProduct (Product product);

    Product getProduct(int id);

    Iterable<Product> getAllProducts();
}
