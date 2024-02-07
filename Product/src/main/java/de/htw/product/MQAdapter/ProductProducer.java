package de.htw.product.MQAdapter;

import de.htw.product.config.MQConfig;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.htw.product.dto.BasketResponse;
import de.htw.product.dto.ProductRequest;
import de.htw.product.dto.ProductResponse;
import de.htw.product.model.Basket;
import de.htw.product.model.Product;
import de.htw.product.repository.BasketRepository;
import de.htw.product.repository.ProductRepository;
import de.htw.product.service.WebService;
import de.htw.product.service.impl.BasketService;
import de.htw.product.service.interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ProductProducer {
    private final BasketRepository basketRepository;

    @Autowired
    private RabbitTemplate productTemplate;
    @Autowired
    private IProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);


    public ProductProducer(BasketRepository basketRepository, ProductRepository productRepository, ProductRepository productRepository1, IProductService productService, RabbitTemplate productTemplate) {
        super();
        this.basketRepository = basketRepository;
        this.productService = productService;
        this.productTemplate = productTemplate;
    }

    @PostMapping("cart/add-to-cart")
    public ResponseEntity<?> publishAddToCartEvent(@RequestBody ObjectNode objectNode, String token, ProductRequest productRequest) {
        Integer productID = objectNode.get("productID").asInt();
        String email = objectNode.get("email").asText();


        Product product = productBuilder(productRequest);
        Basket basket = basketRepository.findByOwner(email);
        basket.getProducts().add(product);

        if (product != null) {
            BasketResponse productResponse = mapToBasketResponse(basket);
            LOGGER.info(String.format("Add Message sent -> %s", productResponse));
            productTemplate.convertAndSend(MQConfig.exchange, MQConfig.addProductToBasket, productResponse);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("cart/remove-from-cart")
    public ResponseEntity<?> publishRemoveFromCartEvent(@RequestBody ObjectNode objectNode, String token) {
        Integer productID = objectNode.get("productID").asInt();
        String owner = objectNode.get("owner").asText();

        if (productID != null && owner != null && productService.getProduct(productID,token) != null ) {
            BasketResponse basket = new BasketResponse();
            basket.setId(productID);
            basket.setOwner(owner);

            LOGGER.info(String.format("Remove Message sent -> %s", basket));

            productTemplate.convertAndSend(MQConfig.exchange, MQConfig.removeProductFromBasket, basket);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("cart/update-cart")
    public ResponseEntity<?> publishUpdateCartEvent(@RequestBody ObjectNode objectNode, String token) {
        Integer productID = objectNode.get("productID").asInt();
        String owner = objectNode.get("owner").asText();

        if (productID != null && owner != null && productService.getProduct(productID, token) != null) {
            BasketResponse basket = new BasketResponse();
            basket.setId(productID);
            basket.setOwner(owner);

            LOGGER.info(String.format("Update Message sent -> %s", basket));

            productTemplate.convertAndSend(MQConfig.exchange, MQConfig.updateProductBasket, basket);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Product productBuilder(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .foiltype(productRequest.getFoiltype())
                .description(productRequest.getDescription())
                .price(Float.parseFloat(productRequest.getPrice()))
                .build();
    }
    private BasketResponse mapToBasketResponse(Basket basket) {
        return BasketResponse.builder()
                .owner(basket.getOwner())
                .products(basket.getProducts())
                .build();
    }
}
