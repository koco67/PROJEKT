package de.htw.product.MQAdapter;

import de.htw.product.config.MQConfig;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.htw.product.dto.ProductRequest;
import de.htw.product.dto.ProductResponse;
import de.htw.product.service.interfaces.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ProductProducer {

    @Autowired
    private RabbitTemplate productTemplate;
    @Autowired
    private IProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);


    public ProductProducer(IProductService productService, RabbitTemplate productTemplate) {
        super();
        this.productService = productService;
        this.productTemplate = productTemplate;
    }

    @PostMapping("cart/add-to-cart")
    public ResponseEntity<?> publishAddToCartEvent(@RequestBody ObjectNode objectNode, String token) {
        int productID = objectNode.get("productID").asInt();
        int quantity = objectNode.get("quantity").asInt();
        String cartID = objectNode.get("cartID").asText();

        ProductRequest product = productService.getProduct(productID, token);

        if (product != null) {
            ProductResponse productMessage = Mapper.mapToProductMessage(product, quantity, cartID);
            LOGGER.info(String.format("Add Message sent -> %s", productMessage));
            productTemplate.convertAndSend(MQConfig.exchange, MQConfig.addProductToCart, productMessage);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("cart/remove-from-cart")
    public ResponseEntity<?> publishRemoveFromCartEvent(@RequestBody ObjectNode objectNode, String token) {
        Integer productID = objectNode.get("productID").asInt();
        String cartID = objectNode.get("cartID").asText();

        if (productID != null && cartID != null && productService.getProduct(productID,token) != null ) {
            ProductMessage productMessage = new ProductMessage();
            productMessage.setProduct(productID);
            productMessage.setUserID(cartID);

            LOGGER.info(String.format("Remove Message sent -> %s", productMessage));

            productTemplate.convertAndSend(MQConfig.exchange, MQConfig.removeProductFromCart, productMessage);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("cart/update-cart")
    public ResponseEntity<?> publishUpdateCartEvent(@RequestBody ObjectNode objectNode, String token) {
        Integer productID = objectNode.get("productID").asInt();
        int quantity = objectNode.get("quantity").asInt();
        String cartID = objectNode.get("cartID").asText();

        if (productID != null && cartID != null && productService.getProduct(productID, token) != null) {
            ProductMessage productMessage = new ProductMessage();
            productMessage.setProduct(productID);
            productMessage.setUserID(cartID);
            productMessage.setQuantity(quantity);

            LOGGER.info(String.format("Update Message sent -> %s", productMessage));

            productTemplate.convertAndSend(MQConfig.exchange, MQConfig.updateProductCart, productMessage);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}