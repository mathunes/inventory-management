package dev.test.inventory_management.service;

import dev.test.inventory_management.exception.ResourceNotFoundException;
import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.ProductType;
import dev.test.inventory_management.model.StockMovement;
import dev.test.inventory_management.model.StockMovementType;
import dev.test.inventory_management.repository.ProductRepository;
import dev.test.inventory_management.repository.StockMovementRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public ProductService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found."));
    }

    public List<Product> findByType(ProductType type) {
        return productRepository.findByType(type);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

        public Integer getTotalOutQuantity(Product product) {
        Integer total = stockMovementRepository.sumOutQuantityByProduct(product);
        return total != null ? total : 0;
    }

    public Map<String, Object> getProductProfit(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID " + productId));

        List<StockMovement> outMovements = stockMovementRepository.findByProductAndType(product, StockMovementType.OUT);

        int totalQuantityOut = 0;
        BigDecimal totalProfit = BigDecimal.ZERO;

        for (StockMovement movement : outMovements) {
            BigDecimal unitProfit = movement.getSalePrice()
                .subtract(product.getPrice());
            BigDecimal movementProfit = unitProfit.multiply(BigDecimal.valueOf(movement.getQuantity()));

            totalProfit = totalProfit.add(movementProfit);
            totalQuantityOut += movement.getQuantity();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("productId", product.getId());
        result.put("code", product.getCode());
        result.put("description", product.getDescription());
        result.put("price", product.getPrice());
        result.put("totalQuantityOut", totalQuantityOut);
        result.put("totalProfit", totalProfit);

        return result;
    }
}