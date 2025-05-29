package dev.test.inventory_management.repository;

import dev.test.inventory_management.model.StockMovement;
import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.StockMovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProduct(Product product);

    List<StockMovement> findByProductAndType(Product product, StockMovementType type);

    @Query("SELECT SUM(sm.quantity) FROM StockMovement sm WHERE sm.product = :product AND sm.type = 'OUT'")
    Integer sumOutQuantityByProduct(@Param("product") Product product);
}