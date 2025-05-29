package dev.test.inventory_management.service;

import dev.test.inventory_management.exception.BusinessException;
import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.StockMovement;
import dev.test.inventory_management.model.StockMovementType;
import dev.test.inventory_management.repository.ProductRepository;
import dev.test.inventory_management.repository.StockMovementRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository, ProductRepository productRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
    }

    public List<StockMovement> findAll() {
        return stockMovementRepository.findAll();
    }

    @Transactional
    public StockMovement registerMovement(StockMovement movement) {
        Product product = productRepository.findById(movement.getProduct().getId())
            .orElseThrow(() -> new BusinessException("Product not found."));

        if (movement.getType() == StockMovementType.IN) {
            product.setQuantity(product.getQuantity() + movement.getQuantity());
        } else if (movement.getType() == StockMovementType.OUT) {
            if (product.getQuantity() < movement.getQuantity()) {
                throw new BusinessException("Not enough stock for product: " + product.getCode());
            }
            product.setQuantity(product.getQuantity() - movement.getQuantity());
        }

        productRepository.save(product);
        return stockMovementRepository.save(movement);
    }
}