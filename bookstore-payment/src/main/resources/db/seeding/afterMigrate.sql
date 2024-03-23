DELETE FROM payment;
DELETE FROM customer;

ALTER SEQUENCE customer_id_seq RESTART 1;

INSERT INTO customer(name, balance)
VALUES ('LDSK', 100), ('Pandora', 100), ('Atlas', 100);