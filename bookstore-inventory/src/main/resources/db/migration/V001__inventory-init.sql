CREATE TABLE product (
   id SERIAL primary key,
   description VARCHAR(50),
   available_quantity int
);

CREATE TABLE inventory (
   id uuid default gen_random_uuid() primary key,
   order_id uuid,
   product_id int,
   status VARCHAR(50),
   quantity int,
   foreign key (product_id) references product(id)
);