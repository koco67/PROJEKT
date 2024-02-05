package de.htw.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "basket")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @Id
    private String owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "basket_products",
    joinColumns = {@JoinColumn(name = "basket_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    private HashMap<Product, Integer> products;
}
