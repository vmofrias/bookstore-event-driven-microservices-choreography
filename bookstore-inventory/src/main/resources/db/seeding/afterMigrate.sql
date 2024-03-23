DELETE FROM inventory;
DELETE FROM product;

ALTER SEQUENCE product_id_seq RESTART 1;

INSERT INTO product(description, available_quantity)
VALUES ('book', 10), ('pen', 10), ('rug', 10);