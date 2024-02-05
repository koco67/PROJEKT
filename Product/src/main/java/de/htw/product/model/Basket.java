package de.htw.product.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinFormula;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "basket_products",
    joinColumns = {@JoinColumn(name = "owner", referencedColumnName = "owner")},
    inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    private List<Product> products;
}
