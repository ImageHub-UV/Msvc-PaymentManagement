CREATE TABLE IF NOT EXISTS card (
    card_number INT PRIMARY KEY,
    user_id INT NOT NULL,
    expiration_date DATE NOT NULL,
    cvc_code INT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
    orders_id SERIAL PRIMARY KEY,
    card_id INT NOT NULL references card(card_number),
    user_id INT NOT NULL,
    order_date DATE NOT NULL,
    payment_method VARCHAR(2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_image(
    order_image_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL references orders(orders_id),
    image_id INT NOT NULL
);