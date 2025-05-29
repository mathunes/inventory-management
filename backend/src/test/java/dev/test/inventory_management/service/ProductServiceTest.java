package dev.test.inventory_management.service;

import dev.test.inventory_management.exception.ResourceNotFoundException;
import dev.test.inventory_management.model.Product;
import dev.test.inventory_management.model.ProductType;
import dev.test.inventory_management.model.StockMovement;
import dev.test.inventory_management.model.StockMovementType;
import dev.test.inventory_management.repository.ProductRepository;
import dev.test.inventory_management.repository.StockMovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setCode("P001");
        product.setDescription("Smart TV");
        product.setType(ProductType.ELECTRONIC);
        product.setPrice(BigDecimal.valueOf(1000));
        product.setQuantity(10);
    }

    @Test
    @DisplayName("Should return product when ID is valid")
    void testFindByIdOrThrow_validId() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findByIdOrThrow(1L);

        assertThat(result).isEqualTo(product);
    }

    @Test
    @DisplayName("Should throw exception when product not found")
    void testFindByIdOrThrow_notFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.findByIdOrThrow(2L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Product with ID 2 not found");
    }

    @Test
    @DisplayName("Should return total out quantity for a product")
    void testGetTotalOutQuantity_positiveCase() {
        when(stockMovementRepository.sumOutQuantityByProduct(product)).thenReturn(5);

        int result = productService.getTotalOutQuantity(product);

        assertThat(result).isEqualTo(5);
    }

    @Test
    @DisplayName("Should return 0 when no out quantity exists")
    void testGetTotalOutQuantity_nullSum() {
        when(stockMovementRepository.sumOutQuantityByProduct(product)).thenReturn(null);

        int result = productService.getTotalOutQuantity(product);

        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Should calculate total profit for a product with movements")
    void testGetProductProfit() {
        StockMovement m1 = new StockMovement();
        m1.setSalePrice(BigDecimal.valueOf(1200));
        m1.setQuantity(2);
        m1.setType(StockMovementType.OUT);
        m1.setProduct(product);

        StockMovement m2 = new StockMovement();
        m2.setSalePrice(BigDecimal.valueOf(1100));
        m2.setQuantity(1);
        m2.setType(StockMovementType.OUT);
        m2.setProduct(product);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockMovementRepository.findByProductAndType(product, StockMovementType.OUT))
            .thenReturn(List.of(m1, m2));

        Map<String, Object> profit = productService.getProductProfit(1L);

        assertThat(profit.get("totalQuantityOut")).isEqualTo(3);
        assertThat(profit.get("totalProfit")).isEqualTo(BigDecimal.valueOf((1200 - 1000) * 2 + (1100 - 1000) * 1));
    }

    @Test
    @DisplayName("Should return profit with zero movement")
    void testGetProductProfit_noMovements() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockMovementRepository.findByProductAndType(product, StockMovementType.OUT)).thenReturn(Collections.emptyList());

        Map<String, Object> result = productService.getProductProfit(1L);

        assertThat(result.get("totalQuantityOut")).isEqualTo(0);
        assertThat(result.get("totalProfit")).isEqualTo(BigDecimal.ZERO);
    }
}
