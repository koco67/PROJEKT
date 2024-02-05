package de.htw.product.repository;

import de.htw.product.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, String> {
    Basket findByEmail(String email);
}
