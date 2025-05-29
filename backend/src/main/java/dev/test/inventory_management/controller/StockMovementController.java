package dev.test.inventory_management.controller;

import dev.test.inventory_management.model.StockMovement;
import dev.test.inventory_management.service.StockMovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
public class StockMovementController {

    private final StockMovementService service;

    public StockMovementController(StockMovementService service) {
        this.service = service;
    }

    @GetMapping
    public List<StockMovement> getAll() {
        return service.findAll();
    }

    @PostMapping
    public StockMovement create(@RequestBody StockMovement movement) {
        return service.registerMovement(movement);
    }
}