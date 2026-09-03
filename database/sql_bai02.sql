USE web_bai01;

ALTER TABLE `user`
    ADD COLUMN IF NOT EXISTS email VARCHAR(255) NULL UNIQUE,
    ADD COLUMN IF NOT EXISTS enabled TINYINT(1) NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS otp VARCHAR(6) NULL,
    ADD COLUMN IF NOT EXISTS otp_expiry BIGINT NULL;

CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(15, 2) NOT NULL DEFAULT 0,
    image VARCHAR(500),
    createdDate BIGINT NOT NULL,
    category_id INT NOT NULL,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id)
        REFERENCES category(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE INDEX idx_products_created_date ON products(createdDate);