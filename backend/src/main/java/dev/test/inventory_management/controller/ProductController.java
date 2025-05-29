package dev.test.inventory_management.controller;

import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.ProductType;
import dev.test.inventory_management.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findByIdOrThrow(id);
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        service.findByIdOrThrow(id);
        product.setId(id);
        return service.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.findByIdOrThrow(id);
        service.delete(id);
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<Map<String, Object>>> getByType(@RequestParam ProductType type) {
        List<Product> products = service.findByType(type);

        List<Map<String, Object>> result = products.stream().map(product -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", product.getId());
            map.put("code", product.getCode());
            map.put("description", product.getDescription());
            map.put("type", product.getType());
            map.put("price", product.getPrice());
            map.put("quantityAvailable", product.getQuantity());
            map.put("quantityOut", service.getTotalOutQuantity(product));
            return map;
        }).toList();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/profit")
    public ResponseEntity<Map<String, Object>> getProfit(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductProfit(id));
    }
}