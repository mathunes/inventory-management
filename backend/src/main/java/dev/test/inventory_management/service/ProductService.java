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
import java.util.ArrayList;
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

    public Product update(Long id, Product input) {
        Product existing = findByIdOrThrow(id);

        existing.setCode(input.getCode());
        existing.setDescription(input.getDescription());
        existing.setPrice(input.getPrice());
        existing.setQuantity(input.getQuantity());
        existing.setType(input.getType());

        return productRepository.save(existing);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

        public Integer getTotalOutQuantity(Product product) {
        Integer total = stockMovementRepository.sumOutQuantityByProduct(product);
        return total != null ? total : 0;
    }

    public List<Map<String, Object>> getAllProductProfits() {
        List<Product> products = productRepository.findAll();
        List<Map<String, Object>> resultList = new ArrayList<>();
    
        for (Product product : products) {
            List<StockMovement> outMovements = stockMovementRepository.findByProductAndType(product, StockMovementType.OUT);
    
            int totalQuantityOut = 0;
            BigDecimal totalProfit = BigDecimal.ZERO;
    
            for (StockMovement movement : outMovements) {
                BigDecimal unitProfit = movement.getSalePrice().subtract(product.getPrice());
                BigDecimal movementProfit = unitProfit.multiply(BigDecimal.valueOf(movement.getQuantity()));
    
                totalProfit = totalProfit.add(movementProfit);
                totalQuantityOut += movement.getQuantity();
            }
    
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("productId", product.getId());
            map.put("code", product.getCode());
            map.put("description", product.getDescription());
            map.put("price", product.getPrice());
            map.put("totalQuantityOut", totalQuantityOut);
            map.put("totalProfit", totalProfit);
    
            resultList.add(map);
        }
    
        return resultList;
    }    
}