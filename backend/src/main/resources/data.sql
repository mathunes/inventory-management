INSERT INTO product (code, description, type, price, quantity) VALUES
('ELEC001', 'Samsung 55" 4K Smart TV', 'ELECTRONIC', 2999.99, 15),
('ELEC002', 'Sony PlayStation 5', 'ELECTRONIC', 4499.99, 10),
('ELEC003', 'Apple MacBook Pro M2', 'ELECTRONIC', 12999.99, 8),
('HOME001', 'LG Washing Machine', 'HOME_APPLIANCE', 2499.99, 12),
('HOME002', 'Samsung Refrigerator', 'HOME_APPLIANCE', 3999.99, 7),
('HOME003', 'Philips Air Fryer', 'HOME_APPLIANCE', 799.99, 20),
('FURN001', 'Modern Sofa Set', 'FURNITURE', 4999.99, 5),
('FURN002', 'Dining Table Set', 'FURNITURE', 2999.99, 8),
('FURN003', 'Office Desk', 'FURNITURE', 899.99, 15);

INSERT INTO stock_movement (product_id, type, quantity, sale_price, sale_date) VALUES
(1, 'IN', 20, NULL, CURRENT_TIMESTAMP),
(1, 'OUT', 5, 3299.99, CURRENT_TIMESTAMP),
(2, 'IN', 15, NULL, CURRENT_TIMESTAMP),
(2, 'OUT', 5, 4799.99, CURRENT_TIMESTAMP),
(3, 'IN', 10, NULL, CURRENT_TIMESTAMP),
(3, 'OUT', 2, 13999.99, CURRENT_TIMESTAMP),
(4, 'IN', 15, NULL, CURRENT_TIMESTAMP),
(4, 'OUT', 3, 2699.99, CURRENT_TIMESTAMP),
(5, 'IN', 10, NULL, CURRENT_TIMESTAMP),
(5, 'OUT', 3, 4299.99, CURRENT_TIMESTAMP); 