package dev.test.inventory_management.repository;

import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.ProductType;
import dev.test.inventory_management.model.StockMovement;
import dev.test.inventory_management.model.StockMovementType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockMovementRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Test
    @DisplayName("Should return correct sum of OUT stock movements for a product")
    void sumOutQuantityByProduct_returnsCorrectSum() {
        Product product = new Product();
        product.setCode("P001");
        product.setDescription("Test Product");
        product.setType(ProductType.ELECTRONIC);
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setQuantity(100);
        product = productRepository.save(product);

        StockMovement inMovement = new StockMovement();
        inMovement.setProduct(product);
        inMovement.setType(StockMovementType.IN);
        inMovement.setQuantity(20);

        StockMovement outMovement1 = new StockMovement();
        outMovement1.setProduct(product);
        outMovement1.setType(StockMovementType.OUT);
        outMovement1.setQuantity(5);

        StockMovement outMovement2 = new StockMovement();
        outMovement2.setProduct(product);
        outMovement2.setType(StockMovementType.OUT);
        outMovement2.setQuantity(10);

        stockMovementRepository.saveAll(List.of(inMovement, outMovement1, outMovement2));

        Integer totalOut = stockMovementRepository.sumOutQuantityByProduct(product);

        assertThat(totalOut).isEqualTo(15);
    }

    @Test
    @DisplayName("Should return null when no OUT movements exist for the product")
    void sumOutQuantityByProduct_returnsNullIfNoOutMovements() {
        Product product = new Product();
        product.setCode("P002");
        product.setDescription("Empty Product");
        product.setType(ProductType.ELECTRONIC);
        product.setPrice(BigDecimal.valueOf(5.0));
        product.setQuantity(0);
        product = productRepository.save(product);

        Integer totalOut = stockMovementRepository.sumOutQuantityByProduct(product);

        assertThat(totalOut).isNull();
    }
}