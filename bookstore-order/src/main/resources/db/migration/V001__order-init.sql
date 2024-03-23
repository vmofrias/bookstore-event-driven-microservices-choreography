CREATE TABLE purchase_order (
    order_id uuid default gen_random_uuid() primary key,
    customer_id int,
    product_id int,
    quantity int,
    unit_price int,
    amount int,
    status VARCHAR(50),
    delivery_date TIMESTAMP,
    version int
);

CREATE TABLE order_payment (
    id SERIAL primary key,
    order_id uuid unique,
    payment_id uuid,
    success boolean,
    message VARCHAR(50),
    status VARCHAR(50),
    foreign key (order_id) references purchase_order(order_id)
);

CREATE TABLE order_inventory (
    id SERIAL primary key,
    order_id uuid unique,
    inventory_id uuid,
    success boolean,
    status VARCHAR(50),
    message VARCHAR(50),
    foreign key (order_id) references purchase_order(order_id)
);