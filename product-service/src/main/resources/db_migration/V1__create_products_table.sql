CREATE TABLE tb_product (
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    brand       VARCHAR(100),
    model       VARCHAR(100),
    currency    VARCHAR(10),
    price       DOUBLE PRECISION,
    stock       INTEGER
);

INSERT INTO tb_product (description, brand, model, currency, price, stock)
VALUES
    ('Notebook Gamer', 'Dell', 'G15', 'USD', 1200.00, 10),
    ('Smartphone', 'Samsung', 'Galaxy S24', 'USD', 800.00, 25),
    ('Monitor 4K', 'LG', '27UL500', 'USD', 350.00, 15);
