package de.htw.product.controller;

import de.htw.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService songService;

}
