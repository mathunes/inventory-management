package dev.test.inventory_management.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ProductType type;
    
    private BigDecimal price;
    
    private int quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StockMovement> movements = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String code, String description, ProductType type, BigDecimal price,
            int quantity, List<StockMovement> movements) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.movements = movements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<StockMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<StockMovement> movements) {
        this.movements = movements;
    }
}