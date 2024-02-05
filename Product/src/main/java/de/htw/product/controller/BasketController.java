package de.htw.product.controller;

import de.htw.product.dto.BasketResponse;
import de.htw.product.dto.ProductRequest;
import de.htw.product.model.Basket;
import de.htw.product.service.impl.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketResponse> getBasket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return basketService.getBasket(token);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketResponse> addTobasket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                      @RequestBody ProductRequest productRequest) {
        return basketService.addToBasket(token, productRequest);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketResponse> removeFromBasket(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                           @RequestBody ProductRequest productRequest) {
        return basketService.removeFromBasket(token, productRequest);
    }
}
