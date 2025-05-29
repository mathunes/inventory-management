package dev.test.inventory_management.service;

import dev.test.inventory_management.exception.BusinessException;
import dev.test.inventory_management.model.Product;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockMovementServiceTest {

    @Mock
    private StockMovementRepository stockMovementRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private StockMovementService stockMovementService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setCode("P001");
        product.setQuantity(10);
    }

    @Test
    @DisplayName("Should register IN movement and increase product quantity")
    void registerInMovement_increasesProductQuantity() {
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(StockMovementType.IN);
        movement.setQuantity(5);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StockMovement result = stockMovementService.registerMovement(movement);

        assertThat(product.getQuantity()).isEqualTo(15);
        assertThat(result).isEqualTo(movement);

        verify(productRepository).save(product);
        verify(stockMovementRepository).save(movement);
    }

    @Test
    @DisplayName("Should register OUT movement and decrease product quantity")
    void registerOutMovement_decreasesProductQuantity() {
        product.setPrice(BigDecimal.valueOf(100.00));
    
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(StockMovementType.OUT);
        movement.setQuantity(3);
        movement.setSalePrice(BigDecimal.valueOf(120.00));
    
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        StockMovement result = stockMovementService.registerMovement(movement);
    
        assertThat(product.getQuantity()).isEqualTo(7);
        assertThat(result).isEqualTo(movement);
    
        verify(productRepository).save(product);
        verify(stockMovementRepository).save(movement);
    }    

    @Test
    @DisplayName("Should throw exception if product not found when registering movement")
    void registerMovement_productNotFound_throwsException() {
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(StockMovementType.IN);
        movement.setQuantity(1);

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stockMovementService.registerMovement(movement))
            .isInstanceOf(BusinessException.class)
            .hasMessage("Product not found.");

        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception if not enough stock for OUT movement")
    void registerOutMovement_insufficientStock_throwsException() {
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(StockMovementType.OUT);
        movement.setQuantity(15);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> stockMovementService.registerMovement(movement))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("Not enough stock");

        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return all stock movements")
    void findAll_returnsAllMovements() {
        StockMovement m1 = new StockMovement();
        StockMovement m2 = new StockMovement();

        when(stockMovementRepository.findAll()).thenReturn(List.of(m1, m2));

        List<StockMovement> result = stockMovementService.findAll();

        assertThat(result).containsExactly(m1, m2);
    }

    @Test
    @DisplayName("Should throw exception if sale price is less than product price for OUT movement")
    void registerOutMovement_salePriceBelowCost_throwsException() {
        product.setPrice(BigDecimal.valueOf(100.00));

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(StockMovementType.OUT);
        movement.setQuantity(2);
        movement.setSalePrice(BigDecimal.valueOf(90.00));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> stockMovementService.registerMovement(movement))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("Sale price must not be lower");

        verify(productRepository, never()).save(any());
        verify(stockMovementRepository, never()).save(any());
    }
}