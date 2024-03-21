DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
   id SERIAL primary key,
   name VARCHAR(50) NOT NULL,
   balance int
);

CREATE TABLE payment (
   payment_id uuid default gen_random_uuid() primary key,
   order_id uuid,
   customer_id int,
   status VARCHAR(50),
   amount int,
   foreign key (customer_id) references customer(id)
);

insert into customer(name, balance)
    values
        ('sam', 100),
        ('mike', 100),
        ('john', 100);