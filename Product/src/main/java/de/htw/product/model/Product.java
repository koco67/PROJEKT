package de.htw.product.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String foiltype;
    private String description;
    private float price;
}
