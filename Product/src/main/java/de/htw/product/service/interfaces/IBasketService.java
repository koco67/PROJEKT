package de.htw.product.service.interfaces;

import de.htw.product.dto.BasketResponse;
import de.htw.product.dto.ProductRequest;
import org.springframework.http.ResponseEntity;

public interface IBasketService {
    ResponseEntity<BasketResponse> getBasket(String token);
    ResponseEntity<BasketResponse> addToBasket(String token, ProductRequest productRequest);
    ResponseEntity<BasketResponse> removeFromBasket(String token, ProductRequest productRequest);
}
