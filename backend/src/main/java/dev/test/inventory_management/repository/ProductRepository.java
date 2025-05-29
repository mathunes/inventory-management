package dev.test.inventory_management.repository;

import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByType(ProductType type);

    Product findByCode(String code);
}
