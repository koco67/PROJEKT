package de.htw.product.service.impl;

import de.htw.product.dto.BasketRequest;
import de.htw.product.dto.BasketResponse;
import de.htw.product.dto.ProductRequest;
import de.htw.product.model.Basket;
import de.htw.product.model.Product;
import de.htw.product.repository.BasketRepository;
import de.htw.product.repository.ProductRepository;
import de.htw.product.service.WebService;
import de.htw.product.service.interfaces.IBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BasketService implements IBasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final WebService webService;

    @Override
    public ResponseEntity<BasketResponse> getBasket(String token) {
        String email = webService.getEmailByToken(token);
        Basket basket = basketRepository.findByOwner(email);
        BasketResponse basketResponse = mapToBasketResponse(basket);
        return ResponseEntity.ok().body(basketResponse);
    }

    @Override
    public ResponseEntity<BasketResponse> addToBasket(String token, ProductRequest productRequest) {
        String email = webService.getEmailByToken(token);
        Basket basket = basketRepository.findByOwner(email);
        Product product = productBuilder(productRequest);
        if(basket != null) {
            basket.getProducts().add(product);
        }

        else {
            List<Product> products = new ArrayList<>();
            basket = new Basket(email, products);
            basket.getProducts().add(product);
        }
        basketRepository.save(basket);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<BasketResponse> removeFromBasket(String token, ProductRequest productRequest) {
        String email = webService.getEmailByToken(token);
        Basket basket = basketRepository.findByOwner(email);
        Product product = productBuilder(productRequest);
        if(basket != null) {
            basket.getProducts().stream().filter(x -> Objects.equals(x.getName(), product.getName()))
                    .findFirst().ifPresent(basket.getProducts()::remove);
            basketRepository.save(basket);
        }
        return ResponseEntity.ok().build();
    }

    private BasketResponse mapToBasketResponse(Basket basket) {
        return BasketResponse.builder()
                .owner(basket.getOwner())
                .products(basket.getProducts())
                .build();
    }

    private Basket basketBuilder(BasketRequest basketRequest) {
        return Basket.builder()
                .owner(basketRequest.getOwner())
                .products(basketRequest.getProducts())
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
