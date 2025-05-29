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
    @DisplayName("Should calculate total profit for all products")
    void testGetAllProductProfits() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCode("P001");
        product1.setDescription("Smart TV");
        product1.setPrice(BigDecimal.valueOf(1000));
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setCode("P002");
        product2.setDescription("Notebook");
        product2.setPrice(BigDecimal.valueOf(2500));
        product2.setQuantity(5);

        StockMovement m1 = new StockMovement();
        m1.setSalePrice(BigDecimal.valueOf(1200));
        m1.setQuantity(2);
        m1.setType(StockMovementType.OUT);
        m1.setProduct(product1);

        StockMovement m2 = new StockMovement();
        m2.setSalePrice(BigDecimal.valueOf(1100));
        m2.setQuantity(1);
        m2.setType(StockMovementType.OUT);
        m2.setProduct(product1);

        StockMovement m3 = new StockMovement();
        m3.setSalePrice(BigDecimal.valueOf(2700));
        m3.setQuantity(1);
        m3.setType(StockMovementType.OUT);
        m3.setProduct(product2);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(stockMovementRepository.findByProductAndType(product1, StockMovementType.OUT))
            .thenReturn(List.of(m1, m2));
        when(stockMovementRepository.findByProductAndType(product2, StockMovementType.OUT))
            .thenReturn(List.of(m3));

        List<Map<String, Object>> results = productService.getAllProductProfits();

        assertThat(results).hasSize(2);

        Map<String, Object> result1 = results.get(0);
        assertThat(result1.get("productId")).isEqualTo(1L);
        assertThat(result1.get("totalQuantityOut")).isEqualTo(3);
        assertThat(result1.get("totalProfit")).isEqualTo(BigDecimal.valueOf(500));

        Map<String, Object> result2 = results.get(1);
        assertThat(result2.get("productId")).isEqualTo(2L);
        assertThat(result2.get("totalQuantityOut")).isEqualTo(1);
        assertThat(result2.get("totalProfit")).isEqualTo(BigDecimal.valueOf(200));
    }

    @Test
    @DisplayName("Should handle product with no stock movements")
    void testGetAllProductProfits_noMovements() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P001");
        product.setDescription("Smart TV");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setQuantity(10);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(stockMovementRepository.findByProductAndType(product, StockMovementType.OUT)).thenReturn(List.of());

        List<Map<String, Object>> results = productService.getAllProductProfits();

        assertThat(results).hasSize(1);
        Map<String, Object> result = results.get(0);
        assertThat(result.get("totalQuantityOut")).isEqualTo(0);
        assertThat(result.get("totalProfit")).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should calculate negative profit when sale price is lower than cost")
    void testGetAllProductProfits_negativeProfit() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("P001");
        product.setDescription("Smart TV");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setQuantity(10);

        StockMovement m1 = new StockMovement();
        m1.setSalePrice(BigDecimal.valueOf(900));
        m1.setQuantity(1);
        m1.setType(StockMovementType.OUT);
        m1.setProduct(product);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(stockMovementRepository.findByProductAndType(product, StockMovementType.OUT)).thenReturn(List.of(m1));

        List<Map<String, Object>> results = productService.getAllProductProfits();

        assertThat(results).hasSize(1);
        Map<String, Object> result = results.get(0);
        assertThat(result.get("totalQuantityOut")).isEqualTo(1);
        assertThat(result.get("totalProfit")).isEqualTo(BigDecimal.valueOf(-100));
    }
}
