INSERT INTO customers(id, name, email, createdat, updatedat) VALUES
(10, 'John', 'john@myemail.com', '2024-08-09T12:35:48.349934', '2024-08-09T12:35:48.349939');

INSERT INTO orders(id, product, quantity, createdat, updatedat) VALUES
(10, 'phone', 1, '2024-08-09T12:35:48.349934', '2024-08-09T12:35:48.349939');

INSERT INTO customers_orders(customers_id, orderdbos_id) VALUES (10, 10);